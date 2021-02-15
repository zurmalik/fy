package zur.fyayc.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zur.fyayc.domain.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    // out of box functions will be used.
}
