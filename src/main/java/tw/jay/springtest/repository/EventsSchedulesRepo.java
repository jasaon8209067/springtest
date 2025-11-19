package tw.jay.springtest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tw.jay.springtest.entity.EventSchedules;
@Repository
public interface EventsSchedulesRepo extends JpaRepository<EventSchedules, Integer> {
    
}
