package com.hieu.service.impl;

import com.hieu.enums.PaymentGateway;
import com.hieu.enums.PaymentStatus;
import com.hieu.exception.PaymentException;
import com.hieu.mapper.PaymentMapper;
import com.hieu.model.Payment;
import com.hieu.payload.dto.UserDto;
import com.hieu.payload.request.PaymentInitiateRequest;
import com.hieu.payload.request.PaymentVerifyRequest;
import com.hieu.payload.response.PaymentDTO;
import com.hieu.payload.response.PaymentInitiateResponse;
import com.hieu.payload.response.PaymentLinkResponse;
import com.hieu.repository.PaymentRepository;
import com.hieu.service.PaymentService;
import com.hieu.service.gateway.RazorpayService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final RazorpayService razorpayService;

    @Override
    public PaymentInitiateResponse initiatePayment(PaymentInitiateRequest request) throws PaymentException {
        paymentRepository.findByBookingId(request.getBookingId())
                .ifPresent(payment -> {
                    if (payment.getStatus() == PaymentStatus.SUCCESS) {

                        throw new RuntimeException("payment already completed for this booking");

                    }
                });
        Payment payment = Payment.builder()
                .userId(request.getUserId())
                .bookingId(request.getBookingId())
                .amount(request.getAmount())
                .provider(request.getGateway())
                .status(PaymentStatus.PENDING)
                .transactionId(generateTransactionId())
                .build();
        payment = paymentRepository.save(payment);
        PaymentInitiateResponse response = PaymentInitiateResponse.builder()
                .paymentId(payment.getId())
                .gateway(request.getGateway())
                .amount(request.getAmount())
                .transactionId(payment.getTransactionId())
                .description(request.getDescription())
                .success(true)
                .message("payment initiated successfully")
                .build();
        if (request.getGateway() == PaymentGateway.RAZORPAY) {
            UserDto userDto = new UserDto();
            userDto.setId(1L);
            userDto.setFullName("Ngoc Hieu");
            userDto.setEmail("hieunb99999@gmail.com");
            userDto.setPhone("0819289600");


            PaymentLinkResponse paymentLinkResponse = razorpayService.createPaymentLink(userDto, payment);
            response.setRazorpayOrderId(paymentLinkResponse.getPayment_link_id());
            response.setCheckoutUrl(paymentLinkResponse.getPayment_link_url());


        }
        return response;
    }

    private String generateTransactionId() {
        return "TXN_" + System.currentTimeMillis() + "_" +
                UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    @Override
    public PaymentDTO verifyPayment(PaymentVerifyRequest request) throws Exception {
        JSONObject paymentDetails = razorpayService.fetchPaymentDetails(
                request.getRazorpayPaymentId()
        );

        String status = paymentDetails.optString("status");
        JSONObject notes = paymentDetails.optJSONObject("notes");
        Long paymentId = Long.parseLong(notes.optString("payment_id"));

        Payment payment = paymentRepository.findById(paymentId).orElseThrow(
                () -> new Exception("payment not found")
        );
        boolean isValid = "captured".equalsIgnoreCase(status);
        if (isValid) {
            if (payment.getProvider() == PaymentGateway.RAZORPAY) {
                payment.setProviderPaymentId(request.getRazorpayPaymentId());

            }
            payment.setStatus(PaymentStatus.SUCCESS);
            payment.setPaidAt(LocalDateTime.now());
            paymentRepository.save(payment);

            //todo: publish kafka event
        } else {
            payment.setStatus(PaymentStatus.FAILED);
            payment.setFailureReason("Payment verification failed");
            paymentRepository.save(payment);

            //todo: publish kafka event


        }
        return null;
    }

    @Override
    public Page<PaymentDTO> getAllPayments(Pageable pageable) {

        return paymentRepository.findAll(pageable).map(
                PaymentMapper::toDTO
        );
    }

    @Override
    public Map<Long, PaymentDTO> getPaymentsByBookingIds(List<Long> bookingIds) {
        return paymentRepository.findByBookingIdIn(bookingIds)
                .stream().collect(Collectors.toMap(Payment::getId, PaymentMapper::toDTO));
    }
}
