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
    public AircraftResponse createAicraft(AircraftRequest request, Long ownerId) throws Exception {
        Airline airline = airlineRepository.findByOwnerId(ownerId).orElseThrow(
                () -> new Exception("Airline not exist for this ownerId")
        );
        Aircraft aircraft = AircraftMapper.toEntity(request, airline);

        if (aircraftRepository.existsByCode(aircraft.getCode())){
            throw new Exception("Code already exist with another aircraft");
        }
        if (aircraft.getSeatingCapacity() < aircraft.getTotalSeats()){
            throw  new Exception("Total seats can't be less than aircraft's capacity");
        }
        return AircraftMapper.toResponse(
                aircraftRepository.save(aircraft)
        );
    }

    @Override
    public AircraftResponse getAircraftById(Long id) throws Exception {

        Aircraft aircraft = aircraftRepository.findById(id).orElseThrow(
                () -> new Exception("Aircraft not exist with id")
        );
        return AircraftMapper.toResponse(aircraft);
    }

    @Override
    public List<AircraftResponse> listAllAicraftByOwner(Long ownerId) throws Exception {
        Airline airline = airlineRepository.findByOwnerId(ownerId).orElseThrow(
                () -> new Exception("This owner don't have airline")
        );

        return aircraftRepository.findByAirlineId(airline.getId())
                .stream()
                .map(AircraftMapper::toResponse).toList();
    }

    @Transactional
    @Override
    public AircraftResponse updateAicraft(Long id, AircraftRequest request, Long ownerId) throws Exception {
        Airline airline = airlineRepository.findByOwnerId(ownerId).orElseThrow(
                () -> new Exception("This owner don't have airline")
        );
        Aircraft aircraft = aircraftRepository.findByIdAndAirlineId(id, airline.getId());
        if (aircraft == null){
            throw  new Exception("Aircraft not exsit with id");
        }
        if (request.getCode() != null
                && !aircraft.getCode().equals(request.getCode())
                && aircraftRepository.existsByCode(request.getCode())){
            throw  new Exception("Code already exist with another aircraft");
        }
         AircraftMapper.updateEntity(aircraft,request);
        return AircraftMapper.toResponse(aircraftRepository.save(aircraft));
    }

    @Transactional
    @Override
    public void deleteAicraft(Long id, Long ownerId) throws Exception {
        Airline airline = airlineRepository.findByOwnerId(id).orElseThrow(
                () -> new Exception("This owner don't have airline")
                );

        Aircraft aircraft = aircraftRepository.findByIdAndAirlineId(id, airline.getId());
        if (aircraft == null){
            throw   new Exception("Aircraft not exist with id");
        }

        aircraftRepository.delete(aircraft);
    }
}
