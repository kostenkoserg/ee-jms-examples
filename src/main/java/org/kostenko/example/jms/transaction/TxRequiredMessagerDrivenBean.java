package org.kostenko.example.jms.transaction;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
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
        name = TxRequiredMessagerDrivenBean.TX_REQUIRED_QUEUE,
        interfaceName = "javax.jms.Queue"
)
@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = TxRequiredMessagerDrivenBean.TX_REQUIRED_QUEUE),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class TxRequiredMessagerDrivenBean implements MessageListener {

    public final static String TX_REQUIRED_QUEUE = "java:global/jms/txRequiredQueue";
    
    @Resource
    MessageDrivenContext messageDrivenContext;

    @Override
    public void onMessage(Message msg) {
        System.out.println("Got new message.");
        try {
            System.out.println("Hello TxRequiredMessagerDrivenBean!");
            for (int x = 1; x < 40; x++) {
                Thread.sleep(10_000l);
                System.out.println("Long transaction: " + (10 * x) + " sec.");
            }
        } catch (Exception ex) {
            System.err.println(ex);
            messageDrivenContext.setRollbackOnly();
        }
        System.out.println("Message  successfully processed");
    }
}
