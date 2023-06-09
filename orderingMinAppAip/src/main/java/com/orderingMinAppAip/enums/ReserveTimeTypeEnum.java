package com.orderingMinAppAip.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum ReserveTimeTypeEnum {
    MORNING(1,"早上"),
    NOON(2,"中午"),
    NIGHT(3,"晚上"),
    MIDNIGHT(4,"夜宵");

    private Integer code;
    private String message;

    ReserveTimeTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    private static final Map<Integer, String> enumMap = new HashMap<>();
    static {
        for (ReserveTimeTypeEnum value : ReserveTimeTypeEnum.values()) {
            enumMap.put(value.getCode(), value.getMessage());
        }
    }

    public static String getNameById(Integer value) {
        return enumMap.get(value);
    }
}
