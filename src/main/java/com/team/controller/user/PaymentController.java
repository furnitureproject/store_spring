package com.team.controller.user;

import java.util.HashMap;
import java.util.Map;

import com.team.entity.Payment;
import com.team.service.PaymentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/payment")
public class PaymentController {

    @Autowired
    PaymentService pyService;

    @PostMapping(value = "/insert", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> insertPOST() {
        Map<String, Object> map = new HashMap<>();
        try {
            Payment payment = new Payment();

            payment.setPaymentStatus(1);
            payment.setPaymentNo(pyService.codeNext()); // 시퀀스 생성시켜줘야함
            pyService.getpayment(payment);
            map.put("payment", payment);
            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    @PutMapping(value = "/update", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> updatePUT(@RequestParam long paymentNO) {
        Map<String, Object> map = new HashMap<>();
        try {
            Payment payment = pyService.selectPayment(paymentNO);
            // 환불 처리의 경우 if 사용
            payment.setPaymentStatus(0);
            pyService.getpayment(payment);
            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    @DeleteMapping(value = "/delete", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> deleteDe(@RequestParam long paymentNO) {
        Map<String, Object> map = new HashMap<>();
        try {
            pyService.deletePayment(paymentNO);
            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    @GetMapping(value = "/select", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> selectGET(@RequestParam long paymentNO) {
        Map<String, Object> map = new HashMap<>();
        try {
            Payment payment = pyService.selectPayment(paymentNO);
            map.put("payment", payment);
            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

}
