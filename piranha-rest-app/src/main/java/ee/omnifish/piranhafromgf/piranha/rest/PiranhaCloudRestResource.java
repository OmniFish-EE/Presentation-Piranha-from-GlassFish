package ee.omnifish.piranhafromgf.piranha.rest;

import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/")
@RequestScoped
public class PiranhaCloudRestResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String helloWorld() {
        return "Hello from Piranha Cloud REST service!";
    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public String helloWorld(Person input) {
        return "Hello, %s, from Piranha Cloud REST service!".formatted(input.getName());
    }
}