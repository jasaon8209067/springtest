package tw.jay.springtest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tw.jay.springtest.entity.ReservationItem;

public interface ReservationItemRepo extends JpaRepository<ReservationItem, Long> {
    
}
