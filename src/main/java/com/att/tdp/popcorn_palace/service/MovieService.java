package com.att.tdp.popcorn_palace.service;

import com.att.tdp.popcorn_palace.dto.request.CreateMovieRequest;
import com.att.tdp.popcorn_palace.dto.response.MovieResponse;

import java.util.List;

public interface MovieService {
    List<MovieResponse> getAllMovies();

    MovieResponse addMovie(CreateMovieRequest createMovieRequest);
    
    MovieResponse updateMovie(String movieTitle, CreateMovieRequest updateMovieRequest);

    void deleteMovie(String movieTitle);
}
