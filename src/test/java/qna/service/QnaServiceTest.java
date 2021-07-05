package qna.service;

import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import qna.exception.CannotDeleteException;
import qna.domain.*;
import qna.repository.AnswerRepository;
import qna.repository.QuestionRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static qna.domain.ContentType.ANSWER;
import static qna.domain.ContentType.QUESTION;

@ExtendWith(MockitoExtension.class)
class QnaServiceTest {

    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private AnswerRepository answerRepository;

    @Mock
    private DeleteHistoryService deleteHistoryService;

    @InjectMocks
    private QnaService qnaService;

    private User user1;
    private User user2;

    private Question question;

    private Answer answer1;
    private Answer answer2;

    @BeforeEach
    public void setUp() throws Exception {
        user1 = new User(1L, "user1", "password", "name", "javajigi@slipp.net");
        user2 = new User(2L, "user2", "password", "name", "sanjigi@slipp.net");

        question = new Question(1L, "title1", "contents1").writeBy(user1);

        answer1 = new Answer(1L, user1, question, "Answers Contents1");
        answer2 = new Answer(2L, user2, question, "Answers Contents1");

        question.addAnswer(answer1);
    }

    @Test
    public void delete_성공() throws Exception {
        when(questionRepository.findByIdAndDeletedFalse(question.getId()))
            .thenReturn(Optional.of(question));
        when(answerRepository.findByQuestionIdAndDeletedFalse(question.getId()))
            .thenReturn(Collections.singletonList(answer1));

        assertThat(question.isDeleted()).isFalse();
        qnaService.deleteQuestion(user1, question.getId());

        assertThat(question.isDeleted()).isTrue();
        verifyDeleteHistories();
    }

    @Test
    public void delete_다른_사람이_쓴_글() throws Exception {
        when(questionRepository.findByIdAndDeletedFalse(question.getId()))
            .thenReturn(Optional.of(question));

        assertThatThrownBy(() -> qnaService.deleteQuestion(user2, question.getId()))
                .isInstanceOf(CannotDeleteException.class);
    }

    @Test
    public void delete_성공_질문자_답변자_같음() throws Exception {
        when(questionRepository.findByIdAndDeletedFalse(question.getId()))
            .thenReturn(Optional.of(question));
        when(answerRepository.findByQuestionIdAndDeletedFalse(question.getId()))
            .thenReturn(Collections.singletonList(answer1));

        qnaService.deleteQuestion(user1, question.getId());

        assertThat(question.isDeleted()).isTrue();
        assertThat(answer1.isDeleted()).isTrue();
        verifyDeleteHistories();
    }

    @Test
    public void delete_답변_중_다른_사람이_쓴_글() throws Exception {
        this.question.addAnswer(answer2);

        when(questionRepository.findByIdAndDeletedFalse(this.question.getId()))
            .thenReturn(Optional.of(this.question));
        when(answerRepository.findByQuestionIdAndDeletedFalse(this.question.getId()))
            .thenReturn(Arrays.asList(answer1, answer2));

        assertThatThrownBy(() -> qnaService.deleteQuestion(user1, this.question.getId()))
                .isInstanceOf(CannotDeleteException.class);
    }

    private void verifyDeleteHistories() {
        List<DeleteHistory> deleteHistories = Arrays.asList(
                new DeleteHistory(QUESTION, question.getId(), question.getWriter()),
                new DeleteHistory(ANSWER, answer1.getId(), answer1.getWriter())
        );

        verify(deleteHistoryService).saveAll(deleteHistories);
    }
}
