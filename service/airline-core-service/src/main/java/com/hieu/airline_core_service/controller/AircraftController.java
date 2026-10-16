package com.hieu.airline_core_service.controller;

import com.hieu.airline_core_service.model.Aircraft;
import com.hieu.airline_core_service.service.AircraftService;
import com.hieu.payload.request.AircraftRequest;
import com.hieu.payload.response.AircraftResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/api/aircrafts")
@RequiredArgsConstructor
public class AircraftController {
    private final AircraftService aircraftService;

    @PostMapping("/create")
    public ResponseEntity<AircraftResponse> createAircraft(
            @Valid @RequestBody AircraftRequest aircraftRequest,
            @RequestHeader("X-User-Id") Long userId
    ) throws Exception {
        AircraftResponse aircraft = aircraftService.createAicraft(aircraftRequest, userId);
        return  ResponseEntity.status(HttpStatus.CREATED).body(aircraft);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AircraftResponse> getAircraftById(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(aircraftService.getAircraftById(id));
    }

    @GetMapping
    public  ResponseEntity<List<AircraftResponse>> listAllAircrafts(
            @RequestHeader("X-User-Id") Long userId
    ) throws Exception {
        return ResponseEntity.ok(aircraftService.listAllAicraftByOwner(userId));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<AircraftResponse> updateAircraft(
        @PathVariable Long id,
        @RequestBody AircraftRequest request,
        @RequestHeader("X-User-Id") Long userId
    ) throws Exception {
        return ResponseEntity.ok(aircraftService.updateAicraft(id, request, userId));
    }

    @DeleteMapping("/delete/{id}")
    public  ResponseEntity<AircraftResponse> deleteAircraft(@PathVariable Long id
    ,@RequestHeader("X-User-Id") Long userId) throws Exception {
        aircraftService.deleteAicraft(id, userId);
        return ResponseEntity.noContent().build();
    }

}
