package org.kostenko.example.jms.transaction;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.jms.JMSContext;
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
public class TransactionTestEndpoint {

    @Inject
    private JMSContext context;
    
    @Resource(lookup = TxRequiredMessagerDrivenBean.TX_REQUIRED_QUEUE)
    private Queue queue;

    @GET
    @Path("/start-tx-and-send-message")
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Response sendMessage(@QueryParam("value") String duplicateId) {
        context.createProducer().send(queue, "TX-MESSAGE");
        return Response.ok().entity("Message was sent.").build();
    }
}
