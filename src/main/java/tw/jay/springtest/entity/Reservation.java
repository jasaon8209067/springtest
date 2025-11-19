package tw.jay.springtest.entity;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "reservations")//預定
@Data
public class Reservation {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String clientId;
    private int userId;

    private int scheduleId;
    private String status;// PENDING / EXPIRED / CONFIRMED

    private LocalDateTime expiresAt;//過期時間
    private int totalAmount;
    private LocalDateTime createdAt;//建立時間
}

