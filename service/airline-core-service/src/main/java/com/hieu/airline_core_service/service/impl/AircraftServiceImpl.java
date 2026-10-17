package com.hieu.airline_core_service.service.impl;

import com.hieu.airline_core_service.mapper.AircraftMapper;
import com.hieu.airline_core_service.model.Aircraft;
import com.hieu.airline_core_service.model.Airline;
import com.hieu.airline_core_service.repository.AircraftRepository;
import com.hieu.airline_core_service.repository.AirlineRepository;
import com.hieu.airline_core_service.service.AircraftService;
import com.hieu.payload.request.AircraftRequest;
import com.hieu.payload.response.AircraftResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AircraftServiceImpl implements AircraftService {

    private final AircraftRepository aircraftRepository;
    private final AirlineRepository airlineRepository;

    @Transactional
    @Override
    public AircraftResponse createAicraft(AircraftRequest request, Long airlineId, Long ownerId) throws Exception {
        // airline phải tồn tại VÀ thuộc owner đang gọi
        Airline airline = airlineRepository.findByIdAndOwnerId(airlineId, ownerId)
                .orElseThrow(() -> new Exception("Airline not found or not owned by you, id " + airlineId));

        Aircraft aircraft = AircraftMapper.toEntity(request, airline);

        if (aircraftRepository.existsByCode(aircraft.getCode())) {
            throw new Exception("Code already exist with another aircraft");
        }
        if (aircraft.getSeatingCapacity() < aircraft.getTotalSeats()) {
            throw new Exception("Total seats can't exceed aircraft's seating capacity");
        }
        return AircraftMapper.toResponse(aircraftRepository.save(aircraft));
    }

    @Override
    public AircraftResponse getAircraftById(Long id) throws Exception {
        Aircraft aircraft = aircraftRepository.findById(id)
                .orElseThrow(() -> new Exception("Aircraft not exist with id " + id));
        return AircraftMapper.toResponse(aircraft);
    }

    @Override
    public List<AircraftResponse> listAllAicraftByOwner(Long ownerId, Long airlineId) throws Exception {
        List<Aircraft> aircrafts;
        if (airlineId != null) {
            // lọc theo 1 airline, đồng thời bảo đảm airline thuộc owner
            aircrafts = aircraftRepository.findByAirlineIdAndAirlineOwnerId(airlineId, ownerId);
        } else {
            aircrafts = aircraftRepository.findByAirlineOwnerId(ownerId);
        }
        return aircrafts.stream().map(AircraftMapper::toResponse).toList();
    }

    @Transactional
    @Override
    public AircraftResponse updateAicraft(Long id, AircraftRequest request, Long ownerId) throws Exception {
        Aircraft aircraft = aircraftRepository.findByIdAndAirlineOwnerId(id, ownerId)
                .orElseThrow(() -> new Exception("Aircraft not found or not owned by you, id " + id));

        if (request.getCode() != null
                && !aircraft.getCode().equals(request.getCode())
                && aircraftRepository.existsByCode(request.getCode())) {
            throw new Exception("Code already exist with another aircraft");
        }
        AircraftMapper.updateEntity(aircraft, request);
        return AircraftMapper.toResponse(aircraftRepository.save(aircraft));
    }

    @Transactional
    @Override
    public void deleteAicraft(Long id, Long ownerId) throws Exception {
        Aircraft aircraft = aircraftRepository.findByIdAndAirlineOwnerId(id, ownerId)
                .orElseThrow(() -> new Exception("Aircraft not found or not owned by you, id " + id));
        aircraftRepository.delete(aircraft);
    }
}