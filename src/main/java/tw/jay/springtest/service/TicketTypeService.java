package tw.jay.springtest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.jay.springtest.DTO.Request.CreateTicketTypeRequest;
import tw.jay.springtest.DTO.Request.UpdateTicketTypeRequest;
import tw.jay.springtest.DTO.Response.TicketTypeResponse;
import tw.jay.springtest.entity.TicketType;
import tw.jay.springtest.mapper.TicketTypeMapper;
import tw.jay.springtest.repository.TicketTypeRepository;

/* 
 * DTO
 *
 * 為了避免直接回傳實體類別(Entity)，通常使用DTO封裝資料
 * 穩定API格式，未來修改實體類別時，不會影響到API
 * 輸入驗證與安全性，可以在DTO中加入驗證註解
 * 效能優化，可以只包含需要的欄位，減少資料傳輸量
 */

 /*
  * DTO流程
  * Controller接收request轉成request DTO
  * Controller呼叫Service
  * Service做邏輯並從repository取得/修改Entity
  * Service把Entity轉成response DTO回傳給Controller
  * Controller回傳response DTO給前端
  */
@Service
public class TicketTypeService {

    @Autowired
    private TicketTypeRepository repo;

    // DTO作法
    //查所有票種
    public List<TicketTypeResponse> getalltickets(){
        return repo.findAll()
                .stream()//將查出的List轉成Java Stream
                .map(TicketTypeMapper::toResponse)//將每一個TicketType轉換成 TicketTypeResponse
                .toList();        
    }

    //查啟用的票種
    public List<TicketTypeResponse> getStatusTickets(){
        return repo.findByStatusTrue()
            .stream()
            .map(TicketTypeMapper::toResponse)
            .toList();
    }

    //新增票種
    public TicketTypeResponse createTicket(CreateTicketTypeRequest dto){
        TicketType entity = TicketTypeMapper.toEntity(dto);//前端傳過來的request需要透過mapper轉換成entity
        TicketType saved = repo.save(entity);//存入資料庫，回傳真正寫入後的entity
        return TicketTypeMapper.toResponse(saved);//不能直接回傳entity給前端所以需要轉成response DTO
    }

    //更新票種資料
    public TicketTypeResponse updateTicket(Long id, UpdateTicketTypeRequest dto){
        TicketType entity = repo.findById(id)
            .orElseThrow(() -> new RuntimeException("此票種不存在"));

        TicketTypeMapper.updateEntity(entity, dto);

        TicketType updated = repo.save(entity);
        return TicketTypeMapper.toResponse(updated);
    
    }

    //更新票種狀態
    public TicketTypeResponse updateStatus(Long id, boolean status){
        TicketType entity = repo.findById(id)
        .orElseThrow(() -> new RuntimeException("此票種不存在"));

        entity.setStatus(status);
        TicketType updated = repo.save(entity);

        return TicketTypeMapper.toResponse(updated);     
    }

    //查詢單一票種
    public TicketTypeResponse findById(Long id){
        TicketType entity = repo.findById(id)
        .orElseThrow(() -> new RuntimeException("此票種不存在"));
        return TicketTypeMapper.toResponse(entity);
    }

    //刪除票種
    public void deleteTicket(Long id){
        repo.deleteById(id);
    }


}