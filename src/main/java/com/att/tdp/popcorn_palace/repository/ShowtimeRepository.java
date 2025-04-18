package com.att.tdp.popcorn_palace.repository;

import com.att.tdp.popcorn_palace.entity.Showtime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ShowtimeRepository extends JpaRepository<Showtime, Long> {
	
    @Query("""
	    SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END
	    FROM Showtime s
	    WHERE s.theater = :theater
	    AND (
	        (s.startTime < :endTime AND s.endTime > :startTime)
	    )
	""")
    boolean existsByTheaterAndTimeConflict(@Param("theater") String theater,
                                           @Param("startTime") LocalDateTime startTime,
                                           @Param("endTime") LocalDateTime endTime);
	
	boolean existsByTheaterAndStartTimeLessThanAndEndTimeGreaterThanAndIdNot(
			String theater, LocalDateTime endTime, LocalDateTime startTime, Long excludeId
	);
	
}
