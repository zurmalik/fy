package zur.fyayc.web.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import zur.fyayc.domain.File;
import zur.fyayc.web.requests.CreateFileRequest;

public interface FileService {

    File addFile(CreateFileRequest createFileRequest);

    Page<File> getFiles(final Pageable pageable);

}
