package com.team.entity;

import java.util.Date;

public interface QnAProjection {
    
    Long getQna_Num();

    String getQna_Title();

    String getQna_Content();

    Date getQna_Regdate();

    String getQna_Reply();

    Date getQna_Reply_ReDate();

    //엔티티의 JoinColumn으로 되어있는 변수 Projection
    // @Value("#{target.user}")
    // User getUser_id();

    // @Value("#{target.user.userid")
    // String getUseruserid();
}
