package qna.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AnswerTest {

    private User user1;
    private User user2;

    private Answer answer1;
    private Answer answer2;

    @BeforeEach
    void setUp() {
        user1 = new User(1L, "user1", "password", "name", "javajigi@slipp.net");
        user2 = new User(2L, "user2", "password", "name", "sanjigi@slipp.net");

        Question question = new Question("title1", "contents1").writeBy(user1);

        answer1 = new Answer(user1, question, "Answers Contents1");
        answer2 = new Answer(user2, question, "Answers Contents2");
    }

    @DisplayName("답변을 쓴 사람인지 확인한다. - 참인 경우")
    @Test
    void isOwnerTrue() {
        // then
        assertThat(answer1.isOwner(user1)).isTrue();
        assertThat(answer2.isOwner(user2)).isTrue();
    }

    @DisplayName("답변을 쓴 사람인지 확인한다. - 거짓인 경우")
    @Test
    void isOwnerFalse() {
        // then
        assertThat(answer1.isOwner(user2)).isFalse();
        assertThat(answer2.isOwner(user1)).isFalse();
    }

    @DisplayName("답변을 삭제한다. - 참인 경우")
    @Test
    void isDeletedTrue() {
        // when
        answer1.delete();
        answer2.delete();

        // then
        assertThat(answer1.isDeleted()).isTrue();
        assertThat(answer2.isDeleted()).isTrue();
    }

    @DisplayName("답변을 삭제한다. - 거짓인 경우")
    @Test
    void isDeletedFalse() {
        // then
        assertThat(answer1.isDeleted()).isFalse();
        assertThat(answer2.isDeleted()).isFalse();
    }
}
