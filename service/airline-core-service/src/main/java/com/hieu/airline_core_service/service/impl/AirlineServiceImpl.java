package com.hieu.airline_core_service.service.impl;

import com.hieu.airline_core_service.mapper.AirlineMapper;
import com.hieu.airline_core_service.model.Airline;
import com.hieu.airline_core_service.repository.AircraftRepository;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AirlineServiceImpl implements AirlineService {

    private final AirlineRepository airlineRepository;
    private final AircraftRepository aircraftRepository;

    @Transactional
    @Override
    public AirlineResponse createAirline(AirlineRequest request, Long ownerId) throws Exception {
        if (airlineRepository.existsByIataCode(request.getIataCode())) {
            throw new Exception("IATA code already exists");
        }
        if (airlineRepository.existsByIcaoCode(request.getIcaoCode())) {
            throw new Exception("ICAO code already exists");
        }
        Airline airline = AirlineMapper.toEntity(request, ownerId);
        return AirlineMapper.toResponse(airlineRepository.save(airline));
    }

    @Override
    public List<AirlineResponse> getAirlinesByOwner(Long ownerId) {
        return airlineRepository.findByOwnerId(ownerId)
                .stream()
                .map(AirlineMapper::toResponse)
                .toList();
    }

    @Transactional
    @Override
    public AirlineResponse updateAirline(Long id, AirlineRequest request, Long ownerId) throws Exception {
        Airline airline = airlineRepository.findByIdAndOwnerId(id, ownerId)
                .orElseThrow(() -> new Exception("Airline not found or not owned by you, id " + id));

        if (request.getIataCode() != null
                && !request.getIataCode().equals(airline.getIataCode())
                && airlineRepository.existsByIataCode(request.getIataCode())) {
            throw new Exception("IATA code already exists");
        }
        if (request.getIcaoCode() != null
                && !request.getIcaoCode().equals(airline.getIcaoCode())
                && airlineRepository.existsByIcaoCode(request.getIcaoCode())) {
            throw new Exception("ICAO code already exists");
        }

        AirlineMapper.updateEntity(airline, request);
        return AirlineMapper.toResponse(airlineRepository.save(airline));
    }

    @Override
    public AirlineResponse getAirlineById(Long id) throws Exception {
        Airline airline = airlineRepository.findById(id)
                .orElseThrow(() -> new Exception("Airline not found with id " + id));
        return AirlineMapper.toResponse(airline);
    }

    @Override
    public Page<AirlineResponse> getAllAirline(Pageable pageable) {
        return airlineRepository.findAll(pageable).map(AirlineMapper::toResponse);
    }

    @Transactional
    @Override
    public void deleteAirline(Long id, Long ownerId) throws Exception {
        Airline airline = airlineRepository.findByIdAndOwnerId(id, ownerId)
                .orElseThrow(() -> new Exception("Airline not found or not owned by you, id " + id));

        if (aircraftRepository.existsByAirlineId(id)) {
            throw new Exception("Can't delete airline that still has aircrafts");
        }
        airlineRepository.delete(airline);
    }

    @Transactional
    @Override
    public AirlineResponse changeStatusByAdmin(Long airlineId, AirlineStatus status) throws Exception {
        Airline airline = airlineRepository.findById(airlineId)
                .orElseThrow(() -> new Exception("Airline not found with id " + airlineId));

        airline.setStatus(status);
        return AirlineMapper.toResponse(airlineRepository.save(airline));
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