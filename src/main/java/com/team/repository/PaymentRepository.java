package com.team.repository;

import com.team.entity.Payment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    @Query(value = "SELECT SEQ_PAY.nextval FROM dual", nativeQuery = true)
    Long getNextSeqVal();

}
