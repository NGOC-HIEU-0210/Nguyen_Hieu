package com.hieu.service;


import com.hieu.enums.BookingStatus;
import com.hieu.exception.PaymentException;
import com.hieu.exception.ResourceNotFoundException;
import com.hieu.payload.request.BookingRequest;
import com.hieu.payload.response.BookingResponse;
import com.hieu.payload.response.BookingStatisticsResponse;
import com.hieu.payload.response.PaymentInitiateResponse;

import java.util.List;

public interface BookingService {

    BookingResponse createBooking(BookingRequest request, Long userId)
            throws ResourceNotFoundException, PaymentException;

    BookingResponse updateBooking(Long id, BookingRequest request)
            throws ResourceNotFoundException;

    BookingResponse getBookingById(Long id) throws ResourceNotFoundException;



    List<BookingResponse> getBookingsByAirline(
            Long airlineId,
            String searchQuery,
            BookingStatus status,
            Long flightInstanceId,
            String sortDirection
    );

    List<BookingResponse> getBookingsByUser(Long userId);

    BookingResponse cancelBooking(Long id) throws ResourceNotFoundException;

    void deleteBooking(Long id) throws ResourceNotFoundException;

//    boolean existsById(Long id);
//
//    long count();
//
//    long countByFlightId(Long flightId);
//
//    BookingStatisticsResponse getBookingStatisticsForAirline(Long airlineId);

}
