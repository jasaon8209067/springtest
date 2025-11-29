package tw.jay.springtest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tw.jay.springtest.DTO.Response.EventTicketTypeResponse;
import tw.jay.springtest.service.EventTicketTypeService;

@RestController
@RequestMapping("/api/eventtickettype")
@CrossOrigin(origins = "http://localhost:5173")
public class EventTicketTypeController {
    @Autowired
    private EventTicketTypeService eventTicketTypeService;

    @GetMapping("/event_ticket_type")
    public List<EventTicketTypeResponse> getAll() {
        List<EventTicketTypeResponse> data = eventTicketTypeService.findAll();

        return data;
    }

    @GetMapping("/event_ticket_type/{eventId}")
    public List<EventTicketTypeResponse> getByEventId(@PathVariable Long eventId) {
        List<EventTicketTypeResponse> data = eventTicketTypeService.findByEventId(eventId);
        return data;
    }

    // 庫存減少
    @PutMapping("/{id}/decreaseStock")
    public ResponseEntity<?> decreaseStock(@PathVariable Long id, @RequestParam int quantity){
        try{
            boolean success = eventTicketTypeService.decreaseStock(id, quantity);
       
            if(success){
            return ResponseEntity.ok("庫存減少成功");
            }else{
            return ResponseEntity.badRequest().body("庫存不足或票種ID錯誤");
            }
        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch(Exception e){
            return ResponseEntity.internalServerError().body("扣庫存時發生錯誤");
        }
    }

}