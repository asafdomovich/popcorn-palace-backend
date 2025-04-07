package com.att.tdp.popcorn_palace.dto.request;

import lombok.*;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Data
public class CreateShowtimeRequest {

    @NotNull
    private Long movieId;

    @NotNull
    private String theater;

    @NotNull
    private double price;
    
    @NotNull
    private LocalDateTime startTime;
    
    @NotNull
    private LocalDateTime endTime;
}
