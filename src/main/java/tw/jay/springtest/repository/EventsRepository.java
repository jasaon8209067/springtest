package tw.jay.springtest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tw.jay.springtest.entity.Event;


@Repository
public interface EventsRepository extends JpaRepository<Event, Long> {
    }
