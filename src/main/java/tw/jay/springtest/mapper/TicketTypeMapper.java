package tw.jay.springtest.mapper;

import tw.jay.springtest.DTO.Request.CreateTicketTypeRequest;
import tw.jay.springtest.DTO.Request.UpdateTicketTypeRequest;
import tw.jay.springtest.DTO.Response.TicketTypeResponse;
import tw.jay.springtest.entity.TicketType;

public class TicketTypeMapper {

    //CreateTicketTypeRequest -> Entity
    public static TicketType toEntity(CreateTicketTypeRequest dto){
        TicketType entity = new TicketType();
        entity.setName(dto.getName());
        entity.setPrice(dto.getPrice());
        entity.setRemark(dto.getRemark());
        entity.setStatus(dto.getStatus());
        return entity;
    }

    //UpdateTicketTypeRequest -> 更新既有Entity(Partial Update）
    public static void updateEntity(TicketType entity, UpdateTicketTypeRequest dto){
        if(dto.getName() != null){
            entity.setName(dto.getName());
        }

        if(dto.getPrice() != null){
            entity.setPrice(dto.getPrice());
        }

        if (dto.getRemark() != null) {
            entity.setRemark(dto.getRemark());
        }

        if (dto.getStatus() != null) {
            entity.setStatus(dto.getStatus());
        }
    }


    //Entity -> Response DTO
    public static TicketTypeResponse toResponse(TicketType entity){
        TicketTypeResponse res = new TicketTypeResponse();
        res.setId(entity.getId());
        res.setName(entity.getName());
        res.setPrice(entity.getPrice());
        res.setRemark(entity.getRemark());
        res.setStatus(entity.isStatus());
        return res;
    }



}