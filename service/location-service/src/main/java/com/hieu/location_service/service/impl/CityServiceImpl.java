package com.hieu.location_service.service.impl;

import com.hieu.location_service.mapper.CityMapper;
import com.hieu.location_service.model.City;
import com.hieu.location_service.repository.CityRepository;
import com.hieu.location_service.service.CityService;
import com.hieu.payload.request.CityRequest;
import com.hieu.payload.response.CityResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {

    private static final Pattern CITY_CODE_PATTERN = Pattern.compile("^[A-Za-z0-9]{2,10}$");

    private final CityRepository cityRepository;

    @Override
    @Transactional
    public CityResponse createCity(CityRequest request) throws Exception {
        if (cityRepository.existsByCityCode(request.getCityCode())) {
            throw new Exception("City code already exists: ");
        }

        City city = CityMapper.toEntity(request);
        City result = cityRepository.save(city);
        return CityMapper.toResponse(result);
    }

    @Override
    @Transactional(readOnly = true)
    public CityResponse getCityById(Long id) throws Exception {
        City city = cityRepository.findById(id).orElseThrow(() ->
            new Exception("City not found with id: " + id)
        );
        return CityMapper.toResponse(city);
    }

    @Override
    @Transactional
    public CityResponse updateCity(Long id, CityRequest request) throws Exception {
        City city = cityRepository.findById(id).orElseThrow( () ->
                    new Exception("City not found with id: " + id)
                );

        if(cityRepository.existsByCityCode(request.getCityCode())) {
            throw new Exception("City with given code already exist");
        }
        City updateCity = cityRepository.save(CityMapper.updateEntity(city,request));

        return CityMapper.toResponse(updateCity);
    }

    @Override
    @Transactional
    public void deleteCity(Long id) throws Exception {
        City city = cityRepository.findById(id).orElseThrow( () ->
                new Exception("City not found with id: " + id)
        );

        cityRepository.delete(city);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CityResponse> getAllCities(Pageable pageable) {
        return cityRepository.findAll(pageable).map(CityMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CityResponse> searchCities(String keyword, Pageable pageable) {
        if (keyword == null || keyword.isBlank()) {
            return getAllCities(pageable);
        }
        return cityRepository.searchByKeyword(keyword.trim(), pageable).map(CityMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CityResponse> getCitiesByCountryCode(String countryCode, Pageable pageable) {
        return cityRepository.findByCountryCodeIgnoreCase(countryCode, pageable)
                .map(CityMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean cityExists(String cityCode) {
        return cityCode != null && cityRepository.existsByCityCode(cityCode);
    }

    @Override
    public boolean validateCityCode(String cityCode) {
        return cityCode != null && CITY_CODE_PATTERN.matcher(cityCode.trim()).matches();
    }

    private City findCityOrThrow(Long id) {
        return cityRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("City not found with id: " + id));
    }
}