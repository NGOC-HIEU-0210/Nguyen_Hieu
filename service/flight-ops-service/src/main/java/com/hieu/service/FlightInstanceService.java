package com.hieu.service;

import com.hieu.model.FlightInstance;
import com.hieu.payload.request.FlightInstanceRequest;
import com.hieu.payload.response.FlightInstanceResponce;

public interface FlightInstanceService {
    FlightInstanceResponce createFlightInstance(
        Long userId,
        FlightInstanceRequest request
    );
}
