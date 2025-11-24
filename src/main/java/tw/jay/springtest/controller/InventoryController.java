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

}