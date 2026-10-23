package com.hieu.repository;

import com.hieu.model.Flight;
import com.hieu.payload.response.FlightResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightRepository extends JpaRepository<Flight, Long> {

    Page<Flight> findByAirlineId(String airlineId, Pageable pageable);

}
