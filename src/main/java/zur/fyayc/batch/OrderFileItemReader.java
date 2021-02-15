package zur.fyayc.batch;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import zur.fyayc.domain.Order;

@Component
@StepScope
public class OrderFileItemReader extends FlatFileItemReader<Order> {

    @Autowired
    public OrderFileItemReader(LineMapper<Order> createOrderLineMapper) {
        super();

        setLineMapper(createOrderLineMapper);
    }

    @Value("#{jobParameters['filePath']}")
    public void setFileName(final String filePath) {
        // TODO F 3 Check how to provide a file reader that reads from AWS without downloading the file.
        // TODO F 4 How to trigger Spring MVC controller so that the processing starts e.g. from Lambda
        setResource(new ClassPathResource(filePath));
    }

}
