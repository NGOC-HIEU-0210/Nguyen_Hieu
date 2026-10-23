package com.hieu.mapper;

import com.hieu.model.Flight;
import com.hieu.payload.request.FlightRequest;
import com.hieu.payload.response.AircraftResponse;
import com.hieu.payload.response.AirlineResponse;
import com.hieu.payload.response.AirportResponse;
import com.hieu.payload.response.FlightResponse;

public class FlightMapper {

    public static Flight toEntity(FlightRequest request) {
        if (request == null) {
            return null;
        }

        return Flight.builder()
                .flightNumber(request.getFlightNumber())

                .aircraftId(request.getAircraftId())
                .departureAirportId(request.getDepartureAirportId())
                .arrivalAirportId(request.getArrivalAirportId())

                .build();
    }

    public static FlightResponse toResponse(Flight flight,
                                            AirlineResponse airlineResponse,
                                            AircraftResponse aircraftResponse,
                                            AirportResponse departureAirport,
                                            AirportResponse arrivalAirport) {
        if (flight == null) {
            return null;
        }

        return FlightResponse.builder()
                .id(flight.getId())
                .flightNumber(flight.getFlightNumber())
                .airline(airlineResponse)
                .aircraft(aircraftResponse)
                .departureAirport(departureAirport)
                .arrivalAirport(arrivalAirport)
                .status(flight.getStatus())
                .createAt(flight.getCreateAt())
                .updateAt(flight.getUpdateAt())
                .build();
    }

    public static void updateEntity( FlightRequest request,Flight flight) {
        if (flight == null || request == null) {
            return;
        }

        if (request.getFlightNumber() != null) {
            flight.setFlightNumber(request.getFlightNumber().trim());
        }
        if (request.getAirlineId() != null) {
            flight.setAirlineId(request.getAirlineId());
        }
        if (request.getAircraftId() != null) {
            flight.setAircraftId(request.getAircraftId());
        }
        if (request.getDepartureAirportId() != null) {
            flight.setDepartureAirportId(request.getDepartureAirportId());
        }
        if (request.getArrivalAirportId() != null) {
            flight.setArrivalAirportId(request.getArrivalAirportId());
        }
        if (request.getStatus() != null) {
            flight.setStatus(request.getStatus());
        }
    }
}
