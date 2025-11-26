package tw.jay.springtest.DTO.Response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;
@Data
public class EventTicketTypeResponse {
    private Long id;
    private Long eventId;
    private String ticketType;
    private Boolean islimited;
    private BigDecimal customprice;
    private Integer customlimit;
    private LocalDateTime createdat;

    private String remark;
}
