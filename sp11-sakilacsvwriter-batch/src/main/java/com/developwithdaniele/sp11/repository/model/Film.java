package com.developwithdaniele.sp11.repository.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "film")
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Film implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long filmId;

    private String title;

    private Integer releaseYear;

    @OneToMany(mappedBy = "film", fetch = FetchType.LAZY)
    private List<FilmActor> filmActors;
}
