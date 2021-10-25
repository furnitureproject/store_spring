package com.team.service;

import java.util.List;
import java.util.Optional;

import com.team.entity.ProductEvent;
import com.team.repository.ProductEventRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductEventServiceImpl implements ProductEventService {

    @Autowired
    ProductEventRepository peRepository;

    @Override
    public int insertProductEvent(ProductEvent productEvent) {

        peRepository.save(productEvent);

        return 1;
    }

    @Override
    public int updateProductEvent(ProductEvent productEvent) {

        peRepository.save(productEvent);

        return 1;
    }

    @Override
    public int deleteProductEvent(Long eventCode) {

        peRepository.deleteById(eventCode);

        return 1;
    }

    @Override
    public ProductEvent selectProductEventOne(Long eventCode) {

        Optional<ProductEvent> productEvent = peRepository.findById(eventCode);

        return productEvent.orElse(null);
    }

    @Override
    public List<ProductEvent> selectProductEventAll() {

        return peRepository.findAll();
    }

}
