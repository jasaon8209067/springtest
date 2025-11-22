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

import tw.jay.springtest.entity.Inventory;
import tw.jay.springtest.entity.TicketType;
import tw.jay.springtest.service.InventorySer;
import tw.jay.springtest.service.TicketTypeService;

@RestController
@RequestMapping("/inventory")
public class InventoryController {
    @Autowired
    private InventorySer inventorySer;

    @Autowired
    private TicketTypeService ticketTypeService;

    // 查詢所有庫存
    @GetMapping("/api/inventory/all")
    public List<Inventory> getallInventory() {
        return inventorySer.getallinvetory();
    }

    // 查詢單一票種庫存
    @GetMapping("/api/inventory/check/{ticketTypeId}")
    public Inventory getInventory(@PathVariable Long ticketTypeId) {
        return inventorySer.getInventory(ticketTypeId);
    }

    // 新增庫存
    @PostMapping("/api/inventory/add")
    public Inventory addInventory(@RequestBody Map<String, Object> payload) {
        Long ticketTypeId = Long.valueOf(payload.get("ticketTypeId").toString());
        int quantity = Integer.parseInt(payload.get("quantity").toString());
        TicketType ticketType = ticketTypeService.findById(ticketTypeId);
        return inventorySer.addInventory(ticketType, quantity);
    }
}