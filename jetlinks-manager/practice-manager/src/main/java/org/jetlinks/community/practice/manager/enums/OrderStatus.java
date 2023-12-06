package org.jetlinks.community.practice.manager.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hswebframework.web.dict.I18nEnumDict;

/**
 * 订单状态枚举类，包括未付款、付款成功、确认收货的状态
 * @Author: Wen-Tao
 * @since: 2.2
 */
@Getter
@AllArgsConstructor
public enum OrderStatus implements I18nEnumDict<String> {
    unpaid("未付款"),
    paid("付款成功"),
    received("确认收货");


    private String text;

    @Override
    public String getValue() {
        return name();
    }
}
