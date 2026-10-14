package com.hieu.location_service.service;


import com.hieu.location_service.repository.CityRepository;
import com.hieu.payload.request.CityRequest;
import com.hieu.payload.response.CityResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CityService {

    CityResponse createCity(CityRequest request) throws Exception;
    CityResponse getCityById(Long id) throws Exception;
    CityResponse updateCity(Long id, CityRequest request) throws Exception;
    void deleteCity(Long id) throws Exception;
    Page<CityResponse> getAllCities (Pageable pageable);
    Page<CityResponse> searchCities (String keyword,Pageable pageable);
    Page<CityResponse> getCitiesByCountryCode (String countryCode,Pageable pageable);
    boolean cityExists(String cityCode);
    boolean validateCityCode(String cityCode);

}
