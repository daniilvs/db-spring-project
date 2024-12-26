package com.formulauno.service;

import com.formulauno.domain.Engine;
import java.util.Collection;
import java.util.Optional;


public interface EngineService {
    Collection<Engine> findAll();
    Optional<Engine> findByManufacturer(String manufacturer);
    void insert(Engine engine);
    void remove(Engine engine);
}