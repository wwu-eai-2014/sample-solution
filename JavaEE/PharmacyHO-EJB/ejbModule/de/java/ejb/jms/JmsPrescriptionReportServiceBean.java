package de.java.ejb.jms;
import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.Resource;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.jboss.logging.Logger;

import de.java.ejb.Subsidiary;
import de.java.ejb.statistic.Duration;
import de.java.ejb.statistic.PrescriptionReport;
import de.java.ejb.statistic.Timespan;

@Stateless
public class JmsPrescriptionReportServiceBean extends AbstractJmsBean implements JmsPrescriptionReportService {
	
	@Resource(lookup = "java:/topic/PrescriptionReport")
	private Topic prescriptionReportTopic;
	
	Logger log = Logger.getLogger(JmsPrescriptionReportServiceBean.class);

	@Override
	public Collection<PrescriptionReport> getPrescriptionReports(Timespan timespan) {
		final Collection<PrescriptionReport> result = new ArrayList<>();
		try {
			final Session session = getConnection().createSession(false, Session.AUTO_ACKNOWLEDGE);
			
			try {
				TextMessage message = session.createTextMessage();
				final Queue replyQueue = session.createTemporaryQueue();
				message.setJMSReplyTo(replyQueue);			
				message.setText(timespan.toString());
	
				MessageProducer sender = session.createProducer(prescriptionReportTopic);
				sender.send(message);
				log.debug("Message send");
				
				execute(new HasOpenConnection() {
					@Override
					public void action() throws JMSException {
						/*
						 * the order of retrieval is done this way, because the response
						 * from JaVa can be expected to arrive sooner (HO and JaVa run
						 * on the same server) than that of C.Sharpe
						 */
						{
							// retrieve report from JaVa
							MessageConsumer consumer = session.createConsumer(replyQueue, "subsidiary='JaVa'");
							BytesMessage resultMsg = (BytesMessage) consumer.receive(1000);
							if (resultMsg != null) {
								result.add(convert(resultMsg, Subsidiary.JAVA));
							}
						}
						
						{
							// retrieve report from CSharpe
							MessageConsumer consumer = session.createConsumer(replyQueue, "subsidiary='CSharpe'");
							BytesMessage resultMsg = (BytesMessage) consumer.receive(1000);
							if (resultMsg != null) {
								result.add(convert(resultMsg, Subsidiary.C_SHARPE));
							}
						}
					}
				});
			} finally {
				session.close();
			}
		} catch (JMSException e) {
			log.error("Tried to send JMS message", e);
			throw new EJBException(e);
		}
		return result;
	}

	private PrescriptionReport convert(BytesMessage message, Subsidiary fromSubsidiary) throws JMSException {
	  long totalNumberOfPrescriptions = message.readLong();
    double averageNumerOfItemsPerPrescription = message.readDouble();
    Duration averageFulfilmentTimespan = new Duration(message.readLong());
    PrescriptionReport report = new PrescriptionReport(fromSubsidiary, totalNumberOfPrescriptions,
    averageNumerOfItemsPerPrescription, averageFulfilmentTimespan);
		log(report);
		return report;
	}

	private void log(PrescriptionReport report) {
		log.debug(String.format(
				"Report received from %s received: tnp %d, aip %f and aft '%s'",
				report.getSubsidiary().toString(), report.getTotalNumberOfPrescriptions(),
				report.getAverageNumberOfItemsPerPrescription(),
				report.getAverageFulfilmentTimespan().toString()));
	}
	
	private void execute(HasOpenConnection requiresOpenConnection) throws JMSException {
		getConnection().start();
		try {
			requiresOpenConnection.action();
		} finally {
			getConnection().stop();
		}
	}
	
	private interface HasOpenConnection {
		void action() throws JMSException;
	}
}
