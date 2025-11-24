package tw.jay.springtest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import tw.jay.springtest.DTO.Request.CreateTicketTypeRequest;
import tw.jay.springtest.DTO.Request.UpdateTicketTypeRequest;
import tw.jay.springtest.DTO.Response.TicketTypeResponse;
import tw.jay.springtest.service.TicketTypeService;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    @Autowired
    private TicketTypeService service;

    //DTO做法
    @GetMapping
    public List<TicketTypeResponse> getAll() {
        return service.getalltickets();
    }
   
    //查啟用的票種
    @GetMapping("/status")
    public List<TicketTypeResponse> getStatusTickets() {
        return service.getStatusTickets();
    }

    //更新票種狀態
    @PatchMapping("/{id}/status")
    public TicketTypeResponse updateTicketStatus(@PathVariable Long id, @RequestParam boolean status) {
        return service.updateStatus(id, status);
    }

    //新增票種
    @PostMapping("/create")
    public TicketTypeResponse createTicket(@RequestBody @Valid CreateTicketTypeRequest dto){
        return service.createTicket(dto);
    }

    //更新票種
    @PostMapping("/{id}")
    public TicketTypeResponse updateTicket(@PathVariable Long id, @RequestBody @Valid UpdateTicketTypeRequest dto){
        return service.updateTicket(id, dto);
    }

    //刪除票種
    @DeleteMapping("/{id}")
    public void deleteTicket(@PathVariable Long id){
        service.deleteTicket(id);
    }

    //查單一票種
    @GetMapping("/{id}")
    public TicketTypeResponse getTickeById(@PathVariable Long id){
        return service.findById(id);
    }
}