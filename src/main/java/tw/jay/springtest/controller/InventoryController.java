package tw.jay.springtest.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tw.jay.springtest.DTO.Request.CreateUpdateInventoryRequest;
import tw.jay.springtest.DTO.Response.InventoryResponse;
import tw.jay.springtest.service.InventorySer;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {
    @Autowired
    private InventorySer inventorySer;

    // @Autowired
    // private TicketTypeService ticketTypeService;

    // 查詢所有庫存
    @GetMapping("/all")
    public List<InventoryResponse> getAllInventory() {
        return inventorySer.getAllInventory();
    }

    // 查詢單一票種庫存
    @GetMapping("check/{ticketTypeId}")
    public InventoryResponse getInventory(@PathVariable Long ticketTypeId) {
        return inventorySer.getInventory(ticketTypeId);
    }

    //增庫存
    @PostMapping("/addinventory")
    public InventoryResponse addInventory(@RequestBody CreateUpdateInventoryRequest request) {
        return inventorySer.addInventory(request);
    }

    //點擊前往結帳->扣庫存
    @PostMapping("/decreaseinventory")
    public Map<String, Object> decrease(@RequestBody CreateUpdateInventoryRequest request){        
        boolean ok = inventorySer.decreaseStock(request);

        if(!ok){
            return Map.of(
                "success",false,
                "message","庫存不足"
            );
        }
        return Map.of(
            "success", true,
            "message", "成功扣庫存"
        );
    }


    //結帳失敗->回補庫存
    @PostMapping("/restore")
    public Map<String, Object> restore(@RequestBody CreateUpdateInventoryRequest request) {
        inventorySer.restoreStock(request);

        return Map.of(
            "success", true,
            "message", "已回補庫存"
        );
    }

    // 一次扣多筆庫存（batch）
    @PostMapping("/decrease-multiple")
    public Map<String, Object> decreaseMultiple(@RequestBody List<CreateUpdateInventoryRequest> requests) {
        try {
            boolean ok = inventorySer.decreaseMultipleStock(requests);
            if (!ok) {
                return Map.of("success", false, "message", "部分或全部扣庫存失敗");
            }
            return Map.of("success", true, "message", "成功扣除所有票種庫存");
        } catch (RuntimeException ex) {
            return Map.of("success", false, "message", ex.getMessage());
        } catch (Exception ex) {
            return Map.of("success", false, "message", "伺服器錯誤，請稍後再試");
        }
    }

    // 一次回補多筆庫存（batch restore）
    @PostMapping("/restore-multiple")
    public Map<String, Object> restoreMultiple(@RequestBody List<CreateUpdateInventoryRequest> requests) {
        try {
            inventorySer.restoreMultipleStock(requests);
            return Map.of("success", true, "message", "已回補多筆庫存");
        } catch (Exception ex) {
            return Map.of("success", false, "message", "回補發生錯誤");
        }
    }









}