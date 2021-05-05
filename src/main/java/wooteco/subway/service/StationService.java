package wooteco.subway.service;

import java.util.List;
import org.springframework.stereotype.Service;
import wooteco.subway.dao.StationDao;
import wooteco.subway.domain.station.Station;

@Service
public class StationService {

    private final StationDao stationDao;

    public StationService(StationDao stationDao) {
        this.stationDao = stationDao;
    }

    public Station createStation(String name) {
        List<Station> stations = stationDao.findAll();
        boolean isDuplicated = stations.stream()
            .anyMatch(station -> station.getName().equals(name));
        if (isDuplicated) {
            throw new IllegalArgumentException("중복!");
        }
        Station station = new Station(name);
        return stationDao.save(station);
    }

    public List<Station> findAll() {
        return stationDao.findAll();
    }

    public void deleteById(long id) {
        stationDao.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("삭제할 수 없습니다."));
        stationDao.deleteById(id);
    }
}
