package tw.jay.springtest.mapper;

import org.springframework.stereotype.Component;

import tw.jay.springtest.DTO.Response.InventoryResponse;
import tw.jay.springtest.entity.Inventory;

@Component
public class InventoryMapper {
    
    public InventoryResponse toResponse(Inventory inv){
        if(inv == null){
            return null;
        }

        InventoryResponse dto = new InventoryResponse();
        dto.setId(inv.getId());
        dto.setTicketTypeId(inv.getTicketType().getId());
        dto.setTicketTypeName(inv.getTicketType().getName());
        dto.setTotalTic(inv.getTotalTic());
        dto.setAvailableTic(inv.getAvailableTic());
        dto.setLastUpdated(inv.getLastUpdated());

        return dto;
    }
}
