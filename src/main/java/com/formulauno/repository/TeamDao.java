package com.formulauno.repository;

//import com.formulauno.domain.Engine;
import com.formulauno.domain.Team;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

@Repository
public class TeamDao implements TeamRepository {
    private final static RowMapper<Team> MAPPER = (rs, i) ->
            new Team(rs.getLong("id"),
            rs.getString("name"),
            rs.getString("location"),
            rs.getInt("since"),
            rs.getInt("till"),
            null);

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public TeamDao(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Collection<Team> findAll() {
        String sql = "select * from team";
        return jdbcTemplate.query(sql, MAPPER);
    }

    @Override
    public Optional<Team> findById(long id) {
        String sql = "select * from team where id = :id";
        var params = new MapSqlParameterSource();
        params.addValue("id", id);
        var list = namedParameterJdbcTemplate.query(sql, params, MAPPER);
        if (list.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(list.getFirst());
        }
    }

    @Override
    public Optional<Team> findByName(String name) {
        String sql = "select * from team where name = :name";
        var params = new MapSqlParameterSource();
        params.addValue("name", name);
        var list = namedParameterJdbcTemplate.query(sql, params, MAPPER);
        if (list.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(list.getFirst());
        }
    }

//    private Optional<Team> extractor(String sql) {
//        var list = namedParameterJdbcTemplate.query(sql, MAPPER);
//        if (list.isEmpty()) {
//            return Optional.empty();
//        } else {
//            return Optional.of(list.get(0));
//        }
//    }

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
        team.setId((Long)(Objects.requireNonNull(keyHolder.getKeys()).get("id")));
    }

//    @Override
//    public void update(Team team, String name, String location, int since, int till) {
//        var params = new MapSqlParameterSource();
//        if (name == null) {
//            params.addValue("name", team.getName());
//        }
//        else params.addValue("name", name);
//        if (location == null) {
//            params.addValue("location", team.getLocation());
//        }
//        else params.addValue("location", location);
//        if (since == 0) {
//            params.addValue("since", team.getSince());
//        }
//        else params.addValue("since", since);
//        if (till == 0) {
//            params.addValue("till", team.getTill());
//        } params.addValue("till", till);
//        params.addValue("id", team.getId());
//        String sql = "update team set name = :name, location = :location, since = :since, till = :till where id = :id";
//        jdbcTemplate.update(sql, params);
//    }

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

