package tw.jay.springtest.DTO.Response;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class OrderResponse {
    private Long orderId;
    private String status;
    private String redirectUrl;
    private LocalDateTime createdAt;
}
