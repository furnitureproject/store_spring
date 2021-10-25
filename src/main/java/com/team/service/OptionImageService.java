package com.team.service;

import java.util.List;

import com.team.entity.OptionImage;

import org.springframework.stereotype.Service;

@Service
public interface OptionImageService {
    
    // 이미지 등록
    // return 1
    public int insertOptionImage(OptionImage optionImage);

    // 이미지 수정
    // return 1
    public int updateOptionImage(OptionImage optionImage);

    // 이미지 삭제
    // return 1
    public int deleteOptionImage(Long optionImgNum);

    // 이미지 1개 조회
    // return OptionImage 혹은 null
    public OptionImage selectOptionImageOne(Long optionImageNum);

    // 이미지 전체 조회
    // return List<OptionImage>
    public List<OptionImage> selectOptionImageAll();

    // 제품 옵션 코드로 조회
    // return List<OptionImage>
    public List<OptionImage> selectByOptionCode(Long optionCode);
}
