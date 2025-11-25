package tw.jay.springtest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.jay.springtest.repository.EventTicketTypeRep;

@Service
public class EventTicketTypeService {
    @Autowired
    private EventTicketTypeRep eventTicketTypeRep;
}
