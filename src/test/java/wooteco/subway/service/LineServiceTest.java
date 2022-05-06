package wooteco.subway.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import wooteco.subway.dao.LineDao;
import wooteco.subway.domain.Line;
import wooteco.subway.dto.LineRequest;
import wooteco.subway.dto.LineResponse;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.BDDMockito.given;

@SpringBootTest
class LineServiceTest {
    @Mock
    LineDao lineDao;

    @InjectMocks
    LineService lineService;

    @Test
    @DisplayName("지하철 노선 이름이 중복되지 않는다면 등록할 수 있다.")
    void save() {
        LineRequest lineRequest = new LineRequest("name", "red");
        given(lineDao.isExistName("name")).willReturn(false);
        given(lineDao.save("name", "red")).willReturn(new Line(1L, "name", "red"));

        assertThat(lineService.save(lineRequest).getId()).isEqualTo(1L);
        assertThat(lineService.save(lineRequest).getName()).isEqualTo("name");
        assertThat(lineService.save(lineRequest).getColor()).isEqualTo("red");
    }

    @Test
    @DisplayName("지하철 노선 이름이 중복된다면 등록할 수 없다.")
    void saveDuplicate() {
        LineRequest lineRequest = new LineRequest("name", "red");
        given(lineDao.isExistName("name")).willReturn(true);
        given(lineDao.save("name", "red")).willReturn(new Line(1L, "name", "red"));

        assertThatThrownBy(() -> lineService.save(lineRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("지하철 노선 이름이 중복될 수 없습니다.");
    }

    @Test
    @DisplayName("지하철 노선 목록을 조회할 수 있다.")
    void findAll() {
        given(lineDao.findAll()).willReturn(List.of(new Line(1L, "name", "red"), new Line(2L, "name2", "blue")));

        List<Long> ids = lineService.findAll().stream()
                .map(LineResponse::getId)
                .collect(Collectors.toList());

        List<String> names = lineService.findAll().stream()
                .map(LineResponse::getName)
                .collect(Collectors.toList());

        List<String> colors = lineService.findAll().stream()
                .map(LineResponse::getColor)
                .collect(Collectors.toList());

        assertThat(ids).containsOnly(1L, 2L);
        assertThat(names).containsOnly("name", "name2");
        assertThat(colors).containsOnly("red", "blue");
    }

    @Test
    @DisplayName("지하철 노선을 조회할 수 있다.")
    void findById() {
        given(lineDao.findById(1L)).willReturn(new Line(1L, "name", "red"));

        LineResponse response = lineService.findById(1L);

        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getName()).isEqualTo("name");
        assertThat(response.getColor()).isEqualTo("red");
    }


    @Test
    @DisplayName("지하철 노선을 삭제할 수 있다.")
    void deleteById() {
        assertDoesNotThrow(() -> lineService.deleteById(1L));
    }

    @Test
    @DisplayName("지하철 노선을 수정할 수 있다.")
    void update() {
        LineRequest lineRequest = new LineRequest("name2", "blue");

        given(lineDao.isExistNameWithoutItself(1L, "name")).willReturn(false);

        assertDoesNotThrow(() -> lineService.update(1L, lineRequest));
    }

    @Test
    @DisplayName("지하철 노선 이름이 중복된다면 수정할 수 없다.")
    void updateDuplicate() {
        LineRequest lineRequest = new LineRequest("name", "blue");

        given(lineDao.isExistNameWithoutItself(1L, "name")).willReturn(true);

        assertThatThrownBy(() -> lineService.update(1L, lineRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("지하철 노선 이름이 중복될 수 없습니다.");
    }
}
