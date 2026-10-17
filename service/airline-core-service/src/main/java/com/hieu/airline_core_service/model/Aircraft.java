package com.hieu.airline_core_service.model;

import com.hieu.enums.AircraftStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Aircraft {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(unique = true, nullable = false, length = 20)
    private String code;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false, length = 50)
    private String manufacture;

    @Column(nullable = false)
    private Integer seatingCapacity;

    @Builder.Default
    @Column(name = "economy_seats")
    private Integer economySeats = 0;

    @Builder.Default
    @Column(name = "premium_economy_seats")
    private Integer premiumEconomySeats = 0;

    @Builder.Default
    @Column(name = "business_seats")
    private Integer businessSeats = 0;

    @Builder.Default
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

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private AircraftStatus status = AircraftStatus.ACTIVE;

    @Builder.Default
    private Boolean isAvailable = true;


    @ManyToOne(optional = false)
    @JoinColumn(name = "airline_id", nullable = false)
    private Airline airline;


    private Long currentAirportId;


    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false)
    private Instant createAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private Instant updateAt;


    @PrePersist
    @PreUpdate
    void applyDefaults() {
        if (economySeats == null) economySeats = 0;
        if (premiumEconomySeats == null) premiumEconomySeats = 0;
        if (businessSeats == null) businessSeats = 0;
        if (firstClassSeats == null) firstClassSeats = 0;
        if (status == null) status = AircraftStatus.ACTIVE;
        if (isAvailable == null) isAvailable = true;
    }

    @Transient
    public Integer getTotalSeats() {
        return nz(economySeats) + nz(premiumEconomySeats)
                + nz(businessSeats) + nz(firstClassSeats);
    }

    @Transient
    public boolean isOperational() {
        return AircraftStatus.ACTIVE.equals(status)
                && Boolean.TRUE.equals(isAvailable);
    }

    @Transient
    public boolean requiresMaintenance() {
        return nextMaintenanceDate != null
                && nextMaintenanceDate.isBefore(LocalDate.now().plusWeeks(2));
    }

    private static int nz(Integer value) {
        return value == null ? 0 : value;
    }
}