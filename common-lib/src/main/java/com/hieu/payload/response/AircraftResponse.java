package com.hieu.payload.response;

import com.hieu.enums.AircraftStatus;
import lombok.*;

import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AircraftResponse {

    private Long id;

    private String code;
    private String model;
    private String manufacture;

    private Integer seatingCapacity;
    private Integer economySeats;
    private Integer premiumEconomySeats;
    private Integer businessSeats;
    private Integer firstClassSeats;
    private Integer totalSeats;

    private Integer rangeKm;
    private Integer cruisingSpeedKmh;
    private Integer maxAltitudeFt;
    private Integer yearOfManufacture;

    private LocalDate registrationDate;
    private LocalDate nextMaintenanceDate;

    private AircraftStatus status;
    private Boolean isAvailable;

    private Boolean isOperational;
    private Boolean requiresMaintenance;

    private Long airlineId;
    private AirlineResponse airline;

    private Long currentAirportId;
    private AirportResponse currentAirport;

    private Instant createdAt;
    private Instant updatedAt;
}
