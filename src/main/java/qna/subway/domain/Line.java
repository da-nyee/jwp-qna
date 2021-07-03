package qna.subway.domain;

import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Line {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(nullable = false)
    private String name;

    // 주의할 점 !! 앞에 @OneXXXX 으로 시작하는 어노테이션들 특징 --> `mappedBy` 속성, 옵션 있음 --> ex. 이 line, station 관계에서 어떤 녀석이 FK를 가지고 있냐 ?
    // FK 누가 들고 있는지 알려줘야 함 --> 그래야지만 JPA가 스키마를 그릴 때 ex. station에 있는 line 사용 O
    // 이걸 안 알려주면? station가 FK를 들고 있을 거라 생각 X --> 컴퓨터는 생각보다 멍청
    // FK 누가 가지고 있는지에 대한 힌트를 줘야함 !
    @OneToMany(mappedBy = "line", orphanRemoval = true)
    private List<Station> stations = new ArrayList<>();

    protected Line() {
    }

    public Line(@NonNull String name) {
        this(null, name);
    }

    public Line(Long id, @NonNull String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public List<Station> getStations() {
        return stations;
    }

    public void addStation(Station station) {
        station.setLine(this);
        stations.add(station);
    }

    public void removeStation(Station station) {
        stations.remove(station);
    }
}
