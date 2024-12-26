package com.formulauno.service;

import com.formulauno.domain.Engine;
import com.formulauno.repository.EngineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EngineServiceImpl implements EngineService {
    private final EngineRepository engineRepository;

    @Override
    public Collection<Engine> findAll() {
        return engineRepository.findAll();
    }

    @Override
    public Optional<Engine> findByManufacturer(String manufacturer) { return engineRepository.findByManufacturer(manufacturer); }

    @Override
    public void insert(Engine engine) {
        engineRepository.insert(engine);
    }

    @Override
    public void remove(Engine engine) {
        engineRepository.remove(engine);
    }
}