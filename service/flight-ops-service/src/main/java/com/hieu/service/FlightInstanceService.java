package com.hieu.service;

import com.hieu.model.FlightInstance;
import com.hieu.payload.request.FlightInstanceRequest;
import com.hieu.payload.response.FlightInstanceResponce;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface FlightInstanceService {
    FlightInstanceResponce createFlightInstance(
        Long userId,
        FlightInstanceRequest request
    ) throws Exception;

    FlightInstanceResponce getFlightInstanceById(Long id) throws Exception;

    Page<FlightInstanceResponce> getByAirlineId(Long airlineId, Long departureAirportId, Long arrivalAirportId, Long flightId, LocalDate onDate, Pageable pageable);

    FlightInstanceResponce updateFlightInstance(Long id, FlightInstanceRequest request) throws Exception;
    void deleteFlightInstance(Long id) throws Exception;
}
