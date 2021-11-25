package com.team.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.team.entity.Product;
import com.team.entity.Seller;
import com.team.repository.ProductRepository;
import com.team.repository.QnaRepository;
import com.team.repository.SellerRepository;
import com.team.vo.ProductVO;
import com.team.vo.QnAVO;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellerServiceImpl implements SellerService{

    @Autowired
    SellerRepository sRepository;

    @Autowired
    ProductRepository pRepository;

    @Autowired
    QnaRepository qRepository;

    @Autowired
    SqlSessionFactory sqlFactory;

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

    // 판매자 별 제품 조회
    @Override
    public List<ProductVO> selectProductList(Map<String, Object> map) {
        return sqlFactory.openSession().selectList("select_selleride_product_list", map);
    }
    
    // 제품 총 개수 조회(sellerid 기준)
    @Override
    public long countBySelleridProduct(String id) {
        return pRepository.countByseller_sellerId(id);
    }

    // 판매자 별 qna 조회
    @Override
    public List<QnAVO> selectQnaList(Map<String, Object> map) {
        return sqlFactory.openSession().selectList("select_seller_id_qna_list", map);
    }

    // qna 총 개수 조회(sellerid 기준)
    @Override
    public long countBySelleridQna(String id) {
        return qRepository.countByProduct_Seller_SellerId(id);
    }


    
}
