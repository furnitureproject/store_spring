package com.team.service;

import java.util.List;
import java.util.Optional;

import com.team.entity.OptionImage;
import com.team.repository.OptionImageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OptionImageServiceImpl implements OptionImageService{

    @Autowired
    OptionImageRepository oRepository;

    @Override
    public int insertOptionImage(OptionImage optionImage) {
        
        oRepository.save(optionImage);

        return 1;
    }

    @Override
    public int updateOptionImage(OptionImage optionImage) {

        oRepository.save(optionImage);

        return 1;
    }

    @Override
    public int deleteOptionImage(Long optionImgNum) {

        oRepository.deleteById(optionImgNum);

        return 1;
    }

    @Override
    public OptionImage selectOptionImageOne(Long optionImageNum) {

        Optional<OptionImage> optionImage = oRepository.findById(optionImageNum);

        return optionImage.orElse(null);
    }

    @Override
    public List<OptionImage> selectOptionImageAll() {
        
        return oRepository.findAll();
    }

    @Override
    public List<OptionImage> selectByOptionCode(Long optionCode) {
        
        return oRepository.findByProductOption_OptionCodeOrderByOptionImgNumDesc(optionCode);
    }
    
}
