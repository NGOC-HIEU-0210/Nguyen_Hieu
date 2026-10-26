package com.hieu.service.impl;

import com.hieu.mapper.FlightScheduleMapper;
import com.hieu.model.Flight;
import com.hieu.model.FlightSchedule;
import com.hieu.payload.request.FlightScheduleRequest;
import com.hieu.payload.response.AirportResponse;
import com.hieu.payload.response.FlightScheduleResponse;
import com.hieu.repository.FlightRepository;
import com.hieu.repository.FlightScheduleRepository;
import com.hieu.service.FlightScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightScheduleServiceImpl implements FlightScheduleService {

    private final FlightScheduleRepository flightScheduleRepository;
    private final FlightRepository flightRepository;

    @Override
    public FlightScheduleResponse createFlightSchedule(Long airlineId, FlightScheduleRequest request) throws Exception {
        if (request == null) {
            throw new Exception("Flight schedule request is required");
        }
        if (request.getFlightId() == null) {
            throw new Exception("Flight ID is required");
        }
        if (request.getDepartureTime() == null || request.getArrivalTime() == null) {
            throw new Exception("Departure and arrival time are required");
        }
        if (request.getStartDate() == null || request.getEndDate() == null) {
            throw new Exception("Start and end date are required");
        }
        if (request.getEndDate().isBefore(request.getStartDate())) {
            throw new Exception("end date before start date");
        }

        Flight flight = flightRepository.findById(request.getFlightId()).orElseThrow(
                () -> new Exception("flight not found with given id")
        );

        FlightSchedule flightSchedule = FlightScheduleMapper.toEntity(request, flight);
        FlightSchedule savedFlightSchedule = flightScheduleRepository.save(flightSchedule);

        return convertToFlightScheduleResponse(savedFlightSchedule, flight);
    }

    @Override
    public FlightScheduleResponse getFlightScheduleById(Long id) {
        FlightSchedule flightSchedule = flightScheduleRepository.findById(id).orElseThrow(
                () -> new RuntimeException("flight schedule not found with id " + id)
        );
        return convertToFlightScheduleResponse(flightSchedule, flightSchedule.getFlight());
    }

    @Override
    public List<FlightScheduleResponse> getFlightScheduleByAirline(Long airlineId) {
        List<FlightSchedule> flightSchedules = flightScheduleRepository.findByFlightAirlineId(airlineId);

        return flightSchedules.stream()
                .map(flightSchedule -> convertToFlightScheduleResponse(flightSchedule, flightSchedule.getFlight()))
                .toList();
    }

    @Override
    public FlightScheduleResponse updateFlightSchedule(Long id, FlightScheduleRequest flightScheduleRequest) {
        if (flightScheduleRequest == null) {
            throw new RuntimeException("Flight schedule request is required");
        }

        FlightSchedule flightSchedule = flightScheduleRepository.findById(id).orElseThrow(
                () -> new RuntimeException("flight schedule not found with id " + id)
        );

        if (flightScheduleRequest.getStartDate() == null || flightScheduleRequest.getEndDate() == null) {
            throw new RuntimeException("Start and end date are required");
        }
        if (flightScheduleRequest.getEndDate().isBefore(flightScheduleRequest.getStartDate())) {
            throw new RuntimeException("end date before start date");
        }

        Flight flight = null;
        if (flightScheduleRequest.getFlightId() != null) {
            flight = flightRepository.findById(flightScheduleRequest.getFlightId()).orElseThrow(
                    () -> new RuntimeException("flight not found with given id")
            );
        }

        FlightScheduleMapper.updateEntity(flightScheduleRequest, flightSchedule, flight);
        FlightSchedule updatedFlightSchedule = flightScheduleRepository.save(flightSchedule);

        return convertToFlightScheduleResponse(updatedFlightSchedule, flight != null ? flight : flightSchedule.getFlight());
    }

    @Override
    public void deleteFlightSchedule(Long id) {
        FlightSchedule flightSchedule = flightScheduleRepository.findById(id).orElseThrow(
                () -> new RuntimeException("flight schedule not found with id " + id)
        );
        flightScheduleRepository.delete(flightSchedule);
    }

    private FlightScheduleResponse convertToFlightScheduleResponse(FlightSchedule flightSchedule, Flight flight) {
        AirportResponse departureAirport = AirportResponse.builder()
                .id(flightSchedule.getDepartureAirportId())
                .build();
        AirportResponse arrivalAirport = AirportResponse.builder()
                .id(flightSchedule.getArrivalAirportId())
                .build();

        return FlightScheduleMapper.toResponse(flightSchedule, flight, departureAirport, arrivalAirport);
    }
}
