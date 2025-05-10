package com.toeicstudyzone.service.impl;

import com.toeicstudyzone.dto.request.AdvertisementRequest;
import com.toeicstudyzone.dto.response.AdvertisementResponse;
import com.toeicstudyzone.entity.Advertisement;
import com.toeicstudyzone.repository.AdvertisementRepository;
import com.toeicstudyzone.service.AdvertisementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdvertisementServiceImpl implements AdvertisementService {

    @Autowired
    private AdvertisementRepository advertisementRepository;

    @Override
    public AdvertisementResponse createAdvertisement(AdvertisementRequest request) {
        Advertisement advertisement = new Advertisement();
        mapToEntity(advertisement, request);
        advertisement = advertisementRepository.save(advertisement);
        return mapToResponse(advertisement);
    }

    @Override
    public AdvertisementResponse updateAdvertisement(Long id, AdvertisementRequest request) {
        Advertisement advertisement = advertisementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Advertisement not found"));
        mapToEntity(advertisement, request);
        advertisement = advertisementRepository.save(advertisement);
        return mapToResponse(advertisement);
    }

    @Override
    public void deleteAdvertisement(Long id) {
        Advertisement advertisement = advertisementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Advertisement not found"));
        advertisementRepository.delete(advertisement);
    }

    @Override
    public AdvertisementResponse getAdvertisementById(Long id) {
        Advertisement advertisement = advertisementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Advertisement not found"));
        return mapToResponse(advertisement);
    }

    @Override
    public List<AdvertisementResponse> getAllActiveAdvertisements() {
        LocalDate today = LocalDate.now();
        return advertisementRepository.findByIsActiveTrueAndStartDateLessThanEqualAndEndDateGreaterThanEqual(today, today)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private void mapToEntity(Advertisement advertisement, AdvertisementRequest request) {
        advertisement.setTitle(request.getTitle());
        advertisement.setDescription(request.getDescription());
        advertisement.setImageUrl(request.getImageUrl());
        advertisement.setLinkUrl(request.getLinkUrl());
        advertisement.setStartDate(request.getStartDate());
        advertisement.setEndDate(request.getEndDate());
        advertisement.setIsActive(request.getIsActive() != null ? request.getIsActive() : true); 
    }

    private AdvertisementResponse mapToResponse(Advertisement advertisement) {
        AdvertisementResponse response = new AdvertisementResponse();
        response.setId(advertisement.getId());
        response.setTitle(advertisement.getTitle());
        response.setDescription(advertisement.getDescription());
        response.setImageUrl(advertisement.getImageUrl());
        response.setLinkUrl(advertisement.getLinkUrl());
        response.setStartDate(advertisement.getStartDate());
        response.setEndDate(advertisement.getEndDate());
        response.setIsActive(advertisement.getIsActive());
        response.setCreatedAt(advertisement.getCreatedAt());
        response.setUpdatedAt(advertisement.getUpdatedAt());
        return response;
    }
}