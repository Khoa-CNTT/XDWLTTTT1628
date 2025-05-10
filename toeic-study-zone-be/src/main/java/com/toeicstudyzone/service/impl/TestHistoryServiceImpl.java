package com.toeicstudyzone.service.impl;

  import com.toeicstudyzone.entity.UserTestHistory;
  import com.toeicstudyzone.repository.UserTestHistoryRepository;
  import com.toeicstudyzone.service.TestHistoryService;
  import org.springframework.beans.factory.annotation.Autowired;
  import org.springframework.stereotype.Service;

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
  }