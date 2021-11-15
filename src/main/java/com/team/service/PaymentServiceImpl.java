package com.team.service;

import com.team.entity.Payment;
import com.team.repository.PaymentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    PaymentRepository pRepository;

    @Override
    public Payment getpayment(Payment payment) {
        return pRepository.save(payment);
    }

    @Override
    public Long codeNext() {
        return pRepository.getNextSeqVal();
    }

    @Override
    public Payment selectPayment(Long paymentNO) {
        return pRepository.getById(paymentNO);
    }

    @Override
    public int deletePayment(Long paymentNO) {
        pRepository.deleteById(paymentNO);
        return 1;

    }

}
