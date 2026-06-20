package com.likelion.likelionassignmentcrud.movie.domain.repository;

import com.likelion.likelionassignmentcrud.director.domain.Director;
import com.likelion.likelionassignmentcrud.movie.domain.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    Page<Movie> findByDirector(Director director, Pageable pageable);
}
