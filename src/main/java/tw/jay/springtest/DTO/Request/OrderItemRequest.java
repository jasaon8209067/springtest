package tw.jay.springtest.DTO.Request;

import java.math.BigDecimal;

import lombok.Data;
@Data
public class OrderItemRequest {
    private Long eventTicketTypeId;
    private int quantity;
    private BigDecimal price;

}
