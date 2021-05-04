package wooteco.subway.line;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import org.springframework.util.ReflectionUtils;
import wooteco.subway.exception.DuplicateException;

public class LineDao {

    private final List<Line> lines = new ArrayList<>();
    private Long seq = 0L;

    public Line save(Line line) {
        Line persistLine = createNewObject(line);
        lines.add(persistLine);
        return persistLine;
    }

    private Line createNewObject(Line line) {
        validateDuplicateName(line);
        Field field = ReflectionUtils.findField(Line.class, "id");
        field.setAccessible(true);
        ReflectionUtils.setField(field, line, ++seq);
        return line;
    }

    private void validateDuplicateName(Line line) {
        String name = line.getName();
        if (lines.stream().anyMatch(it -> it.isSameName(name))) {
            throw new DuplicateException();
        }
    }

    public List<Line> findAll() {
        return new ArrayList<>(lines);
    }
}
