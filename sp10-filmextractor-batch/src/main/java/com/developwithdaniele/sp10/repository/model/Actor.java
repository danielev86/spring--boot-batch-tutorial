package com.developwithdaniele.sp10.repository.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "actor")
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
public class Actor implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long actorId;
    private String firstName;
    private String lastName;
    private Timestamp lastUpdate;

    @OneToMany(mappedBy = "actor", fetch = FetchType.LAZY)
    private List<FilmActor> films;
}
