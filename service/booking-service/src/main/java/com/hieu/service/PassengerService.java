package com.hieu.service;


import com.hieu.exception.ResourceNotFoundException;
import com.hieu.model.Passenger;
import com.hieu.payload.request.PassengerRequest;

public interface PassengerService {

    Passenger createPassenger(PassengerRequest request, Long userId)
            throws ResourceNotFoundException;

    Passenger findOrCreatePassengerEntity(PassengerRequest request, Long userId);

    Passenger findExistingPassenger(PassengerRequest request);

    boolean existsById(Long id);

    long count();
}
