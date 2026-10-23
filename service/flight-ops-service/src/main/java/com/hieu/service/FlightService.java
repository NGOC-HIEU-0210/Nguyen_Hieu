package com.hieu.service;

import com.hieu.enums.FlightStatus;
import com.hieu.payload.request.FlightRequest;
import com.hieu.payload.response.FlightResponse;
import org.springframework.data.domain.Page;

import java.awt.print.Pageable;
import java.util.List;

public interface FlightService {
    FlightResponse createFlight(Long airlineId, FlightRequest flightRequest);
    Page<FlightResponse> getFlightsByAirline(Long airlineId,
                                             Long  departureAirportId,
                                             Long ArrivalAirportId,
                                             Pageable pageable);
    FlightResponse getFlightById(Long id);
    FlightResponse updateFlight(Long id, FlightRequest flightRequest);
    FlightResponse changeStatus(Long id, FlightStatus status);
    void deleteFlight(Long id);

}
