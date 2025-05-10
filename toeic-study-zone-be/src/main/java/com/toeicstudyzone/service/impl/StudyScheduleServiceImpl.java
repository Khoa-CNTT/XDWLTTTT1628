package com.toeicstudyzone.service.impl;

import com.toeicstudyzone.dto.request.StudyScheduleRequest;
import com.toeicstudyzone.dto.response.StudyScheduleResponse;
import com.toeicstudyzone.entity.StudySchedule;
import com.toeicstudyzone.entity.StudyScheduleItem;
import com.toeicstudyzone.repository.StudyScheduleItemRepository;
import com.toeicstudyzone.repository.StudyScheduleRepository;
import com.toeicstudyzone.service.StudyScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudyScheduleServiceImpl implements StudyScheduleService {

    @Autowired
    private StudyScheduleRepository studyScheduleRepository;

    @Autowired
    private StudyScheduleItemRepository studyScheduleItemRepository;

    @Override
    public StudyScheduleResponse createStudySchedule(StudyScheduleRequest request) {
        StudySchedule schedule = new StudySchedule();
        mapToEntity(schedule, request);
        schedule = studyScheduleRepository.save(schedule);

        // Save items if present
        if (request.getItems() != null && !request.getItems().isEmpty()) {
            for (StudyScheduleRequest.StudyScheduleItemRequest itemRequest : request.getItems()) {
                StudyScheduleItem item = new StudyScheduleItem();
                item.setScheduleId(schedule.getId());
                item.setTitle(itemRequest.getTitle());
                item.setCategory(itemRequest.getCategory());
                item.setType(itemRequest.getType());
                studyScheduleItemRepository.save(item);
            }
        }

        return mapToResponse(schedule);
    }

    @Override
    public StudyScheduleResponse updateStudySchedule(Long id, StudyScheduleRequest request) {
        StudySchedule schedule = studyScheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Study schedule not found"));
        mapToEntity(schedule, request);
        schedule = studyScheduleRepository.save(schedule);

        // Update items if present (delete old items and add new ones)
        if (request.getItems() != null) {
            List<StudyScheduleItem> existingItems = studyScheduleItemRepository.findByScheduleId(id);
            studyScheduleItemRepository.deleteAll(existingItems);
            for (StudyScheduleRequest.StudyScheduleItemRequest itemRequest : request.getItems()) {
                StudyScheduleItem item = new StudyScheduleItem();
                item.setScheduleId(schedule.getId());
                item.setTitle(itemRequest.getTitle());
                item.setCategory(itemRequest.getCategory());
                item.setType(itemRequest.getType());
                studyScheduleItemRepository.save(item);
            }
        }

        return mapToResponse(schedule);
    }

    @Override
    public void deleteStudySchedule(Long id) {
        StudySchedule schedule = studyScheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Study schedule not found"));
        studyScheduleRepository.delete(schedule);
    }

    @Override
    public StudyScheduleResponse getStudyScheduleById(Long id) {
        StudySchedule schedule = studyScheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Study schedule not found"));
        return mapToResponse(schedule);
    }

    @Override
    public List<StudyScheduleResponse> getActiveSchedulesByUserId(Long userId) {
        LocalDateTime now = LocalDateTime.now();
        return studyScheduleRepository.findByUserIdAndEndTimeAfter(userId, now)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private void mapToEntity(StudySchedule schedule, StudyScheduleRequest request) {
        schedule.setUserId(request.getUserId());
        schedule.setTitle(request.getTitle());
        schedule.setDescription(request.getDescription());
        schedule.setStartTime(request.getStartTime());
        schedule.setEndTime(request.getEndTime());
        schedule.setIsCompleted(request.getIsCompleted() != null ? request.getIsCompleted() : false);
    }

    private StudyScheduleResponse mapToResponse(StudySchedule schedule) {
        StudyScheduleResponse response = new StudyScheduleResponse();
        response.setId(schedule.getId());
        response.setUserId(schedule.getUserId());
        response.setTitle(schedule.getTitle());
        response.setDescription(schedule.getDescription());
        response.setStartTime(schedule.getStartTime());
        response.setEndTime(schedule.getEndTime());
        response.setIsCompleted(schedule.getIsCompleted());
        response.setCreatedAt(schedule.getCreatedAt());
        response.setUpdatedAt(schedule.getUpdatedAt());
        response.setItems(studyScheduleItemRepository.findByScheduleId(schedule.getId())
                .stream()
                .map(item -> {
                    StudyScheduleResponse.StudyScheduleItemResponse itemResponse = new StudyScheduleResponse.StudyScheduleItemResponse();
                    itemResponse.setId(item.getId());
                    itemResponse.setTitle(item.getTitle());
                    itemResponse.setCategory(item.getCategory());
                    itemResponse.setType(item.getType());
                    itemResponse.setCreatedAt(item.getCreatedAt());
                    itemResponse.setUpdatedAt(item.getUpdatedAt());
                    return itemResponse;
                }).collect(Collectors.toList()));
        return response;
    }
}