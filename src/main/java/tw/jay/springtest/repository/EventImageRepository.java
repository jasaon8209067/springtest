package tw.jay.springtest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tw.jay.springtest.entity.EventImage;

public interface EventImageRepository extends JpaRepository<EventImage, Long> {
    List<EventImage> findByEventId(Long eventId);
}
