package com.team.service;

import com.team.entity.Payment;

import org.springframework.stereotype.Service;

@Service
public interface PaymentService {

    public Payment getpayment(Payment payment);

    public Long codeNext();

    public Payment selectPayment(Long paymentNO);

    public int deletePayment(Long paymentNO);

}
