package com.toeicstudyzone.service.impl;

import com.toeicstudyzone.entity.*;
import com.toeicstudyzone.repository.*;
import com.toeicstudyzone.service.ToeicTestService;
import com.toeicstudyzone.dto.response.TestSectionResponse;
import com.toeicstudyzone.dto.response.PassageResponse;
import com.toeicstudyzone.dto.response.ParagraphResponse;
import com.toeicstudyzone.dto.response.QuestionResponse;
import com.toeicstudyzone.dto.response.AnswerOptionResponse;
import com.toeicstudyzone.dto.response.ToeicTestResponse;
import com.toeicstudyzone.dto.response.TestResultResponse;
import com.toeicstudyzone.dto.response.QuestionResultDTO;
import com.toeicstudyzone.dto.request.TestSubmissionRequest;
// import com.toeicstudyzone.dto.request.UserAnswerDTO;
// import com.toeicstudyzone.enums.QuestionType; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ToeicTestServiceImpl implements ToeicTestService {
    @Autowired
    private ToeicTestRepository toeicTestRepository;

    // @Autowired
    // private TestSectionRepository testSectionRepository;

    // @Autowired
    // private PassageRepository passageRepository;

    // @Autowired
    // private ParagraphRepository paragraphRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerOptionRepository answerOptionRepository;

    @Autowired
    private UserTestHistoryRepository userTestHistoryRepository;

    @Autowired
    private UserQuestionResponseRepository userQuestionResponseRepository;

    @Override
    public ToeicTestResponse getTest(Long testId) {
        ToeicTest test = toeicTestRepository.findById(testId)
                .orElseThrow(() -> new RuntimeException("Test not found"));
        ToeicTestResponse response = new ToeicTestResponse();
        response.setId(test.getId());
        response.setTitle(test.getTitle());
        response.setTotalQuestions(test.getTotalQuestions());
        response.setTimeLimit(test.getTimeLimit());
        response.setSections(test.getSections().stream().map(section -> {
            TestSectionResponse secResp = new TestSectionResponse();
            secResp.setSectionType(section.getSectionType());
            secResp.setSectionPart(section.getSectionPart());
            secResp.setTitle(section.getTitle());
            secResp.setInstructions(section.getInstructions());
            secResp.setPassages(section.getPassages().stream().map(passage -> {
                PassageResponse passResp = new PassageResponse();
                passResp.setTitle(passage.getTitle());
                passResp.setPassageType(passage.getPassageType());
                passResp.setPassageOrder(passage.getPassageOrder());
                passResp.setParagraphs(passage.getParagraphs().stream().map(paragraph -> {
                    ParagraphResponse parResp = new ParagraphResponse();
                    parResp.setContent(paragraph.getContent());
                    parResp.setParagraphTitle(paragraph.getParagraphTitle());
                    parResp.setParagraphOrder(paragraph.getParagraphOrder());
                    if (paragraph.getImage() != null) {
                        parResp.setImageUrl(paragraph.getImage().getImageUrl());
                        parResp.setImageOrder(paragraph.getImage().getImageOrder());
                    }
                    return parResp;
                }).collect(Collectors.toList()));
                return passResp;
            }).collect(Collectors.toList()));
            secResp.setQuestions(section.getQuestions().stream().map(question -> {
                QuestionResponse qResp = new QuestionResponse();
                qResp.setId(question.getId());
                qResp.setQuestionNumber(question.getQuestionNumber());
                qResp.setContent(question.getContent());
                qResp.setQuestionType(question.getQuestionType());
                qResp.setAudioUrl(question.getAudioUrl());
                qResp.setImageUrl(question.getImageUrl());
                qResp.setAnswerOptions(question.getAnswerOptions().stream().map(option -> {
                    AnswerOptionResponse aResp = new AnswerOptionResponse();
                    aResp.setId(option.getId());
                    aResp.setContent(option.getContent());
                    aResp.setOptionLabel(option.getOptionLabel());
                    return aResp;
                }).collect(Collectors.toList()));
                return qResp;
            }).collect(Collectors.toList()));
            return secResp;
        }).collect(Collectors.toList()));
        return response;
    }

    @Override
    public TestResultResponse submitTest(Long userId, TestSubmissionRequest request) {
        ToeicTest test = toeicTestRepository.findById(request.getToeicTestId())
                .orElseThrow(() -> new RuntimeException("Test not found"));
        UserTestHistory history = new UserTestHistory();
        history.setUser(new User());
        history.getUser().setId(userId);
        history.setTest(test);
        history.setStartTime(LocalDateTime.now());
        history.setEndTime(LocalDateTime.now());

        int listeningScore = 0;
        int readingScore = 0;
        int correctCount = 0;

        List<QuestionResultDTO> results = request.getAnswers().stream().map(answer -> {
            Question question = questionRepository.findById(answer.getQuestionId())
                    .orElseThrow(() -> new RuntimeException("Question not found"));
            AnswerOption correctOption = question.getAnswerOptions().stream()
                    .filter(AnswerOption::isCorrect).findFirst().orElse(null);
            boolean isCorrect = answer.getSelectedOptionId() != null && correctOption != null && 
                               answer.getSelectedOptionId().equals(correctOption.getId());
            UserQuestionResponse userResponse = new UserQuestionResponse();
            userResponse.setUserTestHistory(history);
            userResponse.setQuestion(question);
            AnswerOption selectedAnswer = answer.getSelectedOptionId() != null ?
                    answerOptionRepository.findById(answer.getSelectedOptionId())
                    .orElseThrow(() -> new RuntimeException("Answer option not found")) : null;
            userResponse.setSelectedAnswer(selectedAnswer);
            userResponse.setIsCorrect(isCorrect);
            userResponse.setTimeSpent(answer.getTimeSpent());
            userQuestionResponseRepository.save(userResponse);

            QuestionResultDTO result = new QuestionResultDTO();
            result.setQuestionId(question.getId());
            result.setCorrectAnswerId(correctOption != null ? correctOption.getId() : null);
            result.setSelectedAnswerId(answer.getSelectedOptionId());
            result.setCorrect(isCorrect);
            return result;
        }).collect(Collectors.toList());

        // Tính điểm sau khi stream hoàn tất
        for (QuestionResultDTO result : results) {
            if (result.isCorrect()) {
                Question question = questionRepository.findById(result.getQuestionId())
                        .orElseThrow(() -> new RuntimeException("Question not found"));
                if (question.getTestSection().getSectionType() == TestSection.SectionType.LISTENING) {
                    listeningScore += 5;
                } else {
                    readingScore += 5;
                }
                correctCount++;
            }
        }

        history.setTotalScore(listeningScore + readingScore);
        history.setListeningScore(listeningScore);
        history.setReadingScore(readingScore);
        history.setCorrectAnswers(correctCount);
        history.setCompleted(true);
        userTestHistoryRepository.save(history);

        TestResultResponse response = new TestResultResponse();
        response.setTotalScore(history.getTotalScore());
        response.setListeningScore(listeningScore);
        response.setReadingScore(readingScore);
        response.setCorrectCount(correctCount);
        response.setPercentage((double) correctCount / test.getTotalQuestions() * 100);
        response.setResults(results);
        return response;
    }
}