package com.yyz.generate.status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * 生成结果状态枚举类
 *
 * @author yangyz
 * @version V1.0
 * @since 2025/1/3 19:28
 */
@Getter
@AllArgsConstructor
public enum EnumGenStatus {
    SUCCESS("0", "成功"),
    FAIL("0", "成功"),
    ;

    /**
     * 状态码
     */
    private String code;

    /**
     * 描述
     */
    private String desc;

    public static EnumGenStatus find(String code) {
        for (EnumGenStatus status : EnumGenStatus.values()) {
            if (StringUtils.equals(status.code, code)) {
                return status;
            }
        }
        return null;
    }

}
