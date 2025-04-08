package com.att.tdp.popcorn_palace.service.impl;

import com.att.tdp.popcorn_palace.dto.request.CreateMovieRequest;
import com.att.tdp.popcorn_palace.dto.response.MovieResponse;
import com.att.tdp.popcorn_palace.entity.Movie;
import com.att.tdp.popcorn_palace.repository.MovieRepository;
import com.att.tdp.popcorn_palace.service.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MovieServiceTest {

    private MovieRepository movieRepository;
    private MovieService movieService;

    @BeforeEach
    public void setup() {
        movieRepository = mock(MovieRepository.class);
        movieService = new MovieServiceImpl(movieRepository);
    }

    @Test
    public void testGetAllMovies() {
        Movie movie = new Movie(1L, "Avengers", "Action", 180, 9.0, 2019);
        when(movieRepository.findAll()).thenReturn(List.of(movie));

        List<MovieResponse> movies = movieService.getAllMovies();

        assertEquals(1, movies.size());
        assertEquals("Avengers", movies.get(0).getTitle());
        verify(movieRepository).findAll();
    }

    @Test
    public void testAddMovie() {
        CreateMovieRequest request = new CreateMovieRequest("Inception", "Sci-Fi", 148, 8.8, 2010);
        Movie movieToSave = new Movie(null, "Inception", "Sci-Fi", 148, 8.8, 2010);
        Movie savedMovie = new Movie(1L, "Inception", "Sci-Fi", 148, 8.8, 2010);

        when(movieRepository.save(any(Movie.class))).thenReturn(savedMovie);

        MovieResponse response = movieService.addMovie(request);

        assertEquals("Inception", response.getTitle());
        assertEquals(8.8, response.getRating());
        verify(movieRepository).save(any(Movie.class));
    }

    @Test
    public void testUpdateMovie() {
        String title = "Interstellar";
        CreateMovieRequest update = new CreateMovieRequest("Interstellar", "Sci-Fi", 169, 8.6, 2014);
        Movie existing = new Movie(1L, title, "Drama", 150, 7.5, 2013);
        Movie updated = new Movie(1L, update.getTitle(), update.getGenre(), update.getDuration(), update.getRating(), update.getReleaseYear());

        when(movieRepository.findByTitle(title)).thenReturn(Optional.of(existing));
        when(movieRepository.save(existing)).thenReturn(updated);

        MovieResponse response = movieService.updateMovie(title, update);

        assertEquals("Interstellar", response.getTitle());
        assertEquals(169, response.getDuration());
        verify(movieRepository).findByTitle(title);
        verify(movieRepository).save(existing);
    }

    @Test
    public void testDeleteMovie() {
        String title = "The Matrix";
        Movie movie = new Movie(1L, title, "Action", 136, 8.7, 1999);

        when(movieRepository.findByTitle(title)).thenReturn(Optional.of(movie));

        movieService.deleteMovie(title);

        verify(movieRepository).delete(movie);
    }

    @Test
    public void testUpdateMovie_NotFound() {
        when(movieRepository.findByTitle("Unknown")).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            movieService.updateMovie("Unknown", new CreateMovieRequest("New", "Genre", 120, 5.0, 2023));
        });
    }

    @Test
    public void testDeleteMovie_NotFound() {
        when(movieRepository.findByTitle("Ghost Movie")).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            movieService.deleteMovie("Ghost Movie");
        });
    }
}
