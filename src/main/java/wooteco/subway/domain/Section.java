package wooteco.subway.domain;

import wooteco.subway.exception.BusinessException;

public class Section {

    private static final String SAME_UP_DOWN = "상행종점과 하행종점은 같은 지하철역일 수 없습니다.";
    private static final String WRONG_DISTANCE = "거리는 1이상의 정수만 허용됩니다.";

    private final Long id;
    private final Long lineId;
    private final Long upStationId;
    private final Long downStationId;
    private final int distance;

    public Section(Long lineId, Long upStationId, Long downStationId, int distance) {
        this(0L, lineId, upStationId, downStationId, distance);
        validateSameId(upStationId, downStationId);
        validateDistance(distance);
    }

    public Section(Long id, Long lineId, Long upStationId, Long downStationId, int distance) {
        this.id = id;
        this.lineId = lineId;
        this.upStationId = upStationId;
        this.downStationId = downStationId;
        this.distance = distance;
    }

    public boolean isSameLine(Section section) {
        return this.lineId == section.lineId;
    }

    public boolean isSameUp(Long id) {
        return upStationId == id;
    }

    public boolean isSameDown(Long id) {
        return downStationId == id;
    }

    public boolean isSameUpDown(Section target) {
        return upStationId == target.upStationId && downStationId == target.downStationId;
    }

    public boolean isSource(Section target) {
        return ((upStationId == target.upStationId && downStationId != target.downStationId) ||
                (upStationId != target.upStationId && downStationId == target.downStationId));
    }

    public boolean isShorterDistance(Section target) {
        return distance <= target.distance;
    }

    public Section makeRest(Section target) {
        if (upStationId == target.upStationId) {
            return new Section(0L, lineId, target.downStationId, downStationId, distance - target.distance);
        }

        return new Section(0L, lineId, upStationId, target.upStationId, distance - target.distance);
    }

    public Section combine(Section target) {
        if (downStationId == target.upStationId) {
            return new Section(0L, lineId, upStationId, target.downStationId, distance + target.distance);
        }
        return new Section(0L, lineId, target.upStationId, downStationId, distance + target.distance);
    }

    public Long getId() {
        return id;
    }

    public Long getLineId() {
        return lineId;
    }

    public Long getUpStationId() {
        return upStationId;
    }

    public Long getDownStationId() {
        return downStationId;
    }

    public int getDistance() {
        return distance;
    }

    private static void validateSameId(Long upStationId, Long downStationId) {
        if (upStationId == downStationId) {
            throw new BusinessException(SAME_UP_DOWN);
        }
    }

    private static void validateDistance(int distance) {
        if (distance < 1) {
            throw new BusinessException(WRONG_DISTANCE);
        }
    }


}
