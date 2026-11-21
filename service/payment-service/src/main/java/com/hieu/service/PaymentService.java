package com.hieu.service;

import com.hieu.exception.PaymentException;
import com.hieu.payload.request.PaymentInitiateRequest;
import com.hieu.payload.request.PaymentVerifyRequest;
import com.hieu.payload.response.PaymentDTO;
import com.hieu.payload.response.PaymentInitiateResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface PaymentService {

    PaymentInitiateResponse initiatePayment(PaymentInitiateRequest request) throws PaymentException;

    PaymentDTO verifyPayment(PaymentVerifyRequest request) throws Exception;


    Page<PaymentDTO> getAllPayments(Pageable pageable);



    Map<Long, PaymentDTO> getPaymentsByBookingIds(List<Long> bookingIds);
}
