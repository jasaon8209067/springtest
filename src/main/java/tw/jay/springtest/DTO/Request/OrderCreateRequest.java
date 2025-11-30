package tw.jay.springtest.DTO.Request;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;
@Data
public class OrderCreateRequest {
    private Long eventId;
    private BigDecimal totalAmount;
    private String buyerName;

    private List<OrderItemRequest> items;
}
