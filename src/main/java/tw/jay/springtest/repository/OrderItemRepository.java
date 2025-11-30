package tw.jay.springtest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tw.jay.springtest.entity.OrderItem;
@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long>{
    @Query("SELECT oi FROM OrderItem oi JOIN FETCH oi.eventTicketType WHERE oi.order.id = :orderId")
    List<OrderItem> findByOrder_Id(Long orderId);
}
