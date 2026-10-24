package com.hieu.controller;


import com.hieu.enums.FlightStatus;
import com.hieu.payload.request.FlightRequest;
import com.hieu.payload.response.ApiResponse;
import com.hieu.payload.response.FlightResponse;
import com.hieu.service.FlightService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/flights")
public class FlightController {
    private final FlightService flightService;

    @PostMapping
    public ResponseEntity<FlightResponse> createFlight(
            @Valid @RequestBody FlightRequest flightRequest,
            @RequestHeader("Airline-Id") Long airlineId
    ) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(flightService.createFlight(airlineId, flightRequest));
    }

    @GetMapping("/airline")
    public ResponseEntity<Page<FlightResponse>> getFlightsByAirline(
            @RequestHeader("Airline-Id") Long airlineId,
            @RequestParam(required = false) Long departureAirportId,
            @RequestParam(required = false) Long arrivalAirportId,
            @ParameterObject Pageable pageable
    ) throws Exception {
        return ResponseEntity.ok(flightService.getFlightsByAirline(airlineId, departureAirportId, arrivalAirportId, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlightResponse> getFlightById(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(flightService.getFlightById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FlightResponse> updateFlight(
            @PathVariable Long id,
            @Valid @RequestBody FlightRequest flightRequest
    ) throws Exception {
        return ResponseEntity.ok(flightService.updateFlight(id, flightRequest));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<FlightResponse> changeStatus(
            @PathVariable Long id,
            @RequestBody FlightStatus status
    ) throws Exception {
        return ResponseEntity.ok(flightService.changeStatus(id, status));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteFlight(
            @PathVariable Long id,
            @RequestHeader("Airline-Id") Long airlineId
    ) throws Exception {
        flightService.deleteFlight(airlineId, id);
        return ResponseEntity.ok(new ApiResponse("Flight deleted successfully"));
    }
}
