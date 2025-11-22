package tw.jay.springtest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tw.jay.springtest.entity.TicketType;
import tw.jay.springtest.service.TicketTypeService;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {
    
    @Autowired
    private TicketTypeService service;

   @GetMapping
   //DTO做法
//    public List<TicketTypeResponse> getAll() {
//         return service.getalltickets();
//     }
    public List<TicketType> getAllTickets() {
        return service.getAllTicket();
    }

    @GetMapping("/status")
    public List<TicketType> getStatusTickets() {
        return service.getStatusTickets();
    }

    @PatchMapping("/{id}/status")
    public TicketType updateTicketStatus(Long id, boolean status) {
        return service.updateStatus(id, status);
    }
}