package tw.jay.springtest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.jay.springtest.repository.ReservationItemRepo;

@Service
public class ReservationItemSer {
    @Autowired
    private ReservationItemRepo reservationItemRepo;
}
