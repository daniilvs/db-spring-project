package com.formulauno.repository;

import com.formulauno.domain.Team;

import java.util.Collection;
import java.util.Optional;

public interface TeamRepository {
    Collection<Team> findAll();
    Optional<Team> findById(long id);
    Optional<Team> findByName(String name);
    void insert(Team team);
    void update(Team team);
}
