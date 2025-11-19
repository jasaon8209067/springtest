package tw.jay.springtest.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "events")
@Data
public class Events {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String detail;
    private String date;
    private String location;
    
    private LocalDate startDate;//型別設定為LocalDate 不含時間、不含時區 → 不會因為部署地變動而產生日期跑掉的情況。
    private LocalDate endDate;
    private LocalDateTime salesStart;//LocalDateTime 也不帶時區 → 適合表示單純的“當地時間”。
    
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] image;

}
