package tw.jay.springtest.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.jay.springtest.entity.Events;
import tw.jay.springtest.repository.EventsRepository;


@Service
public class EventsService {
    @Autowired
    private EventsRepository eventsRepository;

    public Optional<Events> getEventById(Long id) {//透過id取得活動資料
        return eventsRepository.findById(id);
    }

    public List<Events> getallEvent(){
        return eventsRepository.findAll();
    }
    // public  List<Events> findActiveEvents(Status status){
    //     return eventsRepository.findByActiveTrue();
    // }
    

}
