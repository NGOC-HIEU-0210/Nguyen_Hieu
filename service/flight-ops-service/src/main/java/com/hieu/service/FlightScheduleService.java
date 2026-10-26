package com.hieu.service;

import com.hieu.payload.request.FlightScheduleRequest;
import com.hieu.payload.response.FlightScheduleResponse;

import java.util.List;

public interface FlightScheduleService {
    FlightScheduleResponse createFlightSchedule(
        Long airlineId ,   FlightScheduleRequest flightScheduleRequest
    ) throws Exception;
    FlightScheduleResponse getFlightScheduleById(Long id);
    List<FlightScheduleResponse> getFlightScheduleByAirline(Long airlineId);
    FlightScheduleResponse updateFlightSchedule(
            Long id,
            FlightScheduleRequest flightScheduleRequest
    );
    void deleteFlightSchedule(Long id);

}
