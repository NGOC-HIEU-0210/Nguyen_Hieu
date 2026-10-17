package com.hieu.airline_core_service.controller;

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
@RequestMapping("/api/aircrafts")
@RequiredArgsConstructor
public class AircraftController {
    private final AircraftService aircraftService;

    /** POST /api/aircrafts/create?airlineId=5 */
    @PostMapping("/create")
    public ResponseEntity<AircraftResponse> createAircraft(
            @Valid @RequestBody AircraftRequest aircraftRequest,
            @RequestParam Long airlineId,
            @RequestHeader("X-User-Id") Long userId
    ) throws Exception {
        AircraftResponse aircraft = aircraftService.createAicraft(aircraftRequest, airlineId, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(aircraft);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AircraftResponse> getAircraftById(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(aircraftService.getAircraftById(id));
    }

    /** GET /api/aircrafts            -> máy bay của mọi airline của owner
     *  GET /api/aircrafts?airlineId=5 -> chỉ máy bay của airline 5 */
    @GetMapping
    public ResponseEntity<List<AircraftResponse>> listAllAircrafts(
            @RequestParam(required = false) Long airlineId,
            @RequestHeader("X-User-Id") Long userId
    ) throws Exception {
        return ResponseEntity.ok(aircraftService.listAllAicraftByOwner(userId, airlineId));
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
    public ResponseEntity<Void> deleteAircraft(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long userId
    ) throws Exception {
        aircraftService.deleteAicraft(id, userId);
        return ResponseEntity.noContent().build();
    }
}