package com.formulauno.repository;

import com.formulauno.domain.Team;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import java.util.Collection;
import java.util.Optional;

@Repository
public class TeamDao implements TeamRepository {
    private final JdbcTemplate jdbcTemplate;

    public TeamDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Collection<Team> findAll() {
        String sql = "select * from team";
        return jdbcTemplate.query(sql,
                (rs, i) -> new Team(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("location"),
                        rs.getInt("since"),
                        rs.getInt("till"),
                        null));
    }

    @Override
    public Optional<Team> findById(long id) {
        String sql = "select * from team where id = :id";
        return extractor(sql);
    }

    @Override
    public Optional<Team> findByName(String name) {
        String sql = "select * from team where name = :name";
        return extractor(sql);
    }

    private Optional<Team> extractor(String sql) {
        var list = jdbcTemplate.query(sql,
                (rs, i) -> new Team(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("location"),
                        rs.getInt("since"),
                        rs.getInt("till"),
                        null));
        if (list.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(list.get(0));
        }
    }

    @Override
    public void insert(Team team) {
        var params = new MapSqlParameterSource();
        params.addValue("name", team.getName());
        params.addValue("location", team.getLocation());
        params.addValue("since", team.getSince());
        params.addValue("till", team.getTill());
        var keyHolder = new GeneratedKeyHolder();
        String sql = "insert into team (name, location, since, till) values (name, location, since, till)";
        jdbcTemplate.update(sql, params, keyHolder);
        team.setId((Long)(keyHolder.getKeys().get("id")));
    }

    @Override
    public void update(Team team) {
        var params = new MapSqlParameterSource();
        params.addValue("name", team.getName());
        params.addValue("location", team.getLocation());
        params.addValue("since", team.getSince());
        params.addValue("till", team.getTill());
        params.addValue("id", team.getId());
        String sql = "update team set name = :name, location = :location, since = :since, till = :till where id = :id";
        jdbcTemplate.update(sql, params);
    }
}

