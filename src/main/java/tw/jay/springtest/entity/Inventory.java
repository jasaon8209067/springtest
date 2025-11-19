package tw.jay.springtest.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Table(name = "inventory")//處理庫存
@Data
public class Inventory {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private int scheduId;
    private int ticketTypeId;

    private Integer total;//總庫存量
    private Integer available;//可售數量(動態)
}
