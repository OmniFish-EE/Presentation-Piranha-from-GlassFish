package ee.omnifish.piranhafromgf.piranha;

import cloud.piranha.embedded.EmbeddedPiranha;
import cloud.piranha.embedded.EmbeddedPiranhaBuilder;
import cloud.piranha.embedded.EmbeddedRequest;
import cloud.piranha.embedded.EmbeddedRequestBuilder;
import cloud.piranha.embedded.EmbeddedResponse;
import static ee.omnifish.piranhafromgf.piranha.PiranhaApp.ClassDataSharing;
import jakarta.servlet.ServletException;
import java.io.IOException;

public class PiranhaMinimalApp {

    public static void main(String[] args) throws IOException, ServletException {
        ClassDataSharing.init(args);
        EmbeddedPiranha piranha = new EmbeddedPiranhaBuilder()
                .servletMapped(PiranhaSimpleServlet.class, "/")
                .build();

        piranha.start();

        EmbeddedRequest request = new EmbeddedRequestBuilder().build();
        EmbeddedResponse response = piranha.service(request);

        ClassDataSharing.shutdownIfCDS(null, piranha);

        System.out.println(response.getResponseAsString());

        System.out.println("Application started!");

        piranha.stop()
                .destroy();

    }

}
