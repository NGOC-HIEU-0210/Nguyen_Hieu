package com.hieu.airline_core_service.repository;

import com.hieu.airline_core_service.model.Airline;
import com.hieu.enums.AirlineStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AirlineRepository extends JpaRepository<Airline, Long> {

    Optional<Airline> findByOwnerId(Long ownerId);
    List<Airline> findByStatus(AirlineStatus status);
}
