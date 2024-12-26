package com.formulauno.repository;

import com.formulauno.domain.Engine;

import java.util.Collection;
import java.util.Optional;


public interface EngineRepository {
    Collection<Engine> findAll();
    Optional<Engine> findByManufacturer(String manufacturer);
    void insert(Engine engine);
    void remove(Engine engine);
}
