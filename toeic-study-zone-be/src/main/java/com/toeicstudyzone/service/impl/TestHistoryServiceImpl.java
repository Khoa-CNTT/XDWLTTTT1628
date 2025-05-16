package com.toeicstudyzone.service.impl;

import com.toeicstudyzone.dto.request.UserTestHistoryDTO;
import com.toeicstudyzone.entity.UserTestHistory;
import com.toeicstudyzone.repository.UserTestHistoryRepository;
import com.toeicstudyzone.service.TestHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TestHistoryServiceImpl implements TestHistoryService {

    @Autowired
    private UserTestHistoryRepository userTestHistoryRepository;

    @Override
    public UserTestHistory saveHistory(UserTestHistory history) {
        return userTestHistoryRepository.save(history);
    }

    @Override
    public UserTestHistory getHistoryById(Long id) {
        return userTestHistoryRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserTestHistoryDTO> getHistoryByUserId(Long userId) {
        List<UserTestHistory> history = userTestHistoryRepository.findByUserId(userId);
        return history.stream().map(h -> new UserTestHistoryDTO(
            h.getId(),
            h.getUser().getId(),
            h.getTest().getId(),
            h.getTest().getTitle(),
            h.getTest().getTestYear().getYear(),
            h.getStartTime(),
            h.getEndTime(),
            h.getTotalScore(),
            h.getListeningScore(),
            h.getReadingScore(),
            h.getCorrectAnswers(),
            h.getCompleted()
        )).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserTestHistoryDTO> getHistoryByTestId(Long testId) {
        List<UserTestHistory> history = userTestHistoryRepository.findByTestId(testId);
        return history.stream().map(h -> new UserTestHistoryDTO(
            h.getId(),
            h.getUser().getId(),
            h.getTest().getId(),
            h.getTest().getTitle(),
            h.getTest().getTestYear().getYear(),
            h.getStartTime(),
            h.getEndTime(),
            h.getTotalScore(),
            h.getListeningScore(),
            h.getReadingScore(),
            h.getCorrectAnswers(),
            h.getCompleted()
        )).collect(Collectors.toList());
    }
}