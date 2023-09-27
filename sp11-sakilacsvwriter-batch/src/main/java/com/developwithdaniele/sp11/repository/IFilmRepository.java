package com.developwithdaniele.sp11.repository;

import com.developwithdaniele.sp11.repository.model.Film;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IFilmRepository extends PagingAndSortingRepository<Film, Long> {

    @Query("SELECT  film from Film film join  film.filmActors ")
    Page<Film> findAll(Pageable pageable);

}
