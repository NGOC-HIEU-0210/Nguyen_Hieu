package com.hieu.location_service.service;

import com.hieu.location_service.model.Airport;
import com.hieu.payload.request.AirportRequest;
import com.hieu.payload.response.AirportResponse;

import java.util.List;

public interface AirportService {
    AirportResponse createAirport(AirportRequest request) throws Exception;

    AirportResponse getAirportById(Long id) throws Exception;

    List<AirportResponse> getAllAirports();
    AirportResponse updateAirport(Long id, AirportRequest request) throws Exception;

    void deleteAirport(Long id) throws Exception;
    List<AirportResponse> getAirportByCityId(Long cityId);
}

