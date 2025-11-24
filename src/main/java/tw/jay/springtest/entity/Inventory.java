package tw.jay.springtest.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "inventory") // 處理庫存

public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    //Inventory 要怎麼連結 TicketType？使用 @ManyToOne + ticket_type_id FK
    @ManyToOne
    @JoinColumn(name = "ticket_type_id", nullable = false)
    private TicketType ticketType;


    private int totalTic;// 票總庫存量
    private int availableTic;// 票可售數量(動態)
    private LocalDateTime lastUpdated;


    public TicketType getTicketType() {
        return ticketType;
    }

    public void setTicketType(TicketType ticketType2) {
        this.ticketType = ticketType2;
    }

    public int getTotalTic() {
        return totalTic;
    }

    public void setTotalTic(int totalTic) {
        this.totalTic = totalTic;
    }

    public int getAvailableTic() {
        return availableTic;
    }

    public void setAvailableTic(int availableTic) {
        this.availableTic = availableTic;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
