package com.toeicstudyzone.service;

import com.toeicstudyzone.dto.request.UserTestHistoryDTO; 
import com.toeicstudyzone.entity.UserTestHistory;

import java.util.List;

public interface TestHistoryService {
    UserTestHistory saveHistory(UserTestHistory history);
    UserTestHistory getHistoryById(Long id);
    List<UserTestHistoryDTO> getHistoryByUserId(Long userId); // Thêm phương thức mới
    List<UserTestHistoryDTO> getHistoryByTestId(Long testId); // Thêm phương thức mới
}