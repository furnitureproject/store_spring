package com.team.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import com.team.entity.Delivery;
import com.team.entity.DeliveryProjection;
import com.team.repository.DeliveryRepository;
import com.team.vo.DeliveryVO;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeliveryServiceImpl implements DeliveryService{
    
    @Autowired
    DeliveryRepository dRepository;

    @Autowired
    SqlSessionFactory sqlFactory;

    //delivery 등록
    @Override
    public void insertDelivery(Delivery delivery) {
        dRepository.save(delivery);        
    }
    @Override
    public Delivery insertDelivery2(Delivery delivery) {
        return dRepository.save(delivery);
    }

    @Override
    public void insertAllDelivery(List<Delivery> list) {
        dRepository.saveAll(list);
        // EntityManager em = emf.createEntityManager();
        // em.getTransaction().begin();    //트랜젝션 시작
        // for(Delivery delivery : list) {
        //     em.persist(delivery);
        // }
        // em.getTransaction().commit();
    }

    //delivery 수정(seller)
    @Override
    public void updateDelivery(Delivery delivery) {
        dRepository.save(delivery);
    }

    //delivery 정보 가져오기
    @Override
    public Delivery selectDelivery(long no) {
        Optional<Delivery> delivery = dRepository.findById(no);
        return delivery.orElse(null);
    }

    //delivery 삭제
    @Override
    public void deleteDelivery(long no) {
        dRepository.deleteById(no);
    }

     //deliveryCode 1개 조회
     @Override
     public Delivery selectDelOne(Long no) {
         return dRepository.queryDelOne(no);
     }

    //userid 별 delivery 정보 조회
    @Override
    public List<DeliveryProjection> selectUseridDelivery(String userid) {
        return dRepository.findByOrder_Cart_User_UserIdOrderByDeliveryNo(userid);
    }

    //sellerid 별 delivery 정보 조회
    @Override
    public List<DeliveryProjection> selectSelleridDelivery(String sellerid) {
        return dRepository.findByOrder_Cart_ProductOption_Product_Seller_SellerIdOrderByDeliveryNo(sellerid);
    }

    // 유저 별 delivery 조회
    @Override
    public List<DeliveryVO> selectUserDelList(Map<String, Object> map) {
        return sqlFactory.openSession().selectList("select_user_id_del_list", map);
    }

    // delivery 총 개수 조회(userid 기준)
    @Override
    public long countByUseridDelivery(String id) {
        return dRepository.countByUserAddress_User_UserId(id);
    }

}
