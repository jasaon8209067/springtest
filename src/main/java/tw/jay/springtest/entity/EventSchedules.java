package tw.jay.springtest.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "event_schedules")//活動場次
@Data
public class EventSchedules {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int eventId;
    private LocalDate date;
    private int totalQuota;//總共配額

    private int remainingQuota;//剩餘配額

}
