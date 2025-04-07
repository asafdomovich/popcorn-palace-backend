package com.att.tdp.popcorn_palace.dto.response;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieResponse {

    private Long id;

    private String title;

    private String genre;

    private int duration;

    private double rating;

    private int releaseYear;
}
