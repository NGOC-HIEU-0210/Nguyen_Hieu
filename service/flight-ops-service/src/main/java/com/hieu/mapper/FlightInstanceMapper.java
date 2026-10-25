package com.hieu.mapper;

import com.hieu.enums.FlightStatus;
import com.hieu.model.Flight;
import com.hieu.model.FlightInstance;
import com.hieu.payload.request.FlightInstanceRequest;
import com.hieu.payload.response.AircraftResponse;
import com.hieu.payload.response.AirlineResponse;
import com.hieu.payload.response.AirportResponse;
import com.hieu.payload.response.FlightInstanceResponce;

public class FlightInstanceMapper {

    public static FlightInstance toEntity(FlightInstanceRequest request, Flight flight) {
        if (request == null) {
            return null;
        }

        FlightInstance flightInstance = FlightInstance.builder()
                .airlineId(request.getAirlineId())
                .flight(flight)
                .departureAirportId(request.getDepartureAirportId())
                .arrivalAirportId(request.getArrivalAirportId())
                .departureDateTime(request.getDepartureDateTime())
                .arrivalDateTime(request.getArrivalDateTime())
                .totalSeats(request.getTotalSeats())
                .availableSeats(request.getAvailableSeats() != null ? request.getAvailableSeats() : request.getTotalSeats())
                .status(request.getStatus() != null ? request.getStatus() : FlightStatus.SCHEDULED)
                .minAdvanceBookingDays(request.getMinAdvanceBookingBays())
                .maxAdvanceBookingDays(request.getMaxAdvanceBookingBays())
                .build();

        flightInstance.setScheduleId(request.getScheduleId());
        flightInstance.setIsActive(request.getIsActive() != null ? request.getIsActive() : true);

        return flightInstance;
    }

    public static FlightInstanceResponce toResponse(FlightInstance flightInstance,
                                                    AircraftResponse aircraftResponse,
                                                    AirlineResponse airline,
                                                    AirportResponse departureAirport,
                                                    AirportResponse arrivalAirport) {
        if (flightInstance == null) {
            return null;
        }

        Flight flight = flightInstance.getFlight();

        return FlightInstanceResponce.builder()
                .id(flightInstance.getId())
                .flightId(flight != null ? flight.getId() : null)
                .flightNumber(flight != null ? flight.getFlightNumber() : null)
                .airlineId(flightInstance.getAirlineId())
                .airlineName(airline.getName())
                .airlineLogo(airline.getLogoUrl())
                .aircraftId(flight != null ? flight.getAircraftId() : null)
                .aircraftModal(aircraftResponse.getModel())
                .aircraftCode(aircraftResponse.getCode())
                .departureAirport(departureAirport)
                .arrivalAirport(arrivalAirport)
                .departureDateTime(flightInstance.getDepartureDateTime())
                .arrivalDateTime(flightInstance.getArrivalDateTime())
                .formattedDuration(flightInstance.getFormatedDuration())
                .totalSeats(flightInstance.getTotalSeats())
                .availableSeats(flightInstance.getAvailableSeats())
                .status(flightInstance.getStatus())
                .minAdvanceBookingDays(flightInstance.getMinAdvanceBookingDays())
                .maxAdvanceBookingDays(flightInstance.getMaxAdvanceBookingDays())
                .isActive(flightInstance.getIsActive())
                .build();
    }

    public static void updateEntity(FlightInstanceRequest request, FlightInstance flightInstance) {
        if (request == null || flightInstance == null) {
            return;
        }

        if (request.getAirlineId() != null) {
            flightInstance.setAirlineId(request.getAirlineId());
        }
        if (request.getScheduleId() != null) {
            flightInstance.setScheduleId(request.getScheduleId());
        }
        if (request.getDepartureAirportId() != null) {
            flightInstance.setDepartureAirportId(request.getDepartureAirportId());
        }
        if (request.getArrivalAirportId() != null) {
            flightInstance.setArrivalAirportId(request.getArrivalAirportId());
        }
        if (request.getDepartureDateTime() != null) {
            flightInstance.setDepartureDateTime(request.getDepartureDateTime());
        }
        if (request.getArrivalDateTime() != null) {
            flightInstance.setArrivalDateTime(request.getArrivalDateTime());
        }
        if (request.getTotalSeats() != null) {
            flightInstance.setTotalSeats(request.getTotalSeats());
        }
        if (request.getAvailableSeats() != null) {
            flightInstance.setAvailableSeats(request.getAvailableSeats());
        }
        if (request.getStatus() != null) {
            flightInstance.setStatus(request.getStatus());
        }
        if (request.getMinAdvanceBookingBays() != null) {
            flightInstance.setMinAdvanceBookingDays(request.getMinAdvanceBookingBays());
        }
        if (request.getMaxAdvanceBookingBays() != null) {
            flightInstance.setMaxAdvanceBookingDays(request.getMaxAdvanceBookingBays());
        }
        if (request.getIsActive() != null) {
            flightInstance.setIsActive(request.getIsActive());
        }
    }
}
