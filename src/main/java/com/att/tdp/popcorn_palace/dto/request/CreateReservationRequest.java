package com.att.tdp.popcorn_palace.dto.request;

import lombok.*;

import jakarta.validation.constraints.NotNull;

@Data
public class CreateReservationRequest {

    @NotNull
    private int seatNumber;

    @NotNull
    private Long userId;

    @NotNull
    private Long showtimeId;
}
