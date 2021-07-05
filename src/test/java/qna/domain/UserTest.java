package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UserTest {

    private User user1;
    private User user2;

    @BeforeEach
    void setUp() {
        user1 = new User(1L, "user1", "password", "name", "javajigi@slipp.net");
        user2 = new User(2L, "user2", "password", "name", "sanjigi@slipp.net");
    }

    @DisplayName("사용자의 정보를 변경한다.")
    @Test
    void update() {
        // when
        user1.update(user2);

        // then
        assertThat(user1).isEqualTo(user2);
    }
}
