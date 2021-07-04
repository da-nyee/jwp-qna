package qna.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import qna.domain.Answer;
import qna.domain.Question;
import qna.domain.User;
import qna.repository.AnswerRepository;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class AnswerRepositoryTest {

    @Autowired
    AnswerRepository answers;

    @DisplayName("DB에 데이터를 저장한다.")
    @Test
    void save() {
        // given
        User user = new User("dani", "dani", "dani", "dani@gmail.com");
        Question question = new Question("num1", "question");
        Answer answer = new Answer(user, question, "answer");

        // when
        Answer savedAnswer = answers.save(answer);

        // then
        assertThat(savedAnswer.getId()).isNotNull();
    }
}
