package zur.fyayc.batch;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import zur.fyayc.data.OrderRepository;
import zur.fyayc.domain.Order;

@Slf4j
@Component
@StepScope
public class OrderItemWriter implements ItemWriter<Order> {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public void write(List<? extends Order> orders) throws Exception {
        log.info("Writing orders: {}", orders);

        orderRepository.saveAll(orders);
        orderRepository.flush();

        // TODO F 1 Send HTTP requests to the API Specified and Handle responses
        // 201 Order created
        // 429 Your request is throttled, please retry
        // 500 Internal error
    }

}
