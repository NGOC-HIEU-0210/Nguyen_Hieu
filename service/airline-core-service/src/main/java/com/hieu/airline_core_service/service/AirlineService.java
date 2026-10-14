package com.hieu.airline_core_service.service;

import com.hieu.enums.AirlineStatus;
import com.hieu.payload.request.AirlineRequest;
import com.hieu.payload.response.AirlineDropdownItem;
import com.hieu.payload.response.AirlineResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AirlineService {
    AirlineResponse createAirport(AirlineRequest request, Long ownerId);
    AirlineResponse getAirlineByOwner(Long ownerId);
    AirlineResponse updateAirline(AirlineRequest request, Long ownerId);
    AirlineResponse getAirlineById(Long id);
    Page<AirlineResponse> getAllAirline(Pageable pageable);
    void deleteAirline(Long id, Long ownerId);

    AirlineResponse changeStatusByAdmin(Long airlineId, AirlineStatus status);
    List<AirlineDropdownItem> getAirlineDropdown();


}
