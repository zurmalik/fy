package zur.fyayc.web.service;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import zur.fyayc.data.FileRepository;
import zur.fyayc.domain.File;
import zur.fyayc.util.AdapterToFile;
import zur.fyayc.web.requests.CreateFileRequest;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private AdapterToFile adapterToFile;

    @Override
    public File addFile(@Valid CreateFileRequest createFileRequest) {
        File insertableFile = adapterToFile.convert(createFileRequest);
        File insertedPost = fileRepository.saveAndFlush(insertableFile);
        return insertedPost;
    }

    @Override
    public Page<File> getFiles(Pageable pageable) {
        return fileRepository.findAll(pageable);
    }
}
