package qna.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import qna.domain.DeleteHistory;
import qna.domain.User;

import static org.assertj.core.api.Assertions.assertThat;
import static qna.domain.ContentType.QUESTION;

@DataJpaTest
class DeleteHistoryRepositoryTest {

    @Autowired
    DeleteHistoryRepository deleteHistories;

    @DisplayName("DB에 데이터를 저장한다.")
    @Test
    void save() {
        // given
        User deletedUser = new User("dani", "dani", "dani", "dani@gmail.com");
        DeleteHistory deleteHistory = new DeleteHistory(QUESTION, 1L, deletedUser);

        // when
        DeleteHistory savedDeleteHistory = deleteHistories.save(deleteHistory);

        // then
        assertThat(savedDeleteHistory.getId()).isNotNull();
    }
}
