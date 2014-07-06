package de.java.ejb.jms;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.BytesMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

import de.java.domain.Drug;
import de.java.ejb.DrugService;

@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
    @ActivationConfigProperty(propertyName = "destination", propertyValue = "topic/DrugActions"),
    @ActivationConfigProperty(propertyName = "clientId", propertyValue = "JaVa"),
    @ActivationConfigProperty(propertyName = "DLQMaxResent", propertyValue = "10"),
    @ActivationConfigProperty(propertyName = "subscriptionDurability", propertyValue = "Durable"),
    @ActivationConfigProperty(propertyName = "subscriptionName" , propertyValue = "JaVaDrugMessageListener"),
})
public class DrugActionMDB extends AbstractJmsBean implements MessageListener {

  @EJB
  DrugService service;

  private static final String CREATE = "create";
  private static final String UPDATE = "update";

  @Override
  public void onMessage(Message incomingMessage) {
    try {
      BytesMessage msg = (BytesMessage) incomingMessage;
      String operation = msg.getStringProperty("operation");
      
      int pzn = msg.readInt();
      String name = msg.readUTF();
      String description = msg.readUTF();
      
      switch (operation) {
        case CREATE: createDrug(pzn, name, description); break;
        case UPDATE: updateDrug(pzn, name, description); break;
        default: log.warn(String.format("Received unknown operation: %s", operation));
      }
    } catch (Exception e) {
      log.error("Error while processing message", e);
    }
  }

  private void createDrug(int pzn, String name, String description) {
    Drug newDrug = new Drug();
    newDrug.setPzn(pzn);
    newDrug.setName(name);
    newDrug.setDescription(description);
    service.createDrug(newDrug);
  }
  
  private void updateDrug(int pzn, String name, String description) {
    service.updateMasterData(pzn, name, description);
  }
  
}
