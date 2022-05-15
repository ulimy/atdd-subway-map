package wooteco.subway.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import wooteco.subway.domain.Station;
import wooteco.subway.exception.BusinessException;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Repository
public class StationDao {

    private static final String STATION_NOT_FOUND = "존재하지 않는 지하철역입니다.";

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public StationDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("STATION")
                .usingGeneratedKeyColumns("id");
    }

    private final RowMapper<Station> stationRowMapper = (resultSet, rowNum) ->
            new Station(
                    resultSet.getLong("id"),
                    resultSet.getString("name")
            );

    public Station save(String name) {
        Long id = simpleJdbcInsert.executeAndReturnKey(Map.of("name", name)).longValue();
        return new Station(id, name);
    }

    public Station getById(Long id) {
        try {
            final String sql = "SELECT * FROM STATION WHERE id = ?";
            return jdbcTemplate.queryForObject(sql, stationRowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            throw new BusinessException(STATION_NOT_FOUND);
        }
    }

    public List<Station> findByLineId(Long lineId) {
        final String sql = "SELECT * FROM STATION WHERE " +
                "id IN ( SELECT up_station_id FROM SECTION WHERE line_id = ? ) OR " +
                "id IN ( SELECT down_station_id FROM SECTION WHERE line_id = ? )";

        return jdbcTemplate.query(sql, stationRowMapper, lineId, lineId);
    }

    public List<Station> findAll() {
        final String sql = "SELECT * FROM STATION";
        return jdbcTemplate.query(sql, stationRowMapper);
    }

    public void delete(Long id) {
        final String sql = "DELETE FROM STATION WHERE id=?";
        int count = jdbcTemplate.update(sql, id);
        if (count == 0) {
            throw new BusinessException(STATION_NOT_FOUND);
        }
    }

    public boolean isExistName(String name) {
        final String sql = "SELECT EXISTS (SELECT * FROM STATION WHERE name=?)";
        return jdbcTemplate.queryForObject(sql, Boolean.class, name);
    }

}
