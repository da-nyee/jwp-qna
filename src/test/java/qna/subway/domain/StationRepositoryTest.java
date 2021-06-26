package qna.subway.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

// 이 어노테이션을 붙이면 JPA에 관련된 의존성, Configurations 다 활성화 O
// 내부에 @Transactional 있음 👉 기본적으로 하나의 테스트가 끝날 때마다 rollback O
@DataJpaTest
class StationRepositoryTest {
    // AUTO_INCREMENT는 rollback 영향 X
    // 그래서 가급적 id와 관련한 테스트는 그 값이 있는지/없는지 정도로만 테스트하고, 실제 그 값이 무엇인지 테스트하는 건 피하는 게 좋음 👉 id가 있는 게 중요하지, id가 무엇인지는 우리의 테스트 관심사가 X

    @Autowired
    private StationRepository stations;

    @Test
    void save() {
        Station station = new Station("잠실역");
        Station actual = stations.save(station);

        assertThat(actual.getId()).isNotNull();
        assertThat(actual.getName()).isEqualTo("잠실역");
    }

    @Test
    void findByName() {
        stations.save(new Station("잠실역"));
        Station actual = stations.findByName("잠실역");

        assertThat(actual.getId()).isNotNull();
        assertThat(actual.getName()).isEqualTo("잠실역");
    }

    @Test
    void isEqualToAndIsSameAs() {
        Station station1 = stations.save(new Station("잠실역"));
        Station station2 = stations.findByName("잠실역");

        assertThat(station2.getId()).isNotNull();
        assertThat(station2.getName()).isEqualTo("잠실역");

        assertThat(station1).isEqualTo(station2);       // 동등성 보장
        assertThat(station1).isSameAs(station2);        // 동일성 보장
    }

    @Test
    void updateDirtyChecking() {
        Station station = stations.save(new Station("잠실역"));
        station.changeName("잠실나루역");        // JPA 변경 감지 / 더티 체킹
        Station actual = stations.findByName("잠실나루역");

        assertThat(actual).isNotNull();
    }

    @Test
    void updateNoDirtyChecking() {
        Station station = stations.save(new Station("잠실역"));
        station.changeName("잠실나루역");
        station.changeName("잠실역");
        Station actual = stations.findByName("잠실역");

        assertThat(actual).isNotNull();
    }
}
