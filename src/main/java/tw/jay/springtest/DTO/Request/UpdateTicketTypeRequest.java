package tw.jay.springtest.DTO.Request;

import jakarta.validation.constraints.Positive;
import lombok.Data;

//更新用
@Data
public class UpdateTicketTypeRequest {
    private String name;

    @Positive(message = "價格必須大於 0")
    private Integer price;
    private String remark;
    private Boolean status;
    
}
