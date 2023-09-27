package com.developwithdaniele.sp10.repository.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "film")
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
public class Film implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long filmId;

    private String title;

    @OneToMany(mappedBy = "film", fetch = FetchType.LAZY)
    private List<FilmActor> actors;

}
