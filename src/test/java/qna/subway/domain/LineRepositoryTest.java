package qna.subway.domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class LineRepositoryTest {

    @Autowired
    private StationRepository stations;

    @Autowired
    private LineRepository lines;

    @Test
    void saveWithLine() {
        Station station = new Station("잠실역");
        station.setLine(lines.save(new Line("2호선")));
        stations.save(station);
    }

    @Test
    void findByNameWithLine() {
        Station actual = stations.findByName("교대역");
        assertThat(actual).isNotNull();
        assertThat(actual.getLine().getName()).isEqualTo("3호선");
    }

    @Test
    void removeLine() {
        Station actual = stations.findByName("교대역");
        actual.setLine(null);
    }

    @Test
    void findById() {
        Line line = lines.findByName("3호선");
        assertThat(line.getStations()).hasSize(1);
        assertThat(line.getStations().get(0).getName()).isEqualTo("교대역");
    }

    @Test
    void addStation() {
        Line line = lines.findByName("3호선");
        line.addStation(stations.save(new Station("옥수역")));
    }

    @AfterEach
    void afterEach() {
        stations.flush();
    }
}
