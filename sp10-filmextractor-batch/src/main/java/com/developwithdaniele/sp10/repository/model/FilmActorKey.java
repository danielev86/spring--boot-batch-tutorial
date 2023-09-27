package com.developwithdaniele.sp10.repository.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class FilmActorKey implements Serializable {

    @Column(name = "actor_id")
    private Long actorId;

    @Column(name = "film_id")
    private Long filmId;

}
