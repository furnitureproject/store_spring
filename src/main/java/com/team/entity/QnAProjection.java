package com.team.entity;

import java.util.Date;

public interface QnAProjection {
    
    Long getQna_Num();

    String getQna_Title();

    String getQna_Content();

    Date getQna_Regdate();

    String getQna_Reply();

    Date getQna_Reply_RegDate();

}
