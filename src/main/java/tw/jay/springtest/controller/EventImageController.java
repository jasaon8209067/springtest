package tw.jay.springtest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tw.jay.springtest.service.EventImageService;

@RestController
@RequestMapping("/api/eventimages")


public class  EventImageController {
    @Autowired
    private EventImageService eventImageService;

    @GetMapping("/event/{eventId}")
    public List<String> getImageForEvent(@PathVariable Long eventId){
        return eventImageService.getImageUrlByEventId(eventId);
    }

}
