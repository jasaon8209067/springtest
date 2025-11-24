package tw.jay.springtest.DTO.Response;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ErrorResponse {
    private int status;     //HTTP狀態碼
    private String message; //錯誤訊息
    private long timestamp; //發生時間
}
