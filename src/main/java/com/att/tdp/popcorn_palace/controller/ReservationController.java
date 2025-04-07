package com.att.tdp.popcorn_palace.controller;

import com.att.tdp.popcorn_palace.dto.request.CreateReservationRequest;
import com.att.tdp.popcorn_palace.dto.response.ReservationResponse;
import com.att.tdp.popcorn_palace.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    // Book a ticket (create a reservation)
    @PostMapping
    public ResponseEntity<ReservationResponse> bookTicket(@Valid @RequestBody CreateReservationRequest createReservationRequest) {
        ReservationResponse reservationResponse = reservationService.createReservation(createReservationRequest);
        return new ResponseEntity<>(reservationResponse, HttpStatus.OK);
    }
}
