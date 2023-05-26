package com.orderingMinAppAip.exception;

import lombok.Data;

@Data
public class authorityException extends RuntimeException {
    private Integer code;
    private String message;

    public authorityException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
