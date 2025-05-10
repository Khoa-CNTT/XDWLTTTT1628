package com.toeicstudyzone.controller;

import com.toeicstudyzone.dto.response.TestResultResponse;
import com.toeicstudyzone.dto.request.TestReviewRequest;
import com.toeicstudyzone.service.UserTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/tests")
public class UserTestController {
    @Autowired
    private UserTestService userTestService;

    @GetMapping("/history/{userId}")
    public List<TestResultResponse> getHistory(@PathVariable Long userId) {
        return userTestService.getUserTestHistory(userId);
    }

    @PostMapping("/review")
    public TestResultResponse reviewTest(@RequestBody TestReviewRequest request) {
        return userTestService.reviewTest(request);
    }
}