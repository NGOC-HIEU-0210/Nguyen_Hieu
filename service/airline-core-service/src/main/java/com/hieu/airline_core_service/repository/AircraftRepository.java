package com.hieu.airline_core_service.repository;

import com.hieu.airline_core_service.model.Aircraft;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AircraftRepository extends JpaRepository<Aircraft, Long> {

    List<Aircraft> findByAirlineId(Long airlineId);

    // Tất cả máy bay của mọi airline thuộc owner
    List<Aircraft> findByAirlineOwnerId(Long ownerId);

    // Máy bay của 1 airline cụ thể, và airline đó phải thuộc owner
    List<Aircraft> findByAirlineIdAndAirlineOwnerId(Long airlineId, Long ownerId);

    // Máy bay theo id, chỉ khi airline của nó thuộc owner
    Optional<Aircraft> findByIdAndAirlineOwnerId(Long id, Long ownerId);

    boolean existsByCode(String code);

    boolean existsByAirlineId(Long airlineId);
}