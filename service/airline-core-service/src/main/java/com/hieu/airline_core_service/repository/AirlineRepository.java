package com.hieu.airline_core_service.repository;

import com.hieu.airline_core_service.model.Airline;
import com.hieu.enums.AirlineStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AirlineRepository extends JpaRepository<Airline, Long> {

    // 1 owner có thể có nhiều airline
    List<Airline> findByOwnerId(Long ownerId);

    // Lấy airline theo id và bắt buộc phải thuộc owner (kiểm tra quyền)
    Optional<Airline> findByIdAndOwnerId(Long id, Long ownerId);

    List<Airline> findByStatus(AirlineStatus status);

    boolean existsByIataCode(String iataCode);

    boolean existsByIcaoCode(String icaoCode);
}