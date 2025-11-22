package tw.jay.springtest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.jay.springtest.repository.ReservationRepo;

@Service
public class ReservationSer {
    @Autowired
    private ReservationRepo reservationRepo;
}
