package tw.jay.springtest.DTO.Request;

import lombok.Data;

@Data
public class CreateUpdateInventoryRequest {
    private Long ticketTypeId;
    private Integer totalTic;//用於增加庫存
    private Integer quantity;//用於扣庫存/回補 
}
