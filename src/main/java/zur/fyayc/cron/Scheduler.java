package zur.fyayc.cron;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import zur.fyayc.batch.SpringBatchOrderJobLauncher;
import zur.fyayc.outbound.rest.OrderOutboundAPI;

@Configuration
@EnableScheduling
public class Scheduler {

    @Autowired
    SpringBatchOrderJobLauncher springBatchOrderJobLauncher;

    // TODO I 3 Remove this. Temp declaration to test put call directly without needing to run batch job first.
    @Autowired
    private OrderOutboundAPI orderOutboundAPI;

    // TODO I 4 Remove after debuggin. To ease debugging
    private boolean runSpringBatchOrdersJobFlag = true;
    private boolean runOrderOutboundAPIFlag = false;

    @Scheduled(cron="${fyayc.batch.orders.schedule}")
    public void runSpringBatchOrdersJob() {
        if (runSpringBatchOrdersJobFlag) {
            runSpringBatchOrdersJobFlag = false;
            springBatchOrderJobLauncher.runSpringBatchOrdersJob();
            runSpringBatchOrdersJobFlag = true;
        }
    }

    @Scheduled(cron="${fyayc.outbound.api.orders.schedule}")
    public void runOrderOutboundAPI() {
        if (runOrderOutboundAPIFlag) {
            runOrderOutboundAPIFlag = false;
            orderOutboundAPI.sendOrderRequest();
        }
    }

}
