package com.att.tdp.popcorn_palace.dto.request;

import lombok.*;

import jakarta.validation.constraints.NotNull;

@Data
public class CreateMovieRequest {

    @NotNull
    private String title;

    @NotNull
    private String genre;

    @NotNull
    private int duration;

    @NotNull
    private double rating;

    @NotNull
    private int releaseYear;
}
