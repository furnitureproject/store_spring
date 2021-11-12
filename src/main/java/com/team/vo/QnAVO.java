package com.team.vo;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QnAVO {
    
    private long qnaNum;

    private String qnaTitle;

    private String qnaContent;

    private Date qnaRegdate;

    private String qnaReply;

    private Date qnaReplyRegdate;

    //private Long productCode;
}
