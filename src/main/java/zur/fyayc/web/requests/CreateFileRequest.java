package zur.fyayc.web.requests;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateFileRequest {

    @NotEmpty
    @Size(min = 1, max = 200, message = "fullPath must be between 1 and 200 characters")
    private String fullPath;

    private String status;

}
