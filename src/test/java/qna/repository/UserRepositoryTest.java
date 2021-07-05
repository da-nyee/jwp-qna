package qna.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import qna.domain.User;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    UserRepository users;

    @DisplayName("DB에 데이터를 저장한다.")
    @Test
    void save() {
        // given
        User user = new User("dani", "dani", "dani", "dani@gmail.com");

        // when
        User savedUser = users.save(user);

        // then
        assertThat(savedUser.getId()).isNotNull();
    }
}
