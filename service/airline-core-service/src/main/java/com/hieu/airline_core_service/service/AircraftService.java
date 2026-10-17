package com.hieu.airline_core_service.service;

import com.hieu.payload.request.AircraftRequest;
import com.hieu.payload.response.AircraftResponse;

import java.util.List;

public interface AircraftService {
    AircraftResponse createAicraft(AircraftRequest request, Long airlineId, Long ownerId) throws Exception;
    AircraftResponse getAircraftById(Long id) throws Exception;


    List<AircraftResponse> listAllAicraftByOwner(Long ownerId, Long airlineId) throws Exception;

    AircraftResponse updateAicraft(Long id, AircraftRequest request, Long ownerId) throws Exception;
    void deleteAicraft(Long id, Long ownerId) throws Exception;
}