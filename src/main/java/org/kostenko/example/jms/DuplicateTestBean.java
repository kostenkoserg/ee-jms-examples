package org.kostenko.example.jms;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 *
 * @author kostenko
 */
@MessageDriven(mappedName="jms/Queue", activationConfig =  {
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")})

public class DuplicateTestBean implements MessageListener {
    
    public static List messages = new ArrayList();

    @Override
    public void onMessage(Message msg) {
        System.out.println("Got new message.");
        
        try {
            Thread.sleep(5_000l);
        } catch(Exception ignore) {}
        
        System.out.println("Message  successfully processed");
    }
    
}
