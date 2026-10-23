package com.hieu.service.impl;

import com.hieu.enums.FlightStatus;
import com.hieu.payload.request.FlightRequest;
import com.hieu.payload.response.FlightResponse;
import com.hieu.repository.FlightRepository;
import com.hieu.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.awt.print.Pageable;

@Service
@RequiredArgsConstructor
public class FlightServiceImpl implements FlightService {
    private  final FlightRepository flightRepository;

    @Override
    public FlightResponse createFlight(Long airlineId, FlightRequest flightRequest) {
        return null;
    }

    @Override
    public Page<FlightResponse> getFlightsByAirline(Long airlineId, Long departureAirportId, Long ArrivalAirportId, Pageable pageable) {
        return null;
    }

    @Override
    public FlightResponse getFlightById(Long id) {
        return null;
    }

    @Override
    public FlightResponse updateFlight(Long id, FlightRequest flightRequest) {
        return null;
    }

    @Override
    public FlightResponse changeStatus(Long id, FlightStatus status) {
        return null;
    }

    @Override
    public void deleteFlight(Long id) {

    }
}
