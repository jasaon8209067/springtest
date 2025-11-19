package tw.jay.springtest.service;

import java.util.List;
import java.util.stream.Collectors;

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
    public List<TicketTypeResponse> getalltickets(){
        return repo.findAll().stream()
        .map(TicketTypeMapper::toResponse)
        .collect(Collectors.toList());
    }

    // public List<TicketType> getAllTicket() {
    //     return repo.findAll();
    // }

    public List<TicketType> getStatusTickets() {
        return repo.findByStatusTrue();
    }

    public TicketType updateStatus(Long id, boolean status) {

        // 依 ID 查詢票種，若查不到則丟出例外
        TicketType ticket = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        // 更新票種狀態
        ticket.setStatus(status);

        // 儲存回資料庫並回傳
        return repo.save(ticket);
    }

    // 新增新票種
    public TicketType createTicket(CreateTicketTypeRequest dto) {
        TicketType ticket = TicketTypeMapper.toEntity(dto);
        return repo.save(ticket);
    }

    // 更新現有的票種
    public TicketType updateTicket(Long id, UpdateTicketTypeRequest dto) {
        TicketType ticket = repo.findById(id).orElseThrow(() -> new RuntimeException("Ticket not found"));
        TicketTypeMapper.updateEntity(ticket, dto);
        return repo.save(ticket);
    }

    // 刪除票種
    public void deleteTicket(Long id) {
        repo.deleteById(id);
    }
}