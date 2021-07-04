package qna.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static qna.domain.ContentType.*;

@DataJpaTest
class DeleteHistoryRepositoryTest {

    @Autowired
    DeleteHistoryRepository deleteHistories;

    @DisplayName("DB에 데이터를 저장한다.")
    @Test
    void save() {
        // given
        DeleteHistory deleteHistory = new DeleteHistory(QUESTION, 1L, 2L, LocalDateTime.now());

        // when
        DeleteHistory savedDeleteHistory = deleteHistories.save(deleteHistory);

        // then
        assertThat(savedDeleteHistory.getId()).isNotNull();
    }
}
