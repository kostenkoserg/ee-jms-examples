package org.kostenko.example.jms.transaction;

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
        name = TxNotSupportedMessagerDrivenBean.TX_NOTSUPPORTED_QUEUE,
        interfaceName = "javax.jms.Queue"
)
@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = TxNotSupportedMessagerDrivenBean.TX_NOTSUPPORTED_QUEUE),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class TxNotSupportedMessagerDrivenBean implements MessageListener {

    public final static String TX_NOTSUPPORTED_QUEUE = "java:global/jms/txNotSupportedQueue";
    
    @Override
    public void onMessage(Message msg) {
        System.out.println("Got new message.");
        try {
            System.out.println("Hello TxNotSupportedMessagerDrivenBean!");
            Thread.sleep(60_000l);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("Message  successfully processed");
    }
}
