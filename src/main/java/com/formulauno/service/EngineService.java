package com.formulauno.service;

import com.formulauno.domain.Engine;
import java.util.Collection;


public interface EngineService {
    Collection<Engine> findAll();
    void insert(Engine engine);
    void remove(Engine engine);
}