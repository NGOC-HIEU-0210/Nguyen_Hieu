package com.hieu.airline_core_service.service.impl;

import com.hieu.airline_core_service.mapper.AirlineMapper;
import com.hieu.airline_core_service.model.Airline;
import com.hieu.airline_core_service.repository.AirlineRepository;
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

    private final AirlineRepository airlineRepository;

    @Override
    public AirlineResponse createAirport(AirlineRequest request, Long ownerId) {
        Airline airline = AirlineMapper.toEntity(request,ownerId);
        Airline savedAirline = airlineRepository.save(airline);
        return AirlineMapper.toResponse(savedAirline);
    }

    @Override
    public AirlineResponse getAirlineByOwner(Long ownerId) throws Exception {
        Airline airline = airlineRepository.findByOwnerId(ownerId)
                .orElseThrow(
                        () -> new Exception("Airline not found with owner id " + ownerId)
                );

        return AirlineMapper.toResponse(airline);
    }

    @Override
    public AirlineResponse updateAirline(AirlineRequest request, Long ownerId) throws Exception {
        Airline airline = airlineRepository.findByOwnerId(ownerId)
                .orElseThrow(
                        () -> new Exception("Airline not found with owner id " + ownerId)
                );
        AirlineMapper.updateEntity(airline,request);
        Airline savedAirline = airlineRepository.save(airline);
        return AirlineMapper.toResponse(savedAirline);
    }

    @Override
    public AirlineResponse getAirlineById(Long id) throws Exception {
        Airline airline = airlineRepository.findByOwnerId(id)
                .orElseThrow(
                        () -> new Exception("Airline not found with id " + id)
                );

        return AirlineMapper.toResponse(airline);
    }

    @Override
    public Page<AirlineResponse> getAllAirline(Pageable pageable) {

        return airlineRepository.findAll(pageable).map(
                AirlineMapper::toResponse
        );
    }

    @Override
    public void deleteAirline(Long id, Long ownerId) throws Exception {
        Airline airline = airlineRepository.findByOwnerId(id)
                .orElseThrow(
                        () -> new Exception("Airline not found with id " + id)
                );

        airlineRepository.delete(airline);
    }

    @Override
    public AirlineResponse changeStatusByAdmin(Long airlineId, AirlineStatus status) throws Exception {
        Airline airline = airlineRepository.findByOwnerId(airlineId)
                .orElseThrow(
                        () -> new Exception("Airline not found with id " + airlineId)
                );

        airline.setStatus(status);
        airlineRepository.save(airline);
        Airline updateAirline = airlineRepository.save(airline);

        return AirlineMapper.toResponse(updateAirline);
    }

    @Override
    public List<AirlineDropdownItem> getAirlineDropdown() {
        return airlineRepository.findByStatus(AirlineStatus.ACTIVE)
                .stream()
                .map(a -> AirlineDropdownItem.builder()
                        .id(a.getId())
                        .name(a.getName())
                        .iataCode(a.getIataCode())
                        .icaoCode(a.getIcaoCode())
                        .logoUrl(a.getLogoUrl())

                        .build()
                ).toList();

    }
}
