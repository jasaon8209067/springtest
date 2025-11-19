package tw.jay.springtest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.jay.springtest.entity.EventSchedules;
import tw.jay.springtest.repository.EventsSchedulesRepo;

@Service
public class EventShedulesSer {
    @Autowired
    private EventsSchedulesRepo eventsSchedulesRepo;

    public List<EventSchedules> getEventsSchedulesRepo() {
        return eventsSchedulesRepo.findAll();
    }
}
