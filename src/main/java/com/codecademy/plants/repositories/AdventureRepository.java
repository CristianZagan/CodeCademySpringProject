package com.codecademy.plants.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.codecademy.plants.entities.Adventure;

public interface AdventureRepository extends CrudRepository<Adventure, Integer> {

    List<Adventure> findByCountry(String country);
    List<Adventure> findByState(String state);
}