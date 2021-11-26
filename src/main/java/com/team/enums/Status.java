package com.team.enums;

public enum Status {
    COMPLETE(200, "성공"), ERROR(100, "적합한 권한을 가지고 있지 않습니다"), UNUSER(300, "존재하지 않는 사용자입니다");

    private String status;
    private long code;

    Status(long code, String status) {
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
