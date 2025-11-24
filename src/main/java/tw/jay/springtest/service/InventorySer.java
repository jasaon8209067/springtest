package tw.jay.springtest.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import tw.jay.springtest.DTO.Request.CreateUpdateInventoryRequest;
import tw.jay.springtest.DTO.Response.InventoryResponse;
import tw.jay.springtest.entity.Inventory;
import tw.jay.springtest.entity.TicketType;
import tw.jay.springtest.mapper.InventoryMapper;
import tw.jay.springtest.repository.InventoryRepo;
import tw.jay.springtest.repository.TicketTypeRepository;

@Service
public class InventorySer {
    @Autowired
    private InventoryRepo inventoryRepo;
    @Autowired
    private TicketTypeRepository ticketTypeRepo;
    @Autowired
    private InventoryMapper inventoryMapper;

    // 取得所有庫存
    public List<InventoryResponse> getAllInventory() {
        List<Inventory> list = inventoryRepo.findAll();
        return list.stream()
                .map(inventoryMapper::toResponse)
                .collect(Collectors.toList());
    }

    // 取得單一票種庫存
    public InventoryResponse getInventory(Long ticketTypeId) {
        Inventory inv = inventoryRepo.findByTicketTypeId(ticketTypeId);
        if (inv == null) {
            throw new RuntimeException("該票種庫存不存在");
        }
        return inventoryMapper.toResponse(inv);
    }

    // 增加庫存
    @Transactional
    public InventoryResponse addInventory(CreateUpdateInventoryRequest request) {
        // 用 DTO 的 id 找到 Entity
        TicketType ticketTypeEntity = ticketTypeRepo.findById(request.getTicketTypeId())
                .orElseThrow(() -> new RuntimeException("此票種不存在"));

        Inventory inv = inventoryRepo.findByTicketTypeId(ticketTypeEntity.getId());
        if (inv == null) {
            inv = new Inventory();
            inv.setTicketType(ticketTypeEntity);
            inv.setTotalTic(request.getTotalTic());
            inv.setAvailableTic(request.getTotalTic());

        } else {
            inv.setTotalTic(inv.getTotalTic() + request.getTotalTic());
            inv.setAvailableTic(inv.getAvailableTic() + request.getTotalTic());
        }
        inv.setLastUpdated(LocalDateTime.now());
        Inventory saved = inventoryRepo.save(inv);
        return inventoryMapper.toResponse(saved);
    }

    // 扣庫存(原子更新，避免超賣)
    @Transactional
    public boolean decreaseStock(CreateUpdateInventoryRequest request) {
        int updated = inventoryRepo.decreaseStock(request.getTicketTypeId(), request.getQuantity());
        return updated > 0;
    }

    // 付款失敗/訂單失敗->回補庫存
    @Transactional
    public void restoreStock(CreateUpdateInventoryRequest request) {
        inventoryRepo.increaseStock(request.getTicketTypeId(), request.getQuantity());
    }

}
