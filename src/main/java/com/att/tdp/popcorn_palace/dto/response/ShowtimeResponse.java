package com.att.tdp.popcorn_palace.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShowtimeResponse {

    private Long id;

    private String theater;

    private double price;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Long movieId;
}
