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

}