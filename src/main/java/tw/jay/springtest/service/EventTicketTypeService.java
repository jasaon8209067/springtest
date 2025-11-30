package tw.jay.springtest.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                    dto.setDescription(ett.getTicketType().getDescription());
                    // dto.setRemark(ett.getTicketType().getRemark());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    //測試扣庫存

    /*
    減少指定票種的庫存
    @param eventTicketTypeId EventTicketType的ID (primary key)
    @param quantity減少的數量
    @return true:扣減成功(庫存足夠並更新了1行);false:庫存不足或ID錯誤
    */
    @Transactional
    public boolean decreaseStock(Long eventTicketTypeId, int quantity){
        if (quantity <= 0) {
            throw new IllegalArgumentException("減少數量必須大於0");
        }
        //使用Repository中的原子性更新方法
        int updateRows = eventTicketTypeRep.decreaseStock(eventTicketTypeId, quantity);
        
        return updateRows > 0;
    }

    /*
    增加指定票種的庫存(包括訂單取消或回滾)
    @param eventTicketTypeId EventTicketType的 ID (primary key)
    @param quantity增加的數量
    @return true: 增加成功; false: ID 錯誤
    */
    @Transactional
    public boolean increaseStock(Long eventTicketTypeId, int quantity){
        if(quantity <= 0){
            throw new IllegalArgumentException("增加數量必須大於0");
        }
        //使用Repository中的原子性更新方法
        int updateRows = eventTicketTypeRep.increaseStock(eventTicketTypeId, quantity);

        return updateRows > 0;
   }




}