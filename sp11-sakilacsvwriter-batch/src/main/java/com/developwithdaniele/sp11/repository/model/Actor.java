package com.developwithdaniele.sp11.repository.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "actor")
@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class Actor implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long actorId;

    private String firstName;

    private String lastName;

    private Timestamp lastUpdate;

    @OneToMany(mappedBy = "actor", fetch = FetchType.LAZY)
    private List<FilmActor> filmActors;
}
