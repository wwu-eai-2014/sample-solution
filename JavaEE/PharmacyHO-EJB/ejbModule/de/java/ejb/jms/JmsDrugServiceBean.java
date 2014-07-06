package de.java.ejb.jms;

import javax.annotation.Resource;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.Topic;

import de.java.domain.Drug;

@Stateless
public class JmsDrugServiceBean extends AbstractJmsBean implements JmsDrugService {
  
  private static final String CREATE = "create";
  private static final String UPDATE = "update";
  
  @Resource(lookup = "java:/topic/DrugActions")
  private Topic drugActionsTopic;

  @Override
  public void createAtSubsidiaries(Drug drug) {
    try {
      final Session sesion = getConnection().createSession(false, Session.AUTO_ACKNOWLEDGE);
      
      try {
        BytesMessage message = sesion.createBytesMessage();
        message.setStringProperty("operation", CREATE);
        message.writeInt(drug.getPzn());
        message.writeUTF(drug.getName());
        message.writeUTF(drug.getDescription());
        sesion.createProducer(drugActionsTopic).send(message);
        log.debug("Message send");
      } finally {
      }
    } catch (JMSException e) {
      log.error("Tried to send JMS message", e);
      throw new EJBException(e);
    }
  }

  @Override
  public void updateMasterDataAtSubsidiaries(Drug drug) {
    try {
      final Session sesion = getConnection().createSession(false, Session.AUTO_ACKNOWLEDGE);
      
      try {
        BytesMessage message = sesion.createBytesMessage();
        message.setStringProperty("operation", UPDATE);
        message.writeInt(drug.getPzn());
        message.writeUTF(drug.getName());
        message.writeUTF(drug.getDescription());
        sesion.createProducer(drugActionsTopic).send(message);
        log.debug("Message send");
      } finally {
      }
    } catch (JMSException e) {
      log.error("Tried to send JMS message", e);
      throw new EJBException(e);
    }
  }

}
