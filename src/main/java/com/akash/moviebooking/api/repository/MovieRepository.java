package com.akash.moviebooking.api.repository;

import com.akash.moviebooking.api.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie,String> {

    List<Movie> findByTitleContainingIgnoreCase(String search);


}