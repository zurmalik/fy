package zur.fyayc.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import zur.fyayc.domain.Order;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    private static final int ORDERS_CHUNK_SIZE = 1000;

    @Bean
    public Step orderJobStep(
        OrderFileItemReader pOrderFileItemReader,
        OrderItemWriter pOrderItemWriter,
        StepBuilderFactory pStepBuilderFactory
    ) {
        return pStepBuilderFactory
            .get("orderJobStep")
            .<Order, Order>chunk(ORDERS_CHUNK_SIZE)
            .reader(pOrderFileItemReader)
            .writer(pOrderItemWriter)
            .build();
    }

    @Bean
    public Job orderJob(Step orderJobStep, JobBuilderFactory jobBuilderFactory) {
        return jobBuilderFactory
                .get("orderJob")
                .incrementer(new RunIdIncrementer())
                .flow(orderJobStep)
                .end()
                .build();
    }

    @Bean
    public LineMapper<Order> createOrderLineMapper() {
        DefaultLineMapper<Order> orderLineMapper = new DefaultLineMapper<>();

        LineTokenizer orderLineTokenizer = createOrderLineTokenizer();
        orderLineMapper.setLineTokenizer(orderLineTokenizer);

        FieldSetMapper<Order> orderInformationMapper = createOrderInformationMapper();
        orderLineMapper.setFieldSetMapper(orderInformationMapper);

        return orderLineMapper;
    }

    @Bean
    public LineTokenizer createOrderLineTokenizer() {
        DelimitedLineTokenizer orderLineTokenizer = new DelimitedLineTokenizer();
        orderLineTokenizer.setDelimiter(",");
        orderLineTokenizer.setNames(new String[] {"orderId", "orderDate", "userEmail", "orderLines"});
        return orderLineTokenizer;
    }

    @Bean
    public FieldSetMapper<Order> createOrderInformationMapper() {
        BeanWrapperFieldSetMapper<Order> orderInformationMapper = new BeanWrapperFieldSetMapper<>();
        orderInformationMapper.setTargetType(Order.class);
        return orderInformationMapper;
    }

}
