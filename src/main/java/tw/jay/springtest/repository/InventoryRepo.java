package tw.jay.springtest.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;
import tw.jay.springtest.entity.Inventory;

public interface InventoryRepo extends JpaRepository<Inventory, Long> {

    // 透過 ticket_type 查庫存
    Optional<Inventory> findByTicketTypeId(Long ticketTypeId);

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
    int increaseStock(@Param("ticketTypeId") Long ticketTypeId,@Param("qty") int qty);


    

    // 使用悲觀鎖防止超賣
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT i FROM Inventory i WHERE i.ticketType.id = :ticketTypeId")
    Optional<Inventory> lockInventoryForUpdate(Long ticketTypeId);
}



