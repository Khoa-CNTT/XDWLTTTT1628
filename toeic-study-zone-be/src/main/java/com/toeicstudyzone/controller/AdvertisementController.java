package com.toeicstudyzone.controller;

import com.toeicstudyzone.dto.request.AdvertisementRequest;
import com.toeicstudyzone.dto.response.AdvertisementResponse;
import com.toeicstudyzone.service.AdvertisementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/advertisements")
public class AdvertisementController {

    @Autowired
    private AdvertisementService advertisementService;

    @PostMapping
    public ResponseEntity<AdvertisementResponse> createAdvertisement(@RequestBody AdvertisementRequest request) {
        return ResponseEntity.ok(advertisementService.createAdvertisement(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdvertisementResponse> updateAdvertisement(@PathVariable Long id, @RequestBody AdvertisementRequest request) {
        return ResponseEntity.ok(advertisementService.updateAdvertisement(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdvertisement(@PathVariable Long id) {
        advertisementService.deleteAdvertisement(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdvertisementResponse> getAdvertisementById(@PathVariable Long id) {
        return ResponseEntity.ok(advertisementService.getAdvertisementById(id));
    }

    @GetMapping("/active")
    public ResponseEntity<List<AdvertisementResponse>> getAllActiveAdvertisements() {
        return ResponseEntity.ok(advertisementService.getAllActiveAdvertisements());
    }
    @GetMapping
public ResponseEntity<List<AdvertisementResponse>> getAllAdvertisements() {
    return ResponseEntity.ok(advertisementService.getAllAdvertisements());
}

}