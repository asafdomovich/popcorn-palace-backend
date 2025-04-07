package com.att.tdp.popcorn_palace.service;

import com.att.tdp.popcorn_palace.dto.request.CreateReservationRequest;
import com.att.tdp.popcorn_palace.dto.response.ReservationResponse;

public interface ReservationService {
    ReservationResponse createReservation(CreateReservationRequest createReservationRequest);
}
