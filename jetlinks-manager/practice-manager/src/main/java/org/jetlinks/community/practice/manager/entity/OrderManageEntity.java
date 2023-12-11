package org.jetlinks.community.practice.manager.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hswebframework.ezorm.rdb.mapping.annotation.ColumnType;
import org.hswebframework.ezorm.rdb.mapping.annotation.DefaultValue;
import org.hswebframework.ezorm.rdb.mapping.annotation.EnumCodec;
import org.hswebframework.web.api.crud.entity.GenericEntity;
import org.hswebframework.web.api.crud.entity.RecordCreationEntity;
import org.hswebframework.web.api.crud.entity.RecordModifierEntity;
import org.hswebframework.web.crud.annotation.EnableEntityEvent;
import org.hswebframework.web.crud.generator.Generators;
import org.hswebframework.web.utils.DigestUtils;
import org.jetlinks.community.practice.manager.enums.OrderStatus;
import org.springframework.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

/**
 * 订单管理类，用于封装订单相关的信息,如流水号，订单类型，订单状态等
 * @author Wen-Tao
 * @since 2.2
 * @see OrderStatus
 */
@Table(name ="order_manage")
@Getter
@Setter
@Schema(description = "订单管理表")
@EnableEntityEvent
@ToString
public class OrderManageEntity extends GenericEntity<String> implements RecordCreationEntity , RecordModifierEntity {

    @Column(length = 64, nullable = false,updatable = false)
    @Schema(description = "流水号")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String orderSerialNumber;

    @Column(length = 64, updatable = false)
    @Schema(description = "商品id")
    private String goodsId;

    @Column(length = 32, nullable = false)
    @Schema(description = "订单类型")
    private String orderType;

    @Column(length = 32)
    @EnumCodec
    @DefaultValue("unpaid")
    @ColumnType(javaType = String.class)
    @Schema(description = "订单状态")
    private OrderStatus status;

    @Column(length = 64, nullable = false, updatable = false)
    @Schema(description = "创建人ID",accessMode = Schema.AccessMode.READ_ONLY)
    private String creatorId;

    @Column( nullable = false, updatable = false)
    @DefaultValue(generator = Generators.CURRENT_TIME)
    @Schema(description = "创建时间",accessMode = Schema.AccessMode.READ_ONLY)
    private Long createTime;

    @Column(length = 64, nullable = false)
    @Schema(description = "修改人ID")
    private String modifierId;

    @Column( nullable = false)
    @DefaultValue(generator = Generators.CURRENT_TIME)
    @Schema(description = "修改时间")
    private Long modifyTime;

    @Override
    public String getId() {

        if (super.getId() == null || (!StringUtils.hasText(super.getId()))) {
            generateId();
        }
        return super.getId();
    }

    public void generateId() {
        String id = generateHexId(orderSerialNumber, goodsId);
        setId(id);
    }

    public static String generateHexId(String orderSerialNumber, String goodsIds) {

        return DigestUtils.md5Hex(String.join(orderSerialNumber, "|", goodsIds));
    }
}
