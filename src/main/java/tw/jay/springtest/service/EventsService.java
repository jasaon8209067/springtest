package tw.jay.springtest.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.jay.springtest.entity.Events;
import tw.jay.springtest.repository.EventsRepository;


@Service
public class EventsService {
    @Autowired
    private EventsRepository eventsRepository;

    public Optional<Events> getEventById(Long id) {
        return eventsRepository.findById(id);
    }
    

}
