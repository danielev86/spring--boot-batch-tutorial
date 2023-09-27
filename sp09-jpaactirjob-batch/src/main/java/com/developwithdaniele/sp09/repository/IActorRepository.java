package com.developwithdaniele.sp09.repository;

import com.developwithdaniele.sp09.repository.model.Actor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IActorRepository extends PagingAndSortingRepository<Actor, Long> {

    Page<Actor> findActorsByFirstName(String firstName, Pageable pageable);

}
