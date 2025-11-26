package tw.jay.springtest.controller;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tw.jay.springtest.entity.Event;
import tw.jay.springtest.service.EventsService;

@RestController
@RequestMapping("/api/events")
@CrossOrigin(origins = "http://localhost:5173")
public class EventsController {

    @Autowired
    private EventsService eventsService;

    @GetMapping("/all")
    public List<Event> getallEvents(){
        return eventsService.getallEvent();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEvent(@PathVariable Long id) {
        return eventsService.getEventById(id)
                .map(event -> {//如果有找到event就做下面的事
                    // 將 LONGBLOB 轉 Base64
                    String base64Image = "";
                    if (event.getImage() != null) {
                        base64Image = Base64.getEncoder().encodeToString(event.getImage());
                    }
                    // 回傳 JSON 給前端
                    Map<String, Object> response = new HashMap<>();
                    response.put("id", event.getId());
                    response.put("title", event.getTitle());
                    // response.put("detail", event.getDetail());
                    response.put("event_start", event.getEvent_start());
                    response.put("event_end", event.getEvent_end());
                    response.put("address", event.getAddress());
                    response.put("image", base64Image);

                    return ResponseEntity.ok(response);// 如果有找到event就回傳200和event
                })

                .orElse(ResponseEntity.notFound().build());// 如果沒有就回傳404

    }
}