package tw.jay.springtest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tw.jay.springtest.entity.EventSchedules;
import tw.jay.springtest.repository.EventsSchedulesRepo;
@RestController
@RequestMapping("/api/eventschedules")
public class EventSchedulesController {
    
    @Autowired
    private EventsSchedulesRepo eventsSchedulesRepo;

    @GetMapping("/all")
    public List<EventSchedules> getEventsSchedulesRepo() {
        return eventsSchedulesRepo.findAll();
    }

}
