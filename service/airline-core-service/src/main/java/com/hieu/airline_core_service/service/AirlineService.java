package com.hieu.airline_core_service.service;

import com.hieu.enums.AirlineStatus;
import com.hieu.payload.request.AirlineRequest;
import com.hieu.payload.response.AirlineDropdownItem;
import com.hieu.payload.response.AirlineResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AirlineService {
    AirlineResponse createAirline(AirlineRequest request, Long ownerId) throws Exception;
    List<AirlineResponse> getAirlinesByOwner(Long ownerId);
    AirlineResponse updateAirline(Long id, AirlineRequest request, Long ownerId) throws Exception;
    AirlineResponse getAirlineById(Long id) throws Exception;
    Page<AirlineResponse> getAllAirline(Pageable pageable);
    void deleteAirline(Long id, Long ownerId) throws Exception;

    AirlineResponse changeStatusByAdmin(Long airlineId, AirlineStatus status) throws Exception;
    List<AirlineDropdownItem> getAirlineDropdown();
}