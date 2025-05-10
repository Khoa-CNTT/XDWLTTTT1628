package com.toeicstudyzone.service;

import com.toeicstudyzone.dto.request.StudyScheduleRequest;
import com.toeicstudyzone.dto.response.StudyScheduleResponse;

import java.util.List;

public interface StudyScheduleService {
    StudyScheduleResponse createStudySchedule(StudyScheduleRequest request);
    StudyScheduleResponse updateStudySchedule(Long id, StudyScheduleRequest request);
    void deleteStudySchedule(Long id);
    StudyScheduleResponse getStudyScheduleById(Long id);
    List<StudyScheduleResponse> getActiveSchedulesByUserId(Long userId);
}