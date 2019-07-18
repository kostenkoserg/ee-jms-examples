package org.kostenko.example.jms;


import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 *
 * @author kostenko
 */
@Stateless
public class ClusterDemoEndpoint {

    @Inject
    private JMSContext context;
    @Resource(lookup = "java:comp/jms/duplicateTestQueue")
    private Queue queue;

    @GET
    @Path("/sendmessage")
    public Response sendMessage() {

        try {
            context.createProducer().send(queue, "aaa");
            
            return Response.ok().entity("Recieved messagges: " + DuplicateTestBean.messages).build();
        } catch (Exception e) {
            return Response.ok().entity("Error: " + e).build();
        }
        
        
    }
}
