package com.team.repository;

import com.team.entity.UserInput;
import com.team.entity.UserInputProjection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserinputRepository extends JpaRepository<UserInput, Long>{
    
    //userinput 조회
    @Query(value = "SELECT USER_INPUT.UINPUT_NO, USER_INPUT.UINPUT_ZIPCODE, "
        + "USER_INPUT.UINPUT_ADDRESS, USER_INPUT.UINPUT_ADDRESS_DETAIL, "
        + "USER_INPUT.UINPUT_NAME, USER_INPUT.UINPUT_REQUIREMENT, ORDER_TB.ORDER_NO "
        + "FROM USER_INPUT, ORDER_TB WHERE USER_INPUT.ORDER_NO = ORDER_TB.ORDER_NO=:no", nativeQuery = true)
    public UserInputProjection queryUserInput(@Param("no") long no);

    //userinput 1개 조회
    @Query(value = "SELECT * FROM USER_INPUT WHERE UINPUT_NO =:no", nativeQuery = true)
    public UserInputProjection queryUserInputOne(@Param("no") Long no);
    
}
