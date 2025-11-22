package tw.jay.springtest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.jay.springtest.entity.Inventory;
import tw.jay.springtest.entity.TicketType;
import tw.jay.springtest.repository.InventoryRepo;

@Service
public class InventorySer {
    @Autowired
    private InventoryRepo inventoryRepo;

    public List<Inventory> getallinvetory(){
        return inventoryRepo.findAll();
    }

    public Inventory getInventory(Long ticketType){
        return inventoryRepo.findByTicketTypeId(ticketType);
    }

    public Inventory addInventory(TicketType ticketType, int qut){
        Inventory inv = inventoryRepo.findByTicketTypeId(ticketType.getId());

        if(inv == null){
            inv = new Inventory();
            inv.setTicketType(ticketType);
            inv.setAvailableTic(qut);

        }else{
            inv.setAvailableTic(inv.getAvailableTic() + qut); // 加上新增的庫存
        }
           return inventoryRepo.save(inv);
    }
}
