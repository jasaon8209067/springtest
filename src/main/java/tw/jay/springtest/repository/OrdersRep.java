package tw.jay.springtest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tw.jay.springtest.entity.Orders;


@Repository
public interface OrdersRep extends JpaRepository<Orders, Long>{
    
}
