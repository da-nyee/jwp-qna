package qna.service;

import static qna.domain.ContentType.ANSWER;
import static qna.domain.ContentType.QUESTION;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qna.domain.Answer;
import qna.domain.DeleteHistory;
import qna.domain.Question;
import qna.domain.User;
import qna.exception.CannotDeleteException;
import qna.exception.NotFoundException;
import qna.repository.AnswerRepository;
import qna.repository.QuestionRepository;

@Service
public class QnaService {

    private static final Logger log = LoggerFactory.getLogger(QnaService.class);

    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final DeleteHistoryService deleteHistoryService;

    public QnaService(QuestionRepository questionRepository, AnswerRepository answerRepository,
        DeleteHistoryService deleteHistoryService) {
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
        this.deleteHistoryService = deleteHistoryService;
    }

    @Transactional(readOnly = true)
    public Question findQuestionById(Long id) {
        return questionRepository.findByIdAndDeletedFalse(id)
            .orElseThrow(NotFoundException::new);
    }

    @Transactional
    public void deleteQuestion(User loginUser, Long questionId) throws CannotDeleteException {
        Question question = findQuestionById(questionId);
        validateQuestionOwner(loginUser, question);

        List<Answer> answers = answerRepository.findByQuestionIdAndDeletedFalse(questionId);
        for (Answer answer : answers) {
            validateAnswerOwner(loginUser, answer);
        }

        List<DeleteHistory> deleteHistories = new ArrayList<>();
        question.delete();
        deleteHistories.add(new DeleteHistory(QUESTION, questionId, question.getWriter()));
        for (Answer answer : answers) {
            answer.delete();
            deleteHistories.add(new DeleteHistory(ANSWER, answer.getId(), answer.getWriter()));
        }
        deleteHistoryService.saveAll(deleteHistories);
    }

    private void validateQuestionOwner(User loginUser, Question question) {
        if (isQuestionOwner(loginUser, question)) {
            throw new CannotDeleteException("질문을 삭제할 권한이 없습니다.");
        }
    }

    private boolean isQuestionOwner(User loginUser, Question question) {
        return !question.isOwner(loginUser);
    }

    private void validateAnswerOwner(User loginUser, Answer answer) {
        if (isAnswerOwner(loginUser, answer)) {
            throw new CannotDeleteException("다른 사람이 쓴 답변이 있어 삭제할 수 없습니다.");
        }
    }

    private boolean isAnswerOwner(User loginUser, Answer answer) {
        return !answer.isOwner(loginUser);
    }
}
