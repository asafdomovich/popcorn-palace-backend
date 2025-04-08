package com.att.tdp.popcorn_palace.dto.request;

import lombok.*;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateReservationRequest {

    @NotNull
    private int seatNumber;

    @NotNull
    private UUID userId;

    @NotNull
    private Long showtimeId;
}
