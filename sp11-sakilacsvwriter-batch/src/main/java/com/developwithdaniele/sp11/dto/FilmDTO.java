package com.developwithdaniele.sp11.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@ToString
public class FilmDTO implements Serializable {

    private Long filmId;

    private String title;

    private int releaseDate;

    private String actors;

}
