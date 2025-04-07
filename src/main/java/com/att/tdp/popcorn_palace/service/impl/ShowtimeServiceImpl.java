package com.att.tdp.popcorn_palace.service.impl;


import com.att.tdp.popcorn_palace.dto.request.CreateShowtimeRequest;
import com.att.tdp.popcorn_palace.dto.response.ShowtimeResponse;
import com.att.tdp.popcorn_palace.entity.Movie;
import com.att.tdp.popcorn_palace.entity.Showtime;
import com.att.tdp.popcorn_palace.exception.DataNotFoundException;
import com.att.tdp.popcorn_palace.exception.OverlapException;
import com.att.tdp.popcorn_palace.repository.MovieRepository;
import com.att.tdp.popcorn_palace.repository.ShowtimeRepository;
import com.att.tdp.popcorn_palace.service.ShowtimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ShowtimeServiceImpl implements ShowtimeService {

    private final ShowtimeRepository showtimeRepository;
    private final MovieRepository movieRepository;

    @Override
    public ShowtimeResponse getShowtimeById(Long showtimeId) {
        // Find the showtime by ID and throw exception if not found
        Showtime showtime = showtimeRepository.findById(showtimeId).orElseThrow(
                () -> new DataNotFoundException("Showtime not found with id: " + showtimeId)
        );
        // Return the response object for the found showtime
        return new ShowtimeResponse(showtime.getId(), showtime.getTheater(), showtime.getPrice(),
                showtime.getStartTime(), showtime.getEndTime(), showtime.getMovie().getId());
    }
    @Override
    @Transactional
    public ShowtimeResponse addShowtime(CreateShowtimeRequest createShowtimeRequest) {
        // Find the movie associated with the showtime request
        Movie movie = movieRepository.findById(createShowtimeRequest.getMovieId())
                .orElseThrow(() -> new DataNotFoundException("Movie not found with id: " + createShowtimeRequest.getMovieId()));
        
        // Check for any time conflicts with existing showtimes in the same theater
        boolean hasConflict = showtimeRepository.existsByTheaterAndTimeConflict(
                createShowtimeRequest.getTheater(),
                createShowtimeRequest.getStartTime(),
                createShowtimeRequest.getEndTime()
        );
        
        if (hasConflict) {
            // Throw exception if there is a time conflict
            throw new OverlapException("Showtime overlaps with an existing showtime in the same theater.");
        }
        
        // Create a new showtime entity
        Showtime showtime = new Showtime(
                null,
                createShowtimeRequest.getTheater(),
                createShowtimeRequest.getPrice(),
                createShowtimeRequest.getStartTime(),
                createShowtimeRequest.getEndTime(),
                movie
        );
        
        // Save the new showtime to the repository
        Showtime savedShowtime = showtimeRepository.save(showtime);
        
        // Return the response object for the saved showtime
        return new ShowtimeResponse(
                savedShowtime.getId(),
                savedShowtime.getTheater(),
                savedShowtime.getPrice(),
                savedShowtime.getStartTime(),
                savedShowtime.getEndTime(),
                savedShowtime.getMovie().getId()
        );
    }
    
    
    @Override
    @Transactional
    public ShowtimeResponse updateShowtime(Long showtimeId, CreateShowtimeRequest updateShowtimeRequest) {
        Showtime showtime = showtimeRepository.findById(showtimeId)
                .orElseThrow(() -> new DataNotFoundException("Showtime not found with id: " + showtimeId));
        
        // Find the movie associated with the showtime request
        Movie movie = movieRepository.findById(updateShowtimeRequest.getMovieId())
                .orElseThrow(() -> new DataNotFoundException("Movie not found with id: " + updateShowtimeRequest.getMovieId()));
        
        // Check for any time conflicts with existing showtimes in the same theater
        boolean hasConflict = showtimeRepository.existsByTheaterAndStartTimeLessThanAndEndTimeGreaterThanAndIdNot(
                updateShowtimeRequest.getTheater(),
                updateShowtimeRequest.getEndTime(),
                updateShowtimeRequest.getStartTime(),
                showtimeId
        );
        
        if (hasConflict) {
            // Throw exception if there is a time conflict
            throw new OverlapException("Overlapping showtime exists for theater: " + updateShowtimeRequest.getTheater());
        }
        
        // Update the showtime with the new information
        showtime.setTheater(updateShowtimeRequest.getTheater());
        showtime.setPrice(updateShowtimeRequest.getPrice());
        showtime.setStartTime(updateShowtimeRequest.getStartTime());
        showtime.setEndTime(updateShowtimeRequest.getEndTime());
        showtime.setMovie(movie);
        
        // Save the updated showtime to the repository
        Showtime updated = showtimeRepository.save(showtime);
        
        // Return the response object for the updated showtime
        return new ShowtimeResponse(
                updated.getId(),
                updated.getTheater(),
                updated.getPrice(),
                updated.getStartTime(),
                updated.getEndTime(),
                updated.getMovie().getId()
        );
    }


    @Override
    public void deleteShowtime(Long showtimeId) {
        // Find the showtime by ID
        Showtime showtime = showtimeRepository.findById(showtimeId).orElseThrow(
                () -> new DataNotFoundException("Showtime not found with id: " + showtimeId)
        );
        // Delete the showtime
        showtimeRepository.delete(showtime);
    }
}
