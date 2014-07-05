package de.java.ejb.jms;

import java.math.BigInteger;
import java.util.Collection;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.BytesMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import de.java.domain.prescription.Prescription;
import de.java.domain.prescription.PrescriptionState;
import de.java.ejb.PrescriptionService;
import de.java.ejb.Timespan;

@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
    @ActivationConfigProperty(propertyName = "destination", propertyValue = "topic/PrescriptionReport"),
    @ActivationConfigProperty(propertyName = "clientId", propertyValue = "JaVa"),
    @ActivationConfigProperty(propertyName = "DLQMaxResent", propertyValue = "10"),
})
public class PrescriptionReportMDB extends AbstractJmsBean implements MessageListener {

  @EJB
  PrescriptionService service;

  @Override
  public void onMessage(Message incomingMessage) {
    try {
      final String content = ((TextMessage) incomingMessage).getText();
      
      Collection<Prescription> prescriptions = service.getAllPrescriptionsEnteredIn(new Timespan(content));
      
      long totalNumberOfPrescriptions = prescriptions.size();
      double averageNumberOfItemsPerPrescription = averageNumberOfItems(prescriptions);
      long averageDuration = averageDuration(prescriptions);

      Session session = getConnection().createSession(false, Session.AUTO_ACKNOWLEDGE);
      try {
        BytesMessage reply = session.createBytesMessage();
        reply.setStringProperty("subsidiary", "JaVa");
        reply.writeLong(totalNumberOfPrescriptions);
        reply.writeDouble(averageNumberOfItemsPerPrescription);
        reply.writeLong(averageDuration);
        
        MessageProducer producer = session.createProducer(incomingMessage.getJMSReplyTo());
        producer.send(reply);
      } finally {
        session.close();
      }
    } catch (Exception e) {
      log.error("Error while processing message", e);
    }
  }

  private double averageNumberOfItems(Collection<Prescription> prescriptions) {
    long numberOfItems = 0;
    double numberOfRelevantPrescriptions = 0;
    for (Prescription p : prescriptions) {
      if (p.getItems().isEmpty())
        continue;
      numberOfRelevantPrescriptions++;
      numberOfItems += p.getItems().size();
    }
    if (numberOfRelevantPrescriptions == 0)
      return 0;
    return numberOfItems / numberOfRelevantPrescriptions;
  }

  private long averageDuration(Collection<Prescription> prescriptions) {
    BigInteger sumOfDurations = BigInteger.ZERO;
    long numberOfFulfilledPrescription = 0;
    for (Prescription p : prescriptions) {
      if (p.getState() == PrescriptionState.FULFILLED) {
        BigInteger cur = fulfilmentTimespanInSeconds(p);
        sumOfDurations = sumOfDurations.add(cur);
        numberOfFulfilledPrescription++;
      }
    }
    if (numberOfFulfilledPrescription == 0) {
      return 0;
    }
    BigInteger averageDuration = sumOfDurations.divide(BigInteger.valueOf(numberOfFulfilledPrescription));
    return averageDuration.longValue();
  }

  private BigInteger fulfilmentTimespanInSeconds(Prescription p) {
    long durationInMilliseconds = p.getFulfilmentDate().getTime() - p.getEntryDate().getTime();
    long durationInSeconds = durationInMilliseconds / 1000;
    return BigInteger.valueOf(durationInSeconds);
  }
}
