package org.kostenko.example.jms.duplicate;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.jms.JMSDestinationDefinition;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 *
 * @author kostenko
 */

@JMSDestinationDefinition(
        name = DuplicateJMSTestBean.DUPLICATE_QUEUE,
        interfaceName = "javax.jms.Queue"
)
@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = DuplicateJMSTestBean.DUPLICATE_QUEUE),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class DuplicateJMSTestBean implements MessageListener {

    public final static String DUPLICATE_QUEUE = "java:global/jms/duplicateTestQueue";
    
    @Override
    public void onMessage(Message msg) {
        System.out.println("Got new message.");
        MessageStorage.messages.add(msg);
        try {
            Thread.sleep(5_000l);
        } catch(Exception ignore) {}
        System.out.println("Message  successfully processed");
    }
}
