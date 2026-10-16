package com.hieu.airline_core_service.mapper;

import com.hieu.airline_core_service.model.Aircraft;
import com.hieu.airline_core_service.model.Airline;
import com.hieu.payload.request.AircraftRequest;
import com.hieu.payload.response.AircraftResponse;

public class AircraftMapper {

    public static Aircraft toEntity(AircraftRequest request, Airline airline){
        if (request == null){
            return null;
        }

        return Aircraft.builder()
                .code(request.getCode())
                .model(request.getModel())
                .manufacture(request.getManufacture())
                .seatingCapacity(request.getSeatingCapacity())
                .economySeats(request.getEconomySeats())
                .premiumEconomySeats(request.getPremiumEconomySeats())
                .businessSeats(request.getBusinessSeats())
                .firstClassSeats(request.getFirstClassSeats())
                .rangeKm(request.getRangeKm())
                .cruisingSpeedKmh(request.getCruisingSpeedKmh())
                .maxAltitudeFt(request.getMaxAltitudeFt())
                .yearOfManufacture(request.getYearOfManufacture())
                .registrationDate(request.getRegistrationDate())
                .nextMaintenanceDate(request.getNextMaintenanceDate())
                .status(request.getStatus())
                .isAvailable(request.getIsAvailable())
                .currentAirportId(request.getCurrentAirportId())
                .airline(airline)
                .build();
    }

    public static AircraftResponse toResponse(Aircraft aircraft){
        if (aircraft == null){
            return null;
        }

        return AircraftResponse.builder()
                .id(aircraft.getId())
                .code(aircraft.getCode())
                .model(aircraft.getModel())
                .manufacture(aircraft.getManufacture())
                .seatingCapacity(aircraft.getSeatingCapacity())
                .economySeats(aircraft.getEconomySeats())
                .premiumEconomySeats(aircraft.getPremiumEconomySeats())
                .businessSeats(aircraft.getBusinessSeats())
                .firstClassSeats(aircraft.getFirstClassSeats())
                .totalSeats(aircraft.getTotalSeats())
                .rangeKm(aircraft.getRangeKm())
                .cruisingSpeedKmh(aircraft.getCruisingSpeedKmh())
                .maxAltitudeFt(aircraft.getMaxAltitudeFt())
                .yearOfManufacture(aircraft.getYearOfManufacture())
                .registrationDate(aircraft.getRegistrationDate())
                .nextMaintenanceDate(aircraft.getNextMaintenanceDate())
                .status(aircraft.getStatus())
                .isAvailable(aircraft.getIsAvailable())
                .isOperational(aircraft.isOperational())
                .requiresMaintenance(aircraft.requiresMaintenance())
                .airlineId(aircraft.getAirline() != null ? aircraft.getAirline().getId() : null)

                .airline(AirlineMapper.toResponse(aircraft.getAirline()))
                .currentAirportId(aircraft.getCurrentAirportId())
                .createdAt(aircraft.getCreateAt())
                .updatedAt(aircraft.getUpdateAt())
                .build();
    }

    public static void updateEntity( Aircraft existingAircraft,AircraftRequest request){
        if (request == null || existingAircraft == null){
            return ;
        }

        if (request.getCode() != null){
            existingAircraft.setCode(request.getCode().trim());
        }
        if (request.getModel() != null){
            existingAircraft.setModel(request.getModel().trim());
        }
        if (request.getManufacture() != null){
            existingAircraft.setManufacture(request.getManufacture().trim());
        }
        if (request.getSeatingCapacity() != null){
            existingAircraft.setSeatingCapacity(request.getSeatingCapacity());
        }
        if (request.getEconomySeats() != null){
            existingAircraft.setEconomySeats(request.getEconomySeats());
        }
        if (request.getPremiumEconomySeats() != null){
            existingAircraft.setPremiumEconomySeats(request.getPremiumEconomySeats());
        }
        if (request.getBusinessSeats() != null){
            existingAircraft.setBusinessSeats(request.getBusinessSeats());
        }
        if (request.getFirstClassSeats() != null){
            existingAircraft.setFirstClassSeats(request.getFirstClassSeats());
        }
        if (request.getRangeKm() != null){
            existingAircraft.setRangeKm(request.getRangeKm());
        }
        if (request.getCruisingSpeedKmh() != null){
            existingAircraft.setCruisingSpeedKmh(request.getCruisingSpeedKmh());
        }
        if (request.getMaxAltitudeFt() != null){
            existingAircraft.setMaxAltitudeFt(request.getMaxAltitudeFt());
        }
        if (request.getYearOfManufacture() != null){
            existingAircraft.setYearOfManufacture(request.getYearOfManufacture());
        }
        if (request.getRegistrationDate() != null){
            existingAircraft.setRegistrationDate(request.getRegistrationDate());
        }
        if (request.getNextMaintenanceDate() != null){
            existingAircraft.setNextMaintenanceDate(request.getNextMaintenanceDate());
        }
        if (request.getStatus() != null){
            existingAircraft.setStatus(request.getStatus());
        }
        if (request.getIsAvailable() != null){
            existingAircraft.setIsAvailable(request.getIsAvailable());
        }
        if (request.getCurrentAirportId() != null){
            existingAircraft.setCurrentAirportId(request.getCurrentAirportId());
        }
//        if (airline != null){
//            existingAircraft.setAirline(airline);
//        }

    }
}
