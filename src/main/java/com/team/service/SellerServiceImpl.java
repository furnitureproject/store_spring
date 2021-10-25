package com.team.service;

import java.util.List;
import java.util.Optional;

import com.team.entity.Seller;
import com.team.repository.SellerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellerServiceImpl implements SellerService{

    @Autowired
    SellerRepository sRepository;

    @Override
    public int insertSeller(Seller seller) {
        
        sRepository.save(seller);

        return 1;
    }

    @Override
    public Seller selectSellerOne(String sellerId) {
        
        Optional<Seller> seller = sRepository.findById(sellerId);

        return seller.orElse(null);
    }

    @Override
    public int updateSeller(Seller seller) {
        
        sRepository.save(seller);

        return 1;
    }

    @Override
    public int deleteSeller(Seller seller) {
        
        sRepository.deleteById(seller.getSellerId());

        return 1;
    }

    @Override
    public List<Seller> selectSellerAll() {
        
        return sRepository.findAll();
    }
    
}
