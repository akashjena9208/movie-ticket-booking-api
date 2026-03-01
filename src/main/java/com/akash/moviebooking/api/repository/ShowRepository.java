package com.akash.moviebooking.api.repository;

import com.akash.moviebooking.api.entity.Show;
import com.akash.moviebooking.api.enums.ScreenType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.time.Instant;
import java.util.List;

public interface ShowRepository extends JpaRepository<Show, String> {

    @Query("""
            SELECT DISTINCT t.theaterId
            FROM Theater t
            JOIN t.screens s
            JOIN s.shows sh
            WHERE sh.movie.movieId = :movieId
              AND sh.startsAt BETWEEN :start AND :end
              AND s.screenType = :screenType
              AND t.city = :city
            """)
    Page<String> findTheaterIds(
            @Param("movieId") String movieId,
            @Param("start") Instant start,
            @Param("end") Instant end,
            @Param("screenType") ScreenType screenType,
            @Param("city") String city,
            Pageable pageable
    );

    @Query("""
            SELECT sh FROM Show sh
            WHERE sh.movie.movieId = :movieId
              AND sh.startsAt BETWEEN :start AND :end
              AND sh.screen.screenType = :screenType
              AND sh.theater.theaterId IN :theaterIds
            """)
    List<Show> findShowsForTheaters(
            @Param("movieId") String movieId,
            @Param("start") Instant start,
            @Param("end") Instant end,
            @Param("screenType") ScreenType screenType,
            @Param("theaterIds") List<String> theaterIds
    );
    @Query("""
           SELECT s FROM Show s
           WHERE s.screen.screenId = :screenId
           AND (
                :start < s.endsAt
                AND :end > s.startsAt
           )
           """)
    List<Show> findConflictingShows(
            String screenId,
            Instant start,
            Instant end
    );
}