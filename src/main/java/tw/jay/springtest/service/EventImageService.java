package tw.jay.springtest.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.jay.springtest.entity.EventImage;
import tw.jay.springtest.repository.EventImageRepository;

@Service
public class EventImageService {
    @Autowired
    private EventImageRepository imageRepository;

    private static final String IMAGE_URL_PREFIX = "/images/";
    public List<String> getImageUrlByEventId(Long eventId){
        List<EventImage> images = imageRepository.findByEventId(eventId);

        return images.stream()
                .map(image -> IMAGE_URL_PREFIX + image.getImageUrl())
                .collect(Collectors.toList());
    }
}
