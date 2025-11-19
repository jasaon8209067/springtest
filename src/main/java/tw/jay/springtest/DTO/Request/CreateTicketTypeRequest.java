package tw.jay.springtest.DTO.Request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateTicketTypeRequest {
    @NotBlank(message = "Name canot be empty")
    @Size(max = 50, message = "Name must be less than 50 characters")
    private String name;

    @Min(value = 0, message = "Price must be a positive number")
    private int price;
    
    private String remark;
    private boolean status;

}
