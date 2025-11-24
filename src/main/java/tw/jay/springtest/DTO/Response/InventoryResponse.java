package tw.jay.springtest.DTO.Response;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class InventoryResponse {
    private Long id;
    private Long ticketTypeId;
    private String ticketTypeName;

    private int totalTic;
    private int availableTic;
    private LocalDateTime lastUpdated;

}
