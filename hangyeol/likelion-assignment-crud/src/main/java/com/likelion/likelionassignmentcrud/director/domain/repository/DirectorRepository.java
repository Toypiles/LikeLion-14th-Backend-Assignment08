package com.likelion.likelionassignmentcrud.director.domain.repository;

import com.likelion.likelionassignmentcrud.director.domain.Director;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DirectorRepository extends JpaRepository<Director, Long> {
    Page<Director> findAll(Pageable pageable);
    boolean existsByName(String name);
}
