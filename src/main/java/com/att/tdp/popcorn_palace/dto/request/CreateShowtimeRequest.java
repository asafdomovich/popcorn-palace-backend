package com.att.tdp.popcorn_palace.dto.request;

import lombok.*;

import jakarta.validation.constraints.NotNull;
import java.time.ZonedDateTime;

@Data
public class CreateShowtimeRequest {

    @NotNull
    private Long movieId;

    @NotNull
    private String theater;

    @NotNull
    private double price;

    @NotNull
    private ZonedDateTime startTime;

    @NotNull
    private ZonedDateTime endTime;
}
