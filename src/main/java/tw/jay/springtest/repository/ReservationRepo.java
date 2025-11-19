package tw.jay.springtest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tw.jay.springtest.entity.Reservation;

public interface ReservationRepo extends JpaRepository<Reservation, Long> {
    
}
