package tw.jay.springtest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tw.jay.springtest.DTO.Request.OrderCreateRequest;
import tw.jay.springtest.DTO.Response.OrderResponse;
import tw.jay.springtest.service.OrdersService;

@RestController
@RequestMapping("/api/orders")
public class OrdersController {
    @Autowired
    private OrdersService ordersService;

    //建立訂單並扣庫存
    @PostMapping
    public ResponseEntity<?> checkout(@RequestBody OrderCreateRequest request){
        try {
            //執行原子性的創建訂單和扣庫存
            OrderResponse response = ordersService.createOrderAndDecreaseStock(request);
            //返回201created狀態碼
            return new ResponseEntity<>(response,HttpStatus.CREATED);

        } catch(RuntimeException e){
            //處理庫存不足，ID錯誤時異常
            return ResponseEntity.badRequest().body("結帳失敗: "+ e.getMessage());
        } catch (Exception e) {
            //處理內部錯誤
            return ResponseEntity.internalServerError().body("伺服器錯誤: " + e.getMessage());
        }
    }
}
