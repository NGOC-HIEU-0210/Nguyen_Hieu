package com.hieu.service.impl;


import com.hieu.enums.BookingStatus;
import com.hieu.enums.PaymentGateway;
import com.hieu.exception.PaymentException;
import com.hieu.exception.ResourceNotFoundException;
import com.hieu.mapper.BookingMapper;
import com.hieu.mapper.PassengerMapper;
import com.hieu.model.Booking;
import com.hieu.model.Passenger;
import com.hieu.payload.request.BookingRequest;
import com.hieu.payload.request.PassengerRequest;
import com.hieu.payload.response.*;
import com.hieu.repository.BookingRepository;
import com.hieu.service.BookingService;
import com.hieu.service.PassengerService;
import com.hieu.service.TicketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final PassengerService passengerService;
    private final TicketService ticketService;
    @Override
    public BookingResponse createBooking(BookingRequest request, Long userId) throws ResourceNotFoundException, PaymentException {

        String bookingReference = generateBookingReference();
        Set<Passenger> passengers = new HashSet<>();
        for (PassengerRequest passengerRequest : request.getPassengers()) {
            Passenger passenger =  passengerService.createPassenger(passengerRequest, userId);
            passengers.add(passenger);
        }

        Booking booking = BookingMapper.toEntity(request, userId,
                passengers, bookingReference);


        booking.setAirlineId(1L);
        List<Long> seatInstanceIds = request.getPassengers().stream()
                .map(PassengerRequest::getSeatInstanceId)
                .collect(Collectors.toList());

        booking.setSeatInstanceIds(seatInstanceIds);

         booking = bookingRepository.save(booking);



         for (Passenger passenger : passengers) {
             passenger.setBooking(booking);
         }

         ticketService.generateTicketsForBooking(booking);



        return null;
    }

    @Override
    public BookingResponse updateBooking(Long id, BookingRequest request) throws ResourceNotFoundException {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Booking not found with ID: " + id));

        Set<Passenger> passengers = new HashSet<>();
        for (PassengerRequest passengerRequest : request.getPassengers()) {
            Passenger passenger = passengerService.findOrCreatePassengerEntity(
                    passengerRequest, booking.getUserId());
            passengers.add(passenger);
        }

        BookingMapper.updateEntityFromRequest(request, booking, passengers);
        Booking updated = bookingRepository.save(booking);
        return convertToBookingResponse(updated);
    }

    @Override
    public BookingResponse getBookingById(Long id) throws ResourceNotFoundException {
        Booking booking = bookingRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        return convertToBookingResponse(booking);
    }

    @Override
    public List<BookingResponse> getBookingsByAirline(Long airlineId, String searchQuery, BookingStatus status, Long flightInstanceId, String sortDirection) {

        Sort.Direction direction = "asc".equalsIgnoreCase(sortDirection) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, "bookingDate");
        List<Booking> bookings = bookingRepository.findByAirlineWithFilters(airlineId,
                searchQuery, status, flightInstanceId, sort);
        return bookings.stream().map(
                this :: convertToBookingResponse
        ).toList();
    }

    @Override
    public List<BookingResponse> getBookingsByUser(Long userId) {
        return bookingRepository.findByUserId(userId).stream()
                .map(this::convertToBookingResponse)
                .collect(Collectors.toList());
    }

    @Override
    public BookingResponse cancelBooking(Long id) throws ResourceNotFoundException {
        Booking booking = bookingRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Booking not found"));
        booking.setStatus(BookingStatus.CANCELLED);
        Booking updated = bookingRepository.save(booking);

        return convertToBookingResponse(updated);
    }

    @Override
    public void deleteBooking(Long id) throws ResourceNotFoundException {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Booking not found with ID: " + id));
        bookingRepository.delete(booking);
    }

    private String generateBookingReference() {
        String reference;
        do {
            reference = "BK" + UUID.randomUUID().toString()
                    .substring(0, 8).toUpperCase();
        } while (bookingRepository.findByBookingReference(reference).isPresent());
        return reference;
    }

    private BookingResponse convertToBookingResponse(Booking booking){
        List<FlightCabinAncillaryResponse> ancillaryResponses = new ArrayList<>();
        List<FlightMealResponse> mealResponses = new ArrayList<>();
        PaymentDTO paymentDTO = new PaymentDTO();
        FareResponse fareResponse = new FareResponse();
        FlightResponse flightResponse = new FlightResponse();

        List<SeatInstanceResponse> seatInstanceResponses = new ArrayList<>();
        FlightInstanceResponce flightInstanceResponse = new FlightInstanceResponce();

        return BookingMapper.toResponse(booking,
                paymentDTO,
                fareResponse,
                flightResponse,
                flightInstanceResponse,
                ancillaryResponses,
                mealResponses,
                seatInstanceResponses
        );
    }
//    private BookingResponse convertBookingResponse(Booking booking)  {
//        List<FlightCabinAncillaryResponse> ancillaryResponses=ancillaryClient.getAllByIds(
//                booking.getAncillaryIds()
//        );
//        List<FlightMealResponse> mealResponses=ancillaryClient.getMealsByIds(booking.getMealIds());
//        PaymentDTO paymentDTO=paymentClient.getPaymentByBookingId(booking.getId());
//        FareResponse fareResponse=pricingClient.getFareById(booking.getFareId());
//        FlightResponse flightResponse=flightClient.getFlightById(booking.getFlightId());
//
//        List<SeatInstanceResponse> seatInstanceResponses=seatClient.getAllByIds(booking.getSeatInstanceIds());
//        FlightInstanceResponse flightInstanceResponse=flightClient.getFlightInstanceResponse(booking.getFlightInstanceId());
//
//
//        System.out.println("seat instances -------- "+seatInstanceResponses.size());
//
//        return BookingMapper.toResponse(booking,
//                paymentDTO,
//                fareResponse,
//                flightResponse,
//                flightInstanceResponse,
//                ancillaryResponses,
//                mealResponses,
//                seatInstanceResponses
//        );
//    }
}
