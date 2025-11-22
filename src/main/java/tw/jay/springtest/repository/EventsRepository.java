package tw.jay.springtest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tw.jay.springtest.entity.Events;

@Repository
public interface EventsRepository extends JpaRepository<Events, Long> {
    // List<Events> findByActiveTrue();
}
