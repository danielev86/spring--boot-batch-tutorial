package com.developwithdaniele.sp11.repository.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class FilmActor implements Serializable {

    @EmbeddedId
    private FilmActorKey id;

    @ManyToOne
    @MapsId("actorId")
    @JoinColumn(name = "actor_id")
    private Actor actor;

    @ManyToOne
    @MapsId("filmId")
    @JoinColumn(name="film_id")
    private Film film;

    private Timestamp lastUpdate;
}
