package tw.jay.springtest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.transaction.Transactional;
import tw.jay.springtest.entity.Inventory;

public interface InventoryRepo extends JpaRepository<Inventory, Long> {

    //找到指定票種的庫存
    Inventory findByTicketTypeId(Long ticketType);

    //原子操作扣庫存存
    @Modifying
    @Transactional
    @Query("""
            UPDATE Inventory i 
            SET i.availableTic = i.availableTic - :qty
            WHERE i.ticketType.id = :ticketTypeId 
            AND i.availableTic >= :qty
                   """)
    int decreaseStock(@Param("ticketTypeId") Long ticketTypeId, 
                      @Param("qty")int qty);


    //原子操作恢復庫存
    @Transactional
    @Modifying
    @Query("update Inventory i set i.availableTic = i.availableTic + :qty where i.ticketType.id = :ticketTypeId")
    int increaseStock(Long ticketTypeId, int qty);


}
