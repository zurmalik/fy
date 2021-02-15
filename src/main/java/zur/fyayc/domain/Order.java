package zur.fyayc.domain;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Contains the information that's read from the CSV file.
 */
@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "orders")
public class Order {

    // TODO I 1 Resume (RecoveryFromFailure) will require a trigger to be defined as well. In memory non-persistent DB will
    // not need it. This aspect has been hinted on to share that this dimension will need to be taken care of once the
    // retry is required.
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orders_gen_seq")
    @SequenceGenerator(name = "orders_gen_seq", sequenceName = "orders_seq", initialValue = 1, allocationSize = 50)
    private Long id;

    @Column(name = "order_id")
    private String orderId;

    @Column(name = "order_date")
    private String orderDate;

    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "order_lines_str")
    private String orderLinesStr;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_modified")
    private Date lastModified;

    @Column(name = "status")
    private Status status;

    @Column(name = "retry_count")
    private int retryCount = 0;

    @Transient
    private List<OrderLine> orderLines;
    @Transient
    private LinkedHashMap<String, String> dates;

    public void setOrderDate(String pOrderDate) {
        orderDate = pOrderDate;

        // TODO Q 1 Ask : In Documentation it says it is ordered map. What does that mean ?Since having a map would mean
        // to not have duplicates. So I wonder if they really mean a hash map kind of a thing. Check what do they need
        // as a key and value.
        dates = Arrays.stream(orderDate.split(";"))
                .collect(Collectors.toMap(Function.identity(), Function.identity(), (u, v) -> u, LinkedHashMap::new));

    }

    public void setOrderLines(String pOrderLines) {

        orderLinesStr = pOrderLines;

        orderLines = Arrays
            .stream(orderLinesStr.split(";"))
            .map(
                orderLinesPairToken -> {
                    String[] orderLineAttributesTokens = orderLinesPairToken.split("\\|");
                    return new OrderLine(orderLineAttributesTokens[0], Double.parseDouble(orderLineAttributesTokens[1]));
                }
            ).collect(Collectors.toList());
    }

    public void setStatus(String statusString) {
        if (retryCount < Status.MAX_RETIES_ALLOWED) {
            status = Status.fromString(statusString);
        } else {
            status = Status.FAILED;
        }
    }

}
