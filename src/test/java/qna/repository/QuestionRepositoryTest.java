package qna.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import qna.domain.Question;

@DataJpaTest
class QuestionRepositoryTest {

    @Autowired
    QuestionRepository questions;

    @DisplayName("DB에 데이터를 저장한다.")
    @Test
    void save() {
        // given
        Question question = new Question("num1", "question");

        // when
        Question savedQuestion = questions.save(question);

        // then
        assertThat(savedQuestion.getId()).isNotNull();
    }
}
