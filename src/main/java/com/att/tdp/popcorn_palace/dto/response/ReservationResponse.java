package com.att.tdp.popcorn_palace.dto.response;

import lombok.*;

@Data
public class ReservationResponse {

    private Long reservationId;

    private int seatNumber;

    private Long userId;

    private Long showtimeId;
}
