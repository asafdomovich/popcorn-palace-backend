package com.att.tdp.popcorn_palace.dto.response;

import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationResponse {

    private UUID bookingId;
}
