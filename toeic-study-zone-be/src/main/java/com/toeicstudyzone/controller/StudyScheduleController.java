package com.toeicstudyzone.controller;

import com.toeicstudyzone.dto.request.StudyScheduleRequest;
import com.toeicstudyzone.dto.response.StudyScheduleResponse;
import com.toeicstudyzone.service.StudyScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/study-schedules")
public class StudyScheduleController {

    @Autowired
    private StudyScheduleService studyScheduleService;

    @PostMapping
    public ResponseEntity<StudyScheduleResponse> createStudySchedule(@RequestBody StudyScheduleRequest request) {
        return ResponseEntity.ok(studyScheduleService.createStudySchedule(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudyScheduleResponse> updateStudySchedule(@PathVariable Long id, @RequestBody StudyScheduleRequest request) {
        return ResponseEntity.ok(studyScheduleService.updateStudySchedule(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudySchedule(@PathVariable Long id) {
        studyScheduleService.deleteStudySchedule(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudyScheduleResponse> getStudyScheduleById(@PathVariable Long id) {
        return ResponseEntity.ok(studyScheduleService.getStudyScheduleById(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<StudyScheduleResponse>> getActiveSchedulesByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(studyScheduleService.getActiveSchedulesByUserId(userId));
    }
}