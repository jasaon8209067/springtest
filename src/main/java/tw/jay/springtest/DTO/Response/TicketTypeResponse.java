package tw.jay.springtest.DTO.Response;

import lombok.Data;

@Data
public class TicketTypeResponse {
    private Long id;
    private String name;
    private int price;
    private String remark;
    private boolean status;
}
