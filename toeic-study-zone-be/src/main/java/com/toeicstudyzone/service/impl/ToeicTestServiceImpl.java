package com.toeicstudyzone.service.impl;

import com.toeicstudyzone.dto.request.TestSubmissionRequest;
import com.toeicstudyzone.dto.request.ToeicTestDTO;
import com.toeicstudyzone.dto.response.QuestionResultDTO;
import com.toeicstudyzone.dto.response.TestResultResponse;
import com.toeicstudyzone.entity.*;
import com.toeicstudyzone.exception.ResourceNotFoundException;
import com.toeicstudyzone.repository.*;
import com.toeicstudyzone.service.ToeicTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.toeicstudyzone.dto.response.LatestTestResponse;

import java.time.LocalDateTime;
import java.util.List;
// import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class ToeicTestServiceImpl implements ToeicTestService {

    @Autowired
    private ToeicTestRepository toeicTestRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerOptionRepository answerOptionRepository;

    @Autowired
    private UserTestHistoryRepository userTestHistoryRepository;

    @Autowired
    private UserQuestionResponseRepository userQuestionResponseRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Override
    @Transactional(readOnly = true)
    public ToeicTestDTO getTest(Long testId) {
        ToeicTest test = toeicTestRepository.findById(testId)
                .orElseThrow(() -> new ResourceNotFoundException("Test not found with ID: " + testId));

        Long commentCount = commentRepository.countByTestId(testId);
        Long participantCount = userTestHistoryRepository.countByTestId(testId);

        return new ToeicTestDTO(
                test.getId(),
                test.getTitle(),
                test.getTestYear().getYear(),
                test.getDescription(),
                test.getTotalQuestions(),
                test.getTimeLimit(),
                test.getIsFree(),
                test.getIsPublished(),
                test.getIsPlacementTest(),
                test.getCreatedAt(),
                test.getUpdatedAt(),
                participantCount,
                commentCount);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ToeicTestDTO> getTestsByYear(Long yearId) {
        List<ToeicTest> tests = toeicTestRepository.findByTestYearId(yearId);

        return tests.stream().map(test -> {
            Long commentCount = commentRepository.countByTestId(test.getId());
            Long participantCount = userTestHistoryRepository.countByTestId(test.getId());

            return new ToeicTestDTO(
                    test.getId(),
                    test.getTitle(),
                    test.getTestYear().getYear(),
                    test.getDescription(),
                    test.getTotalQuestions(),
                    test.getTimeLimit(),
                    test.getIsFree(),
                    test.getIsPublished(),
                    test.getIsPlacementTest(),
                    test.getCreatedAt(),
                    test.getUpdatedAt(),
                    participantCount,
                    commentCount);
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ToeicTestDTO> getAllTests() {
        List<ToeicTest> tests = toeicTestRepository.findAll();

        return tests.stream().map(test -> {
            Long commentCount = commentRepository.countByTestId(test.getId());
            Long participantCount = userTestHistoryRepository.countByTestId(test.getId());

            return new ToeicTestDTO(
                    test.getId(),
                    test.getTitle(),
                    test.getTestYear().getYear(),
                    test.getDescription(),
                    test.getTotalQuestions(),
                    test.getTimeLimit(),
                    test.getIsFree(),
                    test.getIsPublished(),
                    test.getIsPlacementTest(),
                    test.getCreatedAt(),
                    test.getUpdatedAt(),
                    participantCount,
                    commentCount);
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public TestResultResponse submitTest(Long userId, TestSubmissionRequest request) {
        ToeicTest test = toeicTestRepository.findById(request.getToeicTestId())
                .orElseThrow(
                        () -> new ResourceNotFoundException("Test not found with ID: " + request.getToeicTestId()));

        UserTestHistory history = new UserTestHistory();
        User user = new User();
        user.setId(userId);
        history.setUser(user);
        history.setTest(test);
        history.setStartTime(LocalDateTime.now());
        history.setEndTime(LocalDateTime.now());

        AtomicInteger listeningScore = new AtomicInteger(0);
        AtomicInteger readingScore = new AtomicInteger(0);
        AtomicInteger correctCount = new AtomicInteger(0);

        List<QuestionResultDTO> results = request.getAnswers().stream().map(answer -> {
            Question question = questionRepository.findById(answer.getQuestionId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Question not found with ID: " + answer.getQuestionId()));

            AnswerOption correctOption = question.getAnswerOptions().stream()
                    .filter(AnswerOption::isCorrect)
                    .findFirst()
                    .orElse(null);

            boolean isCorrect = answer.getSelectedOptionId() != null && correctOption != null &&
                    answer.getSelectedOptionId().equals(correctOption.getId());

            UserQuestionResponse userResponse = new UserQuestionResponse();
            userResponse.setUserTestHistory(history);
            userResponse.setQuestion(question);
            AnswerOption selectedAnswer = (answer.getSelectedOptionId() != null)
                    ? answerOptionRepository.findById(answer.getSelectedOptionId())
                            .orElse(null)
                    : null;
            userResponse.setSelectedAnswer(selectedAnswer);
            userResponse.setIsCorrect(isCorrect);
            userResponse.setTimeSpent(answer.getTimeSpent());
            userQuestionResponseRepository.save(userResponse);

            if (isCorrect) {
                if (question.getTestSection().getSectionType() == TestSection.SectionType.LISTENING) {
                    listeningScore.addAndGet(5);
                } else {
                    readingScore.addAndGet(5);
                }
                correctCount.incrementAndGet();
            }

            QuestionResultDTO result = new QuestionResultDTO();
            result.setQuestionId(question.getId());
            result.setCorrectAnswerId(correctOption != null ? correctOption.getId() : null);
            result.setSelectedAnswerId(answer.getSelectedOptionId());
            result.setCorrect(isCorrect);
            return result;
        }).collect(Collectors.toList());

        history.setTotalScore(listeningScore.get() + readingScore.get());
        history.setListeningScore(listeningScore.get());
        history.setReadingScore(readingScore.get());
        history.setCorrectAnswers(correctCount.get());
        history.setCompleted(true);

        userTestHistoryRepository.save(history);

        TestResultResponse response = new TestResultResponse();
        response.setTotalScore(history.getTotalScore());
        response.setListeningScore(history.getListeningScore());
        response.setReadingScore(history.getReadingScore());
        response.setCorrectCount(correctCount.get());
        response.setPercentage((double) correctCount.get() / test.getTotalQuestions() * 100);
        response.setResults(results);
        return response;
    }

    @Override
    @Transactional
    public ToeicTest createTest(ToeicTest toeicTest) {
        if (toeicTest.getTestYear() == null || toeicTest.getTestYear().getYear() == 0) {
            throw new IllegalArgumentException("Test year is required");
        }
        if (toeicTest.getTitle() == null || toeicTest.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Test title is required");
        }
        if (toeicTest.getTotalQuestions() == null || toeicTest.getTotalQuestions() <= 0) {
            throw new IllegalArgumentException("Total questions must be greater than 0");
        }
        return toeicTestRepository.save(toeicTest);
    }

    @Override
    @Transactional
    public ToeicTest updateTest(Long id, ToeicTest updatedTest) {
        ToeicTest existing = toeicTestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Test not found with ID: " + id));

        existing.setTitle(updatedTest.getTitle());
        existing.setDescription(updatedTest.getDescription());
        existing.setTotalQuestions(updatedTest.getTotalQuestions());
        existing.setTimeLimit(updatedTest.getTimeLimit());
        existing.setIsFree(updatedTest.getIsFree());
        existing.setIsPublished(updatedTest.getIsPublished());
        existing.setIsPlacementTest(updatedTest.getIsPlacementTest());
        existing.setTestYear(updatedTest.getTestYear());
        existing.setUpdatedAt(LocalDateTime.now());

        return toeicTestRepository.save(existing);
    }

    @Override
    @Transactional
    public void deleteTest(Long id) {
        ToeicTest test = toeicTestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Test not found with ID: " + id));
        toeicTestRepository.delete(test);
    }

    @Override
    public List<LatestTestResponse> getLatestPublishedTests() {
        List<ToeicTest> latestTests = toeicTestRepository.findTop8LatestPublishedTests();

        return latestTests.stream()
                .map(test -> {
                   
                    return new LatestTestResponse(
                            test.getId(),
                            test.getTitle(),
                            test.getDescription(),
                            test.getTimeLimit(),
                            test.getTotalQuestions(),
                            userTestHistoryRepository.countByTestId(test.getId()),
                            commentRepository.countByTestId(test.getId()));
                })
                .collect(Collectors.toList());
    }
}
