package org.kostenko.example.jms.duplicate;


import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

/**
 *
 * @author kostenko
 */
@Path("/")
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class DuplicateTestEndpoint {

    @Inject
    private JMSContext context;
    @Resource(lookup = DuplicateJMSTestBean.DUPLICATE_QUEUE)
    private Queue queue;

    @GET
    @Path("/sendmessage")
    public Response sendMessage(@QueryParam("duplicate-id") String duplicateId) {

        try {
            
            ObjectMessage message = context.createObjectMessage();
            
            if (duplicateId == null) {
                context.createProducer().send(queue, message);
            } else {
                message.setStringProperty("_AMQ_DUPL_ID", duplicateId);
                context.createProducer().send(queue, message);
            }
            
            return Response.ok().entity("Message was sent.  Recieved " + MessageStorage.messages.size() + " messagges: " + MessageStorage.messages).build();
        } catch (Throwable e) {
            return Response.ok().entity("Error: " + e).build();
        }
    }
}
