package com.att.tdp.popcorn_palace.service.impl;

import com.att.tdp.popcorn_palace.dto.request.CreateMovieRequest;
import com.att.tdp.popcorn_palace.dto.response.MovieResponse;
import java.util.List;

public interface MovieService {
    List<MovieResponse> getAllMovies();

    MovieResponse addMovie(CreateMovieRequest createMovieRequest);

    void updateMovie(String movieTitle, CreateMovieRequest updateMovieRequest);

    void deleteMovie(String movieTitle);
}
