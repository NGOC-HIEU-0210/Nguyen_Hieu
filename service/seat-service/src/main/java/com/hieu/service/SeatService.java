package com.hieu.service;

import com.hieu.payload.request.SeatRequest;
import com.hieu.payload.response.SeatResponse;


import java.util.List;

public interface SeatService {


    void generateSeats(Long seatMapId) throws Exception;
    SeatResponse getSeatById(Long id);
    List<SeatResponse> getAll();
    SeatResponse updateSeat(Long id, SeatRequest request);

}
