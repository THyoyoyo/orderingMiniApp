package com.orderingMinAppAip.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;


@Getter
public enum ReserveStatusEnum {


    CANCEL(0, "取消"),
    PREPARE(1, "准备中"),
    PROCEED(2, "进行中"),
    FINISH(3, "完成");


    private Integer code;
    private String message;

    ReserveStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    private static final Map<Integer, String> enumMap = new HashMap<>();

    static {
        for (ReserveStatusEnum value : ReserveStatusEnum.values()) {
            enumMap.put(value.getCode(), value.getMessage());
        }
    }

    public static String getNameById(Integer value) {
        return enumMap.get(value);
    }

}
