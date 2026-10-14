package com.hieu.location_service.mapper;

import com.hieu.location_service.model.Airport;
import com.hieu.payload.request.AirportRequest;
import com.hieu.payload.response.AirportResponse;

public class AirportMapper {

    public  static Airport toEntity(AirportRequest request){
        if(request == null) return null;
        return Airport.builder()
                .iataCode(request.getIataCode())
                .name(request.getName())
//                .timeZone(request.getTimeZone())
                .address(request.getAddress())
                .geoCode(request.getGeoCode())
                .build();
    }

    public static AirportResponse toResponse(Airport airport){
        if(airport == null) return null;
        return AirportResponse.builder()
                .id(airport.getId())
                .iataCode(airport.getIataCode())
                .name(airport.getName())
                .detailedName(airport.getDetailedName())
//                .timeZone(airport.getTimeZone())
                .address(airport.getAddress())
                .city(CityMapper.toResponse(airport.getCity()))
                .geoCode(airport.getGeoCode())
                .build();
    }
    public static Airport updateEntity(AirportRequest request, Airport existingAirport){
        if (request == null || existingAirport == null) return existingAirport;

        if (request.getIataCode() != null){
            existingAirport.setIataCode(request.getIataCode().trim());
        }
        if (request.getName() != null){
            existingAirport.setName(request.getName().trim());
        }
//        if (request.getTimeZone() != null){
//            existingAirport.setTimeZone(request.getTimeZone().getId());
//        }
        if (request.getAddress() != null){
            existingAirport.setAddress(request.getAddress());
        }
        if (request.getGeoCode() != null){
            existingAirport.setGeoCode(request.getGeoCode());
        }

return existingAirport;
    }
}
