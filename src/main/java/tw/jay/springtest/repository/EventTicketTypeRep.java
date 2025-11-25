package tw.jay.springtest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tw.jay.springtest.entity.EventTicketType;

public interface EventTicketTypeRep extends JpaRepository<EventTicketType, Long> {
    // 依 event_id 查詢
    List<EventTicketType> findByEventId(Long eventId);
    // 依 TicketType 查詢
    List<EventTicketType> findByTicketTypeId(Long ticketTypeId);
}
