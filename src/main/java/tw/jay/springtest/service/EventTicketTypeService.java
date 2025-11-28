package tw.jay.springtest.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.jay.springtest.DTO.Response.EventTicketTypeResponse;
import tw.jay.springtest.entity.EventTicketType;
import tw.jay.springtest.repository.EventTicketTypeRep;

@Service
public class EventTicketTypeService {
    @Autowired
    private EventTicketTypeRep eventTicketTypeRep;

    //找出所有活動的所有票
    public List<EventTicketTypeResponse> findAll() {

        List<EventTicketType> list = eventTicketTypeRep.findAll();

        return list.stream()
                .map(ett -> {
                    EventTicketTypeResponse dto = new EventTicketTypeResponse();
                    dto.setId(ett.getId());
                    dto.setEventId(ett.getEvent().getId());
                    dto.setTicketType(ett.getTicketType().getName());
                    dto.setTicket_template_id(ett.getTicketType().getId());           
                    dto.setIslimited(ett.getIslimited());
                    dto.setCustomprice(ett.getCustomprice());
                    dto.setCustomlimit(ett.getCustomlimit());
                    dto.setCreatedat(ett.getCreatedat());
                    
                    // dto.setRemark(ett.getTicketType().getRemark());

                    return dto;
                })
                .toList();
    }

    //依活動ID找相對應的票種
    public List<EventTicketTypeResponse> findByEventId(Long eventId) {
        List<EventTicketType> list = eventTicketTypeRep.findByEventId(eventId);

        return list.stream()
                .map(ett -> {
                    EventTicketTypeResponse dto = new EventTicketTypeResponse();
                    dto.setId(ett.getId());
                    dto.setEventId(ett.getEvent().getId());
                    dto.setTicketType(ett.getTicketType().getName());
                    dto.setTicket_template_id(ett.getTicketType().getId());
                    dto.setIslimited(ett.getIslimited());
                    dto.setCustomprice(ett.getCustomprice());
                    dto.setCustomlimit(ett.getCustomlimit());
                    dto.setCreatedat(ett.getCreatedat());
                    
                    // dto.setRemark(ett.getTicketType().getRemark());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    //測試扣庫存




    //測試扣庫存
    // public boolean decreaseStock(Long ticketTypeId, int quantity) {
    //     // 查找指定的票种
    //     EventTicketType eventTicketType = eventTicketTypeRep.findById(ticketTypeId)
    //             .orElseThrow(() -> new RuntimeException("Ticket not found"));

    //     // 判断库存是否足够
    //     if (eventTicketType.getCustomlimit() >= quantity) {
    //         // 扣减库存
    //         eventTicketType.setCustomlimit(eventTicketType.getCustomlimit() - quantity);

    //         // 保存更新后的票种
    //         eventTicketTypeRep.save(eventTicketType);
    //         return true;  // 扣减成功
    //     } else {
    //         return false;  // 库存不足
    //     }
    // }
}
    

