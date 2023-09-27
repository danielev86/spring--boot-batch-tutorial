package com.developwithdaniele.sp10.repository;

import com.developwithdaniele.sp10.repository.model.Film;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IFilmRepository extends PagingAndSortingRepository<Film, Long> {
}
