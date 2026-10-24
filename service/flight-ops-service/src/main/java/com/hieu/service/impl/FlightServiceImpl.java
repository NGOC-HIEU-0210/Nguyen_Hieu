package com.hieu.service.impl;

import com.hieu.enums.FlightStatus;
import com.hieu.mapper.FlightMapper;
import com.hieu.model.Flight;
import com.hieu.payload.request.FlightRequest;
import com.hieu.payload.response.AircraftResponse;
import com.hieu.payload.response.AirlineResponse;
import com.hieu.payload.response.AirportResponse;
import com.hieu.payload.response.FlightResponse;
import com.hieu.repository.FlightRepository;
import com.hieu.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;



@Service
@RequiredArgsConstructor
public class FlightServiceImpl implements FlightService {
    private final FlightRepository flightRepository;

    @Override
    public FlightResponse createFlight(Long airlineId, FlightRequest flightRequest) throws Exception {
        if (flightRepository.existsByFlightNumber(flightRequest.getFlightNumber())) {
            throw new Exception("Flight with already exist");
        }

        Flight flight = FlightMapper.toEntity(flightRequest);
        flight.setAirlineId(airlineId);
        Flight saved = flightRepository.save(flight);


        return convertToFlightResponse(saved);
    }

    @Override
    public Page<FlightResponse> getFlightsByAirline(Long airlineId, Long departureAirportId, Long arrivalAirportId, Pageable pageable) {

        return flightRepository.findByAirlineId(
                airlineId,
                departureAirportId,
                arrivalAirportId,
                pageable
        ).map(this::convertToFlightResponse);
    }

    @Override
    public FlightResponse getFlightById(Long id) throws Exception {
        Flight flight = flightRepository.findById(id).orElseThrow(
                () -> new Exception("flight not found with id")
        );
        return convertToFlightResponse(flight);
    }



    @Override
    public FlightResponse updateFlight(Long id, FlightRequest flightRequest) throws Exception {
        Flight existing = flightRepository.findById(id).orElseThrow(
                () -> new Exception("flight not found with id")
        );
        if (flightRequest.getFlightNumber() != null
        && flightRepository.existsByFlightNumberAndIdNot(flightRequest.getFlightNumber(), id)) {
            throw new Exception("flight with already exist");
        }
        FlightMapper.updateEntity(flightRequest, existing);
        Flight updated = flightRepository.save(existing);
        return convertToFlightResponse(updated);
    }

    @Override
    public FlightResponse changeStatus(Long id, FlightStatus status) throws Exception {
        Flight existing = flightRepository.findById(id).orElseThrow(
                () -> new Exception("flight not found with id")
        );
        existing.setStatus(status);
        Flight updated = flightRepository.save(existing);
        return convertToFlightResponse(updated);
    }

    @Override
    public void deleteFlight(Long airlineId ,Long id) throws Exception {
        Flight existing = flightRepository.findByAirlineIdAndId(airlineId,id).orElseThrow(
                () -> new Exception("flight not found with id")
        );
        flightRepository.delete(existing);
    }

    public FlightResponse convertToFlightResponse(Flight flight) {
        AircraftResponse aircraftResponse = AircraftResponse.builder().id(flight.getAircraftId())
                .build();
        AirlineResponse airlineResponse = AirlineResponse.builder().id(flight.getAirlineId()).build();
        AirportResponse departureAirport = AirportResponse.builder()
                .id(flight.getDepartureAirportId())
                .build();
        AirportResponse arrivalAirport = AirportResponse.builder()
                .id(flight.getArrivalAirportId())
                .build();
        return FlightMapper.toResponse(flight,
                airlineResponse,
                aircraftResponse,
                departureAirport,
                arrivalAirport);
    }
}
