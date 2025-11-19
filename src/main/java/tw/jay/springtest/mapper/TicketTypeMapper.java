package tw.jay.springtest.mapper;

import tw.jay.springtest.DTO.Request.CreateTicketTypeRequest;
import tw.jay.springtest.DTO.Request.UpdateTicketTypeRequest;
import tw.jay.springtest.DTO.Response.TicketTypeResponse;
import tw.jay.springtest.entity.TicketType;


public class TicketTypeMapper {
    //Entity轉Response DTO
    //將TicketType Entity轉換為TicketTypeResponse DTO
    public static TicketTypeResponse toResponse(TicketType entity) {
        TicketTypeResponse response = new TicketTypeResponse();
        response.setId(entity.getId());
        response.setName(entity.getName());
        response.setPrice(entity.getPrice());
        response.setRemark(entity.getRemark());
        response.setStatus(entity.isStatus());
        return response;
        
    }
    
    //CreateTicketTypeRequest DTO轉換為TicketType Entity(建立新的票種)
    public static TicketType toEntity(CreateTicketTypeRequest request) {
        TicketType entity = new TicketType();
        entity.setName(request.getName());
        entity.setPrice(request.getPrice());
        entity.setRemark(request.getRemark());
        entity.setStatus(request.isStatus());
        return entity;

    }

    //使用UpdateTicketTypeRequest DTO更新現有的TicketType Entity
    public static void updateEntity(TicketType entity, UpdateTicketTypeRequest request) {
        entity.setName(request.getName());
        entity.setPrice(request.getPrice());
        entity.setRemark(request.getRemark());
        entity.setStatus(request.isStatus());
    }   
}
