package com.hieu.mapper;

import com.hieu.model.Flight;
import com.hieu.model.FlightSchedule;
import com.hieu.payload.request.FlightScheduleRequest;
import com.hieu.payload.response.AirportResponse;
import com.hieu.payload.response.FlightScheduleResponse;

public class FlightScheduleMapper {

    public static FlightSchedule toEntity(FlightScheduleRequest request, Flight flight) {
        if (request == null || flight == null) {
            return null;
        }

        return FlightSchedule.builder()
                .flight(flight)
                .departureAirportId(flight.getDepartureAirportId())
                .arrivalAirportId(flight.getArrivalAirportId())
                .departureTime(request.getDepartureTime())
                .arrivalTime(request.getArrivalTime())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .operatingDays(request.getOperatingDays())
                .isActive(request.getIsActive() != null ? request.getIsActive() : true)
                .build();
    }

    public static FlightScheduleResponse toResponse(FlightSchedule flightSchedule,
                                                      Flight flight,
                                                      AirportResponse departureAirport,
                                                      AirportResponse arrivalAirport) {
        if (flightSchedule == null) {
            return null;
        }

        Flight flightToUse = flight != null ? flight : flightSchedule.getFlight();

        return FlightScheduleResponse.builder()
                .id(flightSchedule.getId())
                .flightId(flightToUse != null ? flightToUse.getId() : null)
                .flightNumber(flightToUse != null ? flightToUse.getFlightNumber() : null)
                .departureAirport(departureAirport)
                .arrivalAirport(arrivalAirport)
                .departureTime(flightSchedule.getDepartureTime())
                .arrivalTime(flightSchedule.getArrivalTime())
                .startDate(flightSchedule.getStartDate())
                .endDate(flightSchedule.getEndDate())
                .operatingDays(flightSchedule.getOperatingDays())
                .isActive(flightSchedule.getIsActive())
                .build();
    }

    public static void updateEntity(FlightScheduleRequest request, FlightSchedule flightSchedule, Flight flight) {
        if (request == null || flightSchedule == null) {
            return;
        }

        if (flight != null) {
            flightSchedule.setFlight(flight);
        }
        if (request.getDepartureTime() != null) {
            flightSchedule.setDepartureTime(request.getDepartureTime());
        }
        if (request.getArrivalTime() != null) {
            flightSchedule.setArrivalTime(request.getArrivalTime());
        }
        if (request.getStartDate() != null) {
            flightSchedule.setStartDate(request.getStartDate());
        }
        if (request.getEndDate() != null) {
            flightSchedule.setEndDate(request.getEndDate());
        }
        if (request.getOperatingDays() != null) {
            flightSchedule.setOperatingDays(request.getOperatingDays());
        }
        if (request.getIsActive() != null) {
            flightSchedule.setIsActive(request.getIsActive());
        }
    }
}
