package com.hieu.service.impl;

import com.hieu.mapper.FlightMapper;
import com.hieu.model.Flight;
import com.hieu.model.FlightInstance;
import com.hieu.payload.request.FlightInstanceRequest;
import com.hieu.payload.response.AircraftResponse;
import com.hieu.payload.response.AirlineResponse;
import com.hieu.payload.response.AirportResponse;
import com.hieu.payload.response.FlightInstanceResponce;
import com.hieu.mapper.FlightInstanceMapper;
import com.hieu.repository.FlightInstanceRepository;
import com.hieu.repository.FlightRepository;
import com.hieu.service.FlightInstanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightInstanceServiceImpl implements FlightInstanceService {

    private final FlightInstanceRepository flightInstanceRepository;
    private final FlightRepository flightRepository;

    @Override
    public FlightInstanceResponce createFlightInstance(Long userId, FlightInstanceRequest request) throws Exception {
        if (request == null) {
            throw new Exception("Flight instance request is required");
        }
        if (request.getFlightId() == null) {
            throw new Exception("Flight ID is required");
        }
        if (request.getDepartureDateTime() == null) {
            throw new Exception("Departure date-time is required");
        }
        if (request.getArrivalDateTime() == null) {
            throw new Exception("Arrival date-time is required");
        }
        if (request.getTotalSeats() == null || request.getTotalSeats() <= 0) {
            throw new Exception("Total seats is required");
        }
        if (request.getDepartureDateTime().isAfter(request.getArrivalDateTime())) {
            throw new Exception("Arrival date-time must be after departure date-time");
        }

        Flight flight = flightRepository.findById(request.getFlightId()).orElseThrow(
                () -> new Exception("Flight not found with id " + request.getFlightId())
        );

        AircraftResponse aircraft = AircraftResponse.builder()
                .id(1L)
                .totalSeats(90)
                .build();

        FlightInstance flightInstance = FlightInstanceMapper.toEntity(request, flight);
        flightInstance.setTotalSeats(aircraft.getTotalSeats());
        flightInstance.setAvailableSeats(aircraft.getTotalSeats());


        FlightInstance savedFlightInstance = flightInstanceRepository.save(flightInstance);

        return convertToFlightInstanceResponse(savedFlightInstance);
    }

    @Override
    public FlightInstanceResponce getFlightInstanceById(Long id) throws Exception {
        FlightInstance flightInstance =  flightInstanceRepository.findById(id).orElseThrow(
                () -> new Exception("Flight instance not found with id " + id)
        );
        return convertToFlightInstanceResponse(flightInstance);
    }

    @Override
    public Page<FlightInstanceResponce> getByAirlineId(Long airlineId, Long departureAirportId, Long arrivalAirportId, Long flightId, LocalDate onDate, Pageable pageable) {
        LocalDateTime dayStart = onDate != null ? onDate.atStartOfDay() : null;

        LocalDateTime dayEnd = onDate != null ? onDate.plusDays(1).atStartOfDay() : null;


        return flightInstanceRepository.findByAirlineId(airlineId, departureAirportId, arrivalAirportId, flightId, dayStart, dayEnd, pageable)
                .map(this:: convertToFlightInstanceResponse);

    }

    @Override
    public FlightInstanceResponce updateFlightInstance(Long id, FlightInstanceRequest request) throws Exception {
        FlightInstance flightInstance = flightInstanceRepository.findById(id).orElseThrow(
                () -> new Exception("Flight instance not found with id " + id)
        );

        if (request.getFlightId() != null) {
            Flight flight = flightRepository.findById(request.getFlightId()).orElseThrow(
                    () -> new Exception("Flight not found with id " + request.getFlightId())
            );
            flightInstance.setFlight(flight);
        }

        FlightInstanceMapper.updateEntity(request, flightInstance);

//        FlightInstance updatedFlightInstance = flightInstanceRepository.save(flightInstance);
//
//        return FlightInstanceMapper.toResponse(updatedFlightInstance);

        return convertToFlightInstanceResponse(flightInstanceRepository.save(flightInstance));
    }

    @Override
    public void deleteFlightInstance(Long id) throws Exception {
        FlightInstance flightInstance = flightInstanceRepository.findById(id).orElseThrow(
                () -> new Exception("Flight instance not found with id " + id)
        );
        flightInstanceRepository.delete(flightInstance);
    }

    private FlightInstanceResponce convertToFlightInstanceResponse(FlightInstance flightInstance) {
        AirlineResponse airline = AirlineResponse.builder().id(flightInstance.getAirlineId()).build();
        AirportResponse departureAirport = AirportResponse.builder().id(flightInstance.getDepartureAirportId()).build();
        AirportResponse arrivalAirport = AirportResponse.builder().id(flightInstance.getArrivalAirportId()).build();
        AircraftResponse aircraftResponse = AircraftResponse.builder().id(flightInstance.getFlight().getAircraftId()).build();
        return FlightInstanceMapper.toResponse(
                flightInstance,
                aircraftResponse,
                airline,
                departureAirport,
                arrivalAirport
        );
    }
}
