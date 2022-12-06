package ee.omnifish.piranhafromgf.piranha;

import cloud.piranha.embedded.EmbeddedPiranha;
import cloud.piranha.embedded.EmbeddedPiranhaBuilder;
import cloud.piranha.embedded.EmbeddedRequest;
import cloud.piranha.embedded.EmbeddedRequestBuilder;
import cloud.piranha.embedded.EmbeddedResponse;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import jakarta.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PiranhaFunction implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private static EmbeddedPiranha piranha = startPiranha();

    static EmbeddedPiranha startPiranha() {
        return new EmbeddedPiranhaBuilder()
            .servletMapped(PiranhaLambdaSimpleServlet.class, "/")
            .build()
            .start();
    }

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent awsRequest, Context context) {
        
        EmbeddedRequest request = new EmbeddedRequestBuilder()
                .servletPath(awsRequest.getPath())
                .build();

        try {
            
            final EmbeddedResponse response = piranha.service(request);
            
            return new APIGatewayProxyResponseEvent()
                    .withMultiValueHeaders(piranhaResponseHeadersAsMap(response))
                    .withIsBase64Encoded(false)
                    .withStatusCode(response.getStatus())
                    .withBody(response.getResponseAsString());
        } catch (IOException | ServletException ex) {
            throw new RuntimeException(ex);
        }

    }

    private static Map<String, List<String>> piranhaResponseHeadersAsMap(final EmbeddedResponse response) {
        return response.getHeaderNames()
                .stream().collect(Collectors.toMap(
                        i -> i,
                        i -> new ArrayList<>(response.getHeaders(i))
                ));
    }

}
