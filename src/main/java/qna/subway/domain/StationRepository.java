package qna.subway.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// JpaRepository/CrudRepository 상속하는 순간 bean이 됨 👉 따로 @Repository 안 붙여줘도 Spring이 알아서 bean으로 관리함
public interface StationRepository extends JpaRepository<Station, Long> {       // JpaRepository<T, ID> 👉 <Entity 타입, Id 타입>

    // 메소드 시그니처만 추가하면, Spring Data JPA가 이 메소드 이름을 보고 자동으로 해당 기능을 만들어줌
    Station findByName(String name);

    List<Station> findAllByName(String name);
}
