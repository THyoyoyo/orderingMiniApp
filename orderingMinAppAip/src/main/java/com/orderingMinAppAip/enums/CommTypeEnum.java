package com.orderingMinAppAip.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum CommTypeEnum {

    stop(0,"禁用"),
    start(1,"启用");

    private Integer code;
    private String message;

    CommTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    private static final Map<Integer, String> enumMap = new HashMap<>();
    static {
        for (CommTypeEnum value : CommTypeEnum.values()) {
            enumMap.put(value.getCode(), value.getMessage());
        }
    }

    public static String getNameById(Integer value) {
        return enumMap.get(value);
    }

}
