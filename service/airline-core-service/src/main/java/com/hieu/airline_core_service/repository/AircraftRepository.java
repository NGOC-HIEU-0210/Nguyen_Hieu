package com.hieu.airline_core_service.repository;

import com.hieu.airline_core_service.model.Aircraft;
import com.hieu.airline_core_service.model.Airline;
import com.hieu.payload.response.AircraftResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AircraftRepository extends JpaRepository<Aircraft,Long> {

    List<Aircraft> findByAirlineId(Long airlineId);
    boolean existsByCode(String code);
    Aircraft findByIdAndAirlineId(Long id, Long airlineId);
}
