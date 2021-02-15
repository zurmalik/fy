package zur.fyayc.batch;

import java.util.Date;
import java.util.List;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import zur.fyayc.data.FileRepository;
import zur.fyayc.domain.File;
import zur.fyayc.domain.Status;

/**
 * This bean schedules and runs our Spring Batch job.
 */
@Component
public class SpringBatchOrderJobLauncher {

    @Autowired
    private Job orderJob;
    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private FileRepository fileRepository;

    public void runSpringBatchOrdersJob() {

        List<File> files = getFilesWithUpdatedTryCount();

        for (File file : files) {

            try {

                jobLauncher.run(orderJob, newExecution(file));

            } catch (
                JobExecutionAlreadyRunningException |
                JobRestartException |
                JobInstanceAlreadyCompleteException |
                JobParametersInvalidException

                e // TODO I 8 Catch more specific exceptions
            ) {
                e.printStackTrace();
            }

        }

    }

    private List<File> getFilesWithUpdatedTryCount() {
        List<File> files = fileRepository.findAllProcessableFiles(Status.FAILED.getIndicator());
        files.stream().forEach(file -> file.setRetryCount(file.getRetryCount() + 1));
        fileRepository.saveAll(files);
        fileRepository.flush();

        return files;
    }

    private static JobParameters newExecution(File file) {
        JobParametersBuilder jobBuilder = new JobParametersBuilder();
        jobBuilder.addDate("date", new Date());
        jobBuilder.addString("filePath", file.getFullPath());

        return jobBuilder.toJobParameters();
    }

}
