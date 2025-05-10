package com.toeicstudyzone.service;

import com.toeicstudyzone.dto.request.AdvertisementRequest;
import com.toeicstudyzone.dto.response.AdvertisementResponse;

import java.util.List;

public interface AdvertisementService {
    AdvertisementResponse createAdvertisement(AdvertisementRequest request);
    AdvertisementResponse updateAdvertisement(Long id, AdvertisementRequest request);
    void deleteAdvertisement(Long id);
    AdvertisementResponse getAdvertisementById(Long id);
    List<AdvertisementResponse> getAllActiveAdvertisements();
}