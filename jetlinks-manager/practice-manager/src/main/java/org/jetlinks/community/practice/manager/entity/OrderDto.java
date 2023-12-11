package org.jetlinks.community.practice.manager.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.hswebframework.ezorm.rdb.mapping.annotation.ColumnType;
import org.hswebframework.ezorm.rdb.mapping.annotation.EnumCodec;
import org.jetlinks.community.practice.manager.enums.OrderStatus;

import java.io.Serializable;

/**
 * 用于存放订单和对应商品具体信息类
 * @author Wen-Tao
 * @see
 * @since 2.2
 */

@Getter
@Setter
@Schema(description = "订单拓展类")
public class OrderDto implements Serializable {

    @Schema(description = "流水号")
    private String orderSerialNumber;


    @Schema(description = "商品id")
    private String goodsId;

    @Schema(description = "订单类型")
    private String orderType;


    @EnumCodec
    @ColumnType(javaType = String.class)
    @Schema(description = "订单状态")
    private OrderStatus status;

    @Schema(description = "创建人ID",accessMode = Schema.AccessMode.READ_ONLY)
    private String creatorId;

    @Schema(description = "创建时间",accessMode = Schema.AccessMode.READ_ONLY)
    private Long createTime;


    @Schema(description = "修改人ID")
    private String modifierId;


    @Schema(description = "修改时间")
    private Long modifyTime;


    @Schema(description = "商品名称")
    private String goodsName;


    @Schema(description = "商品类型")
    private String goodsType;



}
