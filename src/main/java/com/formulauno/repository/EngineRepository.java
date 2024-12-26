package com.formulauno.repository;

import com.formulauno.domain.Engine;

import java.util.Collection;


public interface EngineRepository {
    Collection<Engine> findAll();
    void insert(Engine engine);
    void remove(Engine engine);
}
