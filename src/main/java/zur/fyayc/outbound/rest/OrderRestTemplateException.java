package zur.fyayc.outbound.rest;

import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

/**
 * TODO I 6 See if this is required.
 */
@Getter
@ToString
public class OrderRestTemplateException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private HttpStatus statusCode;
    private String error;

    public OrderRestTemplateException(HttpStatus statusCode, String error) {
        super(error);
        this.statusCode = statusCode;
        this.error = error;
    }

}
