package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class QuestionTest {

    private User user1;
    private User user2;

    private Question question1;
    private Question question2;

    @BeforeEach
    void setUp() {
        user1 = new User(1L, "user1", "password", "name", "javajigi@slipp.net");
        user2 = new User(2L, "user2", "password", "name", "sanjigi@slipp.net");

        question1 = new Question("title1", "contents1").writeBy(user1);
        question2 = new Question("title2", "contents2").writeBy(user2);
    }

    @DisplayName("질문을 쓴 사람인지 확인한다. - 참인 경우")
    @Test
    void isOwnerTrue() {
        // then
        assertThat(question1.isOwner(user1)).isTrue();
        assertThat(question2.isOwner(user2)).isTrue();
    }

    @DisplayName("질문을 쓴 사람인지 확인한다. - 거짓인 경우")
    @Test
    void isOwnerFalse() {
        // then
        assertThat(question1.isOwner(user2)).isFalse();
        assertThat(question2.isOwner(user1)).isFalse();
    }

    @DisplayName("질문을 삭제한다. - 참인 경우")
    @Test
    void isDeletedTrue() {
        // when
        question1.delete();
        question2.delete();

        // then
        assertThat(question1.isDeleted()).isTrue();
        assertThat(question2.isDeleted()).isTrue();
    }

    @DisplayName("질문을 삭제한다. - 거짓인 경우")
    @Test
    void isDeletedFalse() {
        // then
        assertThat(question1.isDeleted()).isFalse();
        assertThat(question2.isDeleted()).isFalse();
    }
}
