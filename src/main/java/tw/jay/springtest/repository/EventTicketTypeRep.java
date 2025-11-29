package tw.jay.springtest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import jakarta.transaction.Transactional;
import tw.jay.springtest.entity.EventTicketType;

public interface EventTicketTypeRep extends JpaRepository<EventTicketType, Long> {
    // 依 event_id 查詢
    List<EventTicketType> findByEventId(Long eventId);
    // 依 TicketType 查詢
    List<EventTicketType> findByTicketTypeId(Long ticketTypeId);

    // 庫存操作

    //減少庫存
    @Modifying
    @Transactional
    @Query("UPDATE EventTicketType ett SET ett.customlimit = ett.customlimit - :quantity WHERE ett.id = :id AND ett.customlimit >= :quantity")
    int decreaseStock(Long id, int quantity);//這是一個樂觀鎖的變體，只有當`customlimit >= :quantity`時才會執行更新

    //增加庫存
    @Modifying
    @Transactional
    @Query("UPDATE EventTicketType ett SET ett.customlimit = ett.customlimit + :quantity WHERE ett.id = :id")
    int increaseStock(Long id, int quantity);
}