package com.hieu.service;

import com.hieu.enums.FlightStatus;
import com.hieu.payload.request.FlightRequest;
import com.hieu.payload.response.FlightResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface FlightService {
    FlightResponse createFlight(Long airlineId, FlightRequest flightRequest) throws Exception;
    Page<FlightResponse> getFlightsByAirline(Long airlineId,
                                             Long  departureAirportId,
                                             Long ArrivalAirportId,
                                             Pageable pageable);
    FlightResponse getFlightById(Long id) throws Exception;
    FlightResponse updateFlight(Long id, FlightRequest flightRequest) throws Exception;
    FlightResponse changeStatus(Long id, FlightStatus status) throws Exception;
    void deleteFlight(Long airlineId ,Long id) throws Exception;

}
