package com.hieu.controller;


import com.hieu.payload.request.FlightInstanceRequest;
import com.hieu.payload.response.ApiResponse;
import com.hieu.payload.response.FlightInstanceResponce;
import com.hieu.service.FlightInstanceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/flight-instances")
public class FlightInstanceController {
    private final FlightInstanceService flightInstanceService;

    @PostMapping
    public ResponseEntity<FlightInstanceResponce> createFlightInstance(
            @RequestHeader("Airline-Id") Long airlineId,
            @Valid @RequestBody FlightInstanceRequest flightInstanceRequest
    ) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(flightInstanceService.createFlightInstance(airlineId, flightInstanceRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlightInstanceResponce> getFlightInstanceById(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(flightInstanceService.getFlightInstanceById(id));
    }

    @GetMapping("/airline")
    public ResponseEntity<Page<FlightInstanceResponce>> getFlightInstancesByAirline(
            @RequestHeader("Airline-Id") Long airlineId,
            @RequestParam(required = false) Long departureAirportId,
            @RequestParam(required = false) Long arrivalAirportId,
            @RequestParam(required = false) Long flightId,
            @RequestParam(required = false) LocalDate onDate,
            @ParameterObject Pageable pageable
    ) throws Exception {
        return ResponseEntity.ok(
                flightInstanceService.getByAirlineId(airlineId, departureAirportId, arrivalAirportId, flightId, onDate, pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FlightInstanceResponce> updateFlightInstance(
            @PathVariable Long id,
            @Valid @RequestBody FlightInstanceRequest flightInstanceRequest
    ) throws Exception {
        return ResponseEntity.ok(flightInstanceService.updateFlightInstance(id, flightInstanceRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteFlightInstance(@PathVariable Long id) throws Exception {
        flightInstanceService.deleteFlightInstance(id);
        return ResponseEntity.ok(new ApiResponse("Flight instance deleted successfully"));
    }
}
