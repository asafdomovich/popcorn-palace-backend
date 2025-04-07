package com.att.tdp.popcorn_palace.service.impl;

import com.att.tdp.popcorn_palace.dto.request.CreateMovieRequest;
import com.att.tdp.popcorn_palace.dto.response.MovieResponse;
import com.att.tdp.popcorn_palace.entity.Movie;
import com.att.tdp.popcorn_palace.repository.MovieRepository;
import com.att.tdp.popcorn_palace.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    @Override
    public List<MovieResponse> getAllMovies() {
        List<Movie> movies = movieRepository.findAll();
        return movies.stream().map(movie -> new MovieResponse(
                movie.getId(), movie.getTitle(), movie.getGenre(),
                movie.getDuration(), movie.getRating(), movie.getReleaseYear()
        )).collect(Collectors.toList());
    }

    @Override
    public MovieResponse addMovie(CreateMovieRequest createMovieRequest) {
        Movie movie = new Movie(
                null,
                createMovieRequest.getTitle(),
                createMovieRequest.getGenre(),
                createMovieRequest.getDuration(),
                createMovieRequest.getRating(),
                createMovieRequest.getReleaseYear()
        );
        Movie savedMovie = movieRepository.save(movie);
        return new MovieResponse(savedMovie.getId(), savedMovie.getTitle(), savedMovie.getGenre(),
                savedMovie.getDuration(), savedMovie.getRating(), savedMovie.getReleaseYear());
    }

    @Override
    public void updateMovie(String movieTitle, CreateMovieRequest updateMovieRequest) {
        Movie movie = movieRepository.findByTitle(movieTitle).orElseThrow();
        movie.setTitle(updateMovieRequest.getTitle());
        movie.setGenre(updateMovieRequest.getGenre());
        movie.setDuration(updateMovieRequest.getDuration());
        movie.setRating(updateMovieRequest.getRating());
        movie.setReleaseYear(updateMovieRequest.getReleaseYear());
        movieRepository.save(movie);
    }

    @Override
    public void deleteMovie(String movieTitle) {
        Movie movie = movieRepository.findByTitle(movieTitle).orElseThrow();
        movieRepository.delete(movie);
    }
}
