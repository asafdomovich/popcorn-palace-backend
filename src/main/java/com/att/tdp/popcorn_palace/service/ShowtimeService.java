package com.att.tdp.popcorn_palace.service;


import com.att.tdp.popcorn_palace.dto.request.CreateShowtimeRequest;
import com.att.tdp.popcorn_palace.dto.response.ShowtimeResponse;

public interface ShowtimeService {
    ShowtimeResponse getShowtimeById(Long showtimeId);

    ShowtimeResponse addShowtime(CreateShowtimeRequest createShowtimeRequest);
    
    ShowtimeResponse updateShowtime(Long showtimeId, CreateShowtimeRequest updateShowtimeRequest);

    void deleteShowtime(Long showtimeId);
}
