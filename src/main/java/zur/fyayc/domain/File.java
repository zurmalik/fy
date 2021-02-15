package zur.fyayc.domain;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
@Table(name = "files")
public class File {

    // TODO I 2 Resume (RecoveryFromFailure) will require a trigger to be defined as well. in memory non-persistent DB will
    // not need it. This aspect has been hinted on to share that this dimension will need to be taken care of once the
    // retry is required.
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orders_gen_seq")
    @SequenceGenerator(name = "orders_gen_seq", sequenceName = "orders_seq", initialValue = 1, allocationSize = 50)
    private Long id;

    @Column(name = "full_path")
    private String fullPath;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_modified")
    private Date lastModified;

    @Column(name = "status")
    private String status;

    @Column(name = "retry_count")
    private int retryCount = 0;

    public void setStatus(String statusString) {
        status = Status.fromString(statusString).getIndicator();
    }

}
