package com.team.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import com.team.entity.OptionProjection;
import com.team.entity.ProductOption;
import com.team.repository.ProductOptionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductOptionServiceImpl implements ProductOptionService {

    @Autowired
    ProductOptionRepository poRepository;

    @Autowired
    EntityManagerFactory emf;

    @Override
    public Long countByCode(long productCode) {
        return poRepository.countByProduct_ProductCode(productCode);
    }

    @Override
    public int insertProductOption(ProductOption productOption) {

        poRepository.save(productOption);

        return 1;
    }

    @Override
    public void insertProductOptionList(List<ProductOption> list) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        String sql = " INSERT INTO PRODUCT_OPTION(OPTION_CODE, OPTION_NAME, OPTION_QUANTITY, "
                + " OPTION_PRICE, PRODUCT_CODE, EVENT_CODE) "
                + " VALUES( :optionCode, :optionName, :optionQuantity, :optionPrice, :product, " + " :productEvent) ";
        for (ProductOption productOption : list) {
            em.createNativeQuery(sql).setParameter("optionCode", productOption.getOptionCode())
                    .setParameter("optionName", productOption.getOptionName())
                    .setParameter("optionQuantity", productOption.getOptionQuantity())
                    .setParameter("optionPrice", productOption.getOptionPrice())
                    .setParameter("product", productOption.getProduct().getProductCode())
                    .setParameter("productEvent", productOption.getProductEvent().getEventCode()).executeUpdate();

        }
        em.getTransaction().commit();
    }

    @Override
    public int updateProductOption(ProductOption productOption) {

        poRepository.save(productOption);

        return 1;
    }

    @Override
    public int deleteProductOption(Long optionCode) {

        poRepository.deleteById(optionCode);

        return 1;
    }

    @Override
    public ProductOption selectProductOptionOne(Long optionCode) {

        Optional<ProductOption> productoption = poRepository.findById(optionCode);

        return productoption.orElse(null);
    }

    @Override
    public List<ProductOption> selectProductOptionAll() {

        return poRepository.findAll();
    }

    @Override
    public List<OptionProjection> selectByProductCode(Long productCode) {
        return poRepository.findByProduct_ProductCodeOrderByOptionCodeAsc(productCode);
    }

    @Override
    public Long selectOptionPrice(Long productCode) {
        return poRepository.SelectPriceLow(productCode);
    }

}
