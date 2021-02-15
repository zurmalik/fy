package zur.fyayc.data;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import zur.fyayc.domain.File;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {

    @Query(value = "SELECT * FROM files WHERE status <> :statusVal ORDER BY last_modified ASC", nativeQuery = true)
    List<File> findAllProcessableFiles(String statusVal);

}
