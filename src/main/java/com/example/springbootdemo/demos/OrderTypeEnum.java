package com.example.springbootdemo.demos;

/**
 * @author zhangdi
 * @date 2024/9/5 15:23
 */
public enum OrderTypeEnum {

    ASSIGN(0, "交办"),
    DIRECT_RESPONSE(1, "直接答复"),
    INVALID_ORDER(2, "无效工单");

    private Integer code;

    private String typeName;

    OrderTypeEnum(int code, String typeName) {
        this.code = code;
        this.typeName = typeName;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return typeName;
    }

    public static String getTypeNameByCode(int code) {
        for (OrderTypeEnum orderType : OrderTypeEnum.values()) {
            if (orderType.getCode() == code) {
                return orderType.getName();
            }
        }
        return null;
    }
}
