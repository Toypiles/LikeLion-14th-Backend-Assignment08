package com.likelion.likelionassignmentcrud.director.domain;

import com.likelion.likelionassignmentcrud.director.api.dto.request.DirectorUpdateRequestDto;
import com.likelion.likelionassignmentcrud.movie.domain.Movie;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Director {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="director_id")
    private Long directorId;

    private String name;
    private int age;
    private int debutYear;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Part part;

    @OneToMany(mappedBy = "director",
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Movie> movies = new ArrayList<>();

    @Builder //생성자 (맥)cmd + n, (윈도우) Alt + Insert
    private Director(String name, int age, int debutYear, Part part) {
        this.name = name;
        this.age = age;
        this.debutYear=debutYear;
        this.part = part;
    }

    public void update(DirectorUpdateRequestDto directorUpdateRequestDto) {
        this.name = directorUpdateRequestDto.name();
        this.age = directorUpdateRequestDto.age();
        this.debutYear = directorUpdateRequestDto.debutYear();
        this.part = directorUpdateRequestDto.part();
    }
}
