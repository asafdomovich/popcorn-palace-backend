package com.att.tdp.popcorn_palace.dto.response;

import lombok.*;

import java.time.ZonedDateTime;

@Data
public class ShowtimeResponse {

    private Long id;

    private String theater;

    private double price;

    private ZonedDateTime startTime;

    private ZonedDateTime endTime;

    private Long movieId;
}
