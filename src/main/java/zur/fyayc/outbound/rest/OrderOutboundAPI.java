package zur.fyayc.outbound.rest;


import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import zur.fyayc.web.requests.CreateFileRequest;

/**
 * TODO F 2 Ensure that around 400 HTTP Request per second is supported.
 */
@Component
public class OrderOutboundAPI {

    @Value( "${fyayc.orderapi.url}" )
    private String orderAPIURL;

    @Autowired
    private RestTemplate fyaycRestTemplate;

    public void sendOrderRequest() {
        try {

            URI uri = new URI(orderAPIURL);

            CreateFileRequest body = new CreateFileRequest();
            body.setFullPath("data/output-100.csv");
            RequestEntity<CreateFileRequest> requestEntity = RequestEntity
                .put(uri)
                .accept(MediaType.APPLICATION_JSON)
                .body(body);

            fyaycRestTemplate.exchange(requestEntity, Object.class);

        } catch (Exception e) { // TODO I 5 catch more specific exceptions
            e.printStackTrace();
        }

    }

}
