package com.formulauno.repository;

import com.formulauno.domain.Engine;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class EngineDao implements EngineRepository {
    private final JdbcTemplate jdbcTemplate;

    public EngineDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void insert(Engine engine) {
        var keyHolder = new GeneratedKeyHolder();
        String sql = "insert into engine (manufacturer) values (manufacturer)";
        jdbcTemplate.update(sql,
                SqlParams.of("manufacturer", engine.getManufacturer()),
                keyHolder);
        engine.setId((Long)(keyHolder.getKeys().get("id")));
    }

    @Override
    public void remove(Engine engine) {
        String sql = "DELETE FROM engine WHERE manufacturer = ?";
        jdbcTemplate.update(sql, engine.getManufacturer());
    }

    @Override
    public Collection<Engine> findAll() {
        String sql = "SELECT manufacturer FROM engine";
        return jdbcTemplate.query(sql , (rs, rowNum) -> new Engine(
                rs.getLong("id"),
                rs.getString("manufacturer"),
                null));
    }

    private static class SqlParams {
        public static MapSqlParameterSource of(String k1, Object v1) {
            var params = new MapSqlParameterSource();
            params.addValue(k1, v1);
            return params;
        }

        public static MapSqlParameterSource of(String k1, Object v1, String k2, Object v2) {
            var params = new MapSqlParameterSource();
            params.addValue(k1, v1);
            params.addValue(k2, v2);
            return params;
        }
    }
}