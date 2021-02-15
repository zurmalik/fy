package zur.fyayc.web;

import javax.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zur.fyayc.domain.File;
import zur.fyayc.web.requests.CreateFileRequest;
import zur.fyayc.web.service.FileService;
import zur.fyayc.web.service.FileServiceImpl;

/**
 * This is a mechanism to get information from AWS to this (Spring boot application).
 */
@RestController
@RequestMapping(path = "/files", produces = MediaType.APPLICATION_JSON_VALUE)
public class FileController {

    private final FileService fileService;

    public FileController(FileServiceImpl fileService) {
        this.fileService = fileService;
    }

    @GetMapping
    Page<File> getFileEntries(Pageable pageable) {
        return fileService.getFiles(pageable);
    }

    @PutMapping
    ResponseEntity<File> createFileEntry(@Valid @RequestBody CreateFileRequest createFileRequest) {
        File file = fileService.addFile(createFileRequest);
        return new ResponseEntity<>(file, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
