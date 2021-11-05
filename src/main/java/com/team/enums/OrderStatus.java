package com.team.enums;

public enum OrderStatus {
    COMPLETE(200, "결제완료"), CANCEL(100, "결제취소"), REFUND(300, "환불"), WAIT(000, "배송중");

    private String status;
    private long code;

    OrderStatus(long code, String status) {
        this.status = status;
        this.code = code;
    }

    public Long getCode() {
        return code;
    }

    public String getStatus() {
        return status;
    }
}
