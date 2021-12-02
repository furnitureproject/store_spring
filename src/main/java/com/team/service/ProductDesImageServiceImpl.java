package com.team.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManagerFactory;

import com.team.entity.DesProjection;
import com.team.entity.ProductDesImage;
import com.team.repository.ProductDesImageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductDesImageServiceImpl implements ProductDesImageService {

    @Autowired
    ProductDesImageRepository pRepository;

    

    @Override
    public int insertDesImage(ProductDesImage productDesImage) {

        pRepository.save(productDesImage);

        return 1;
    }

    // public void insertDesImageList(List<ProductDesImage> list) {
    // EntityManager em = emf.createEntityManager();
    // em.getTransaction().begin();
    // String sql = " INSERT INTO ProductDesImage(DES_IMG_NUM, DES_IMG_NAME, "
    // + " DES_IMG_DATA, DES_IMG_SIZE, DES_IMG_TYPE, PRODUCT_CODE) "
    // + " VALUES( SEQ_IMG_NUM.NEXTVAL, :desimgname, " + " :desimgdata, :desimgsize,
    // : desimgtype, "
    // + " :product) ";
    // for (ProductDesImage productDesImage : list) {
    // em.createNativeQuery(sql).setParameter("desImgName",
    // productDesImage.getDesImgName())
    // .setParameter("desImgData", productDesImage.getDesImgData())
    // .setParameter("desImgSize", productDesImage.getDesImgSize())
    // .setParameter("desImgType", productDesImage.getDesImgType())
    // .setParameter("product",
    // productDesImage.getProduct().getProductCode()).executeUpdate();
    // }
    // em.getTransaction().commit();
    // }
    @Override
    public int insertDesImageList(List<ProductDesImage> list) {

        pRepository.saveAll(list);

        return 1;
    }

    @Override
    public int updateDesImage(ProductDesImage productDesImage) {

        pRepository.save(productDesImage);

        return 1;
    }

    @Override
    public int deleteDesImage(Long desImgNum) {

        pRepository.deleteById(desImgNum);

        return 1;
    }

    @Override
    public ProductDesImage selectDesImageOne(Long desImgNum) {

        Optional<ProductDesImage> productDesImage = pRepository.findById(desImgNum);

        return productDesImage.orElse(null);
    }

    @Override
    public List<ProductDesImage> selectDesImageAll() {

        return pRepository.findAll();
    }

    @Override
    public List<DesProjection> DesImgNumList(Long productCode) {
        return pRepository.findByProduct_ProductCodeOrderByDesImgNumAsc(productCode);
    }

    // @Override
    // public List<ProductDesImage> selectByProductCode(Long productCode) {

    // // return
    // pRepository.findByProduct_ProductCodeOrderByDesImgNumDesc(productCode);
    // }

}
