package com.hieu.airline_core_service.service.impl;

import com.hieu.airline_core_service.service.AirlineService;
import com.hieu.enums.AirlineStatus;
import com.hieu.payload.request.AirlineRequest;
import com.hieu.payload.response.AirlineDropdownItem;
import com.hieu.payload.response.AirlineResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AirlineServiceImpl implements AirlineService {

    @Override
    public AirlineResponse createAirport(AirlineRequest request, Long ownerId) {
        return null;
    }

    @Override
    public AirlineResponse getAirlineByOwner(Long ownerId) {
        return null;
    }

    @Override
    public AirlineResponse updateAirline(AirlineRequest request, Long ownerId) {
        return null;
    }

    @Override
    public AirlineResponse getAirlineById(Long id) {
        return null;
    }

    @Override
    public Page<AirlineResponse> getAllAirline(Pageable pageable) {
        return null;
    }

    @Override
    public void deleteAirline(Long id, Long ownerId) {

    }

    @Override
    public AirlineResponse changeStatusByAdmin(Long airlineId, AirlineStatus status) {
        return null;
    }

    @Override
    public List<AirlineDropdownItem> getAirlineDropdown() {
        return List.of();
    }
}
