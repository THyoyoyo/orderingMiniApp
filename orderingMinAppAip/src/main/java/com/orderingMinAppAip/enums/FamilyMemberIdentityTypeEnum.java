package com.orderingMinAppAip.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;


@Getter
public enum FamilyMemberIdentityTypeEnum {

    ADMIN(1,"管理员"),
    MEMBER(2,"成员"),
    VISITOR(3,"访客");

    private Integer code;
    private String message;

    FamilyMemberIdentityTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    private static final Map<Integer, String> enumMap = new HashMap<>();
    static {
        for (FamilyMemberIdentityTypeEnum value : FamilyMemberIdentityTypeEnum.values()) {
            enumMap.put(value.getCode(), value.getMessage());
        }
    }

    public static String getNameById(Integer value) {
        return enumMap.get(value);
    }

}
