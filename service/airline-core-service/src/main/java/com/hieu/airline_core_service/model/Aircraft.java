package com.hieu.airline_core_service.model;

import com.hieu.enums.AircraftStatus;
import com.hieu.enums.AirlineStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;
import java.time.LocalDate;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Aircraft {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(updatable = false, nullable = false)
    private Long id;

    @Column(unique = true, nullable = false)
    private String code;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false, length = 50)
    private String manufacture;

    @Column(nullable = false)
    private Integer seatingCapacity;

    @Column(name = "economy_seats")
    private Integer economySeats = 0;

    @Column(name = "premium_economy_seats")
    private Integer premiumEconomySeats = 0;

    @Column(name = "business_seats")
    private Integer businessSeats = 0;

    @Column(name = "first_class_seats")
    private Integer firstClassSeats = 0;

    private Integer rangeKm;

    @Column(name = "cruising_speed_kmh")
    private Integer cruisingSpeedKmh;

    private Integer maxAltitudeFt;

    @Column(name = "year_of_manufacture")
    private Integer yearOfManufacture;

    private LocalDate nextMaintenanceDate;

    private LocalDate registrationDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private AircraftStatus status = AircraftStatus.ACTIVE;

    private Boolean isAvailable = true;

    @ManyToOne
    private Airline airline;

    private Long currentAirportId;

    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false)
    private Instant createAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private Instant updateAt;

    public Integer getTotalSeats(){
        return economySeats + premiumEconomySeats + businessSeats + firstClassSeats;
    }

    public boolean isOperational(){
        return AircraftStatus.ACTIVE.equals(status)
                && Boolean.TRUE.equals(isAvailable);
    }

    public boolean requiresMaintenance(){
        return nextMaintenanceDate != null
                && nextMaintenanceDate.isBefore(LocalDate.now().plusWeeks(2));
    }




}
