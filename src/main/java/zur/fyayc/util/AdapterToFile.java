package zur.fyayc.util;

import java.util.Date;
import org.springframework.stereotype.Service;
import zur.fyayc.domain.File;
import zur.fyayc.domain.Status;
import zur.fyayc.web.requests.CreateFileRequest;

@Service
public class AdapterToFile extends Adapter<CreateFileRequest, File> {

    @Override
    public File convert(CreateFileRequest createFileRequest) {

        File file = new File();

        file.setFullPath(createFileRequest.getFullPath());
        file.setLastModified(new Date());
        file.setRetryCount(0);
        file.setStatus(getStatusIndicator(createFileRequest));

        return file;
    }

    private static String getStatusIndicator(CreateFileRequest createFileRequest) {

        String status = Status.RECEIVED.getIndicator();

        if (createFileRequest.getStatus() != null) {
            status = Status.fromString(createFileRequest.getStatus()).getIndicator();
        }

        return status;
    }

}
