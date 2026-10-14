package com.hieu.location_service.service.impl;

import com.hieu.location_service.mapper.AirportMapper;
import com.hieu.location_service.model.Airport;
import com.hieu.location_service.model.City;
import com.hieu.location_service.repository.AirportRepository;
import com.hieu.location_service.repository.CityRepository;
import com.hieu.location_service.service.AirportService;
import com.hieu.payload.request.AirportRequest;
import com.hieu.payload.response.AirportResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AirportServiceImpl implements AirportService {

    public final AirportRepository airportRepository;
    public  final CityRepository cityRepository;
    @Override
    public AirportResponse createAirport(AirportRequest request) throws Exception {
        if (airportRepository.findByIataCode(request.getIataCode()).isPresent()) {
            throw new Exception("Airport with Iata Code Already Exist");
        }
        City city = cityRepository.findById(request.getCityId())
                .orElseThrow(() -> new Exception("City Not Found"));
        Airport airport = AirportMapper.toEntity(request);
        airport.setCity(city);

        Airport savedAirport = airportRepository.save(airport);

        return AirportMapper.toResponse(savedAirport);

    }

    @Override
    public AirportResponse getAirportById(Long id) throws Exception {
        Airport airport = airportRepository.findById(id).orElseThrow(
                () -> new Exception("airport not exist with provided id")
        );
        return AirportMapper.toResponse(airport);
    }

    @Override
    public List<AirportResponse> getAllAirports() {
        return airportRepository.findAll().stream()
                .map(AirportMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public AirportResponse updateAirport(Long id, AirportRequest request) throws Exception {
        Airport existingAirport = airportRepository.findById(id).orElseThrow(
                () -> new Exception("airport not exist with id " + id)
        );
        if(request.getIataCode() != null && !existingAirport.getIataCode().equals(request.getIataCode())
        && airportRepository.findByIataCode(request.getIataCode()).isPresent()) {
            throw new Exception("Airport with Iata Code Already Exist");
        }



        AirportMapper.updateEntity(request, existingAirport);

        Airport updatedAirport = airportRepository.save(existingAirport);

        return AirportMapper.toResponse(updatedAirport);
    }

    @Override
    public void deleteAirport(Long id) throws Exception {
        Airport airport = airportRepository.findById(id).orElseThrow(
                () -> new Exception("airport not exist with provided id")
        );
        airportRepository.delete(airport);
    }

    @Override
    public List<AirportResponse> getAirportByCityId(Long cityId) {

        return airportRepository.findByCityId(cityId).stream()
                .map(AirportMapper::toResponse)
                .collect(Collectors.toList());
    }
}
