package com.toeicstudyzone.service;

  import com.toeicstudyzone.entity.UserTestHistory;

  public interface TestHistoryService {

      UserTestHistory saveHistory(UserTestHistory history);

      UserTestHistory getHistoryById(Long id);
  }