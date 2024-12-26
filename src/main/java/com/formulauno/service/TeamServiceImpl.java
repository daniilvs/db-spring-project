package com.formulauno.service;

import com.formulauno.domain.Team;
import com.formulauno.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {
    private final TeamRepository teamRepository;

    @Override
    public Collection<Team> findAll() {
        return teamRepository.findAll();
    }

    @Override
    public Optional<Team> findById(long id) {
        return teamRepository.findById(id);
    }

    @Override
    public Optional<Team> findByName(String name) {
        return teamRepository.findByName(name);
    }

    @Override
    public void insert(Team team) {
        teamRepository.insert(team);
    }

    @Override
    public void update(Team team) {
        teamRepository.update(team);
    }
}