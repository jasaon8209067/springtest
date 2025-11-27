package tw.jay.springtest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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

    // 假设用户提交结账请求时，传递的是票种ID和购买数量
//     @PostMapping("/process")
//     public ResponseEntity<String> processCheckout(@RequestBody Customlimit customlimit) {
//         Long ticketTypeId = checkoutRequest.getTicketTypeId();
//         int quantity = checkoutRequest.getQuantity();

//         // 执行扣库存操作
//         boolean success = eventTicketTypeService.decreaseStock(ticketTypeId, quantity);

//         if (success) {
//             // 库存扣减成功
//             return ResponseEntity.ok("Checkout successful.");
//         } else {
//             // 库存不足
//             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Not enough stock.");
//         }
//     }
}