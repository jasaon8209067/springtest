package tw.jay.springtest.DTO.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

//新增用
@Data
public class CreateTicketTypeRequest {
    @NotBlank(message = "票種名稱不能空白")
    private String name;
    
    @Positive(message = "價格必須大於 0")
    private int price;
    
    private String remark;
    
    @NotNull(message = "狀態不能為空")
    private Boolean status;

}
