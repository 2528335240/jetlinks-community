package org.jetlinks.community.practice.manager.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.hswebframework.ezorm.rdb.mapping.annotation.DefaultValue;
import org.hswebframework.web.api.crud.entity.GenericEntity;
import org.hswebframework.web.api.crud.entity.RecordCreationEntity;
import org.hswebframework.web.api.crud.entity.RecordModifierEntity;
import org.hswebframework.web.crud.annotation.EnableEntityEvent;
import org.hswebframework.web.crud.generator.Generators;
import org.hswebframework.web.utils.DigestUtils;
import org.hswebframework.web.validator.CreateGroup;

import javax.persistence.*;

import javax.validation.constraints.Pattern;

/**
 * 商品管理类，用于封装商品管理相关的信息 如 商品名称，商品分类，商品批次等。
 * @Author: Wen-Tao
 * @since: 2.2
 */
@Table(name ="goods_manage")
@Getter
@Setter
@Schema(description = "商品管理表")
@EnableEntityEvent
public class GoodsManageEntity extends GenericEntity<String> implements RecordCreationEntity, RecordModifierEntity {


    @Column(length = 64, nullable = false)
    @Schema(description = "商品名称")
    private String goodsName;

    @Column(length = 64)
    @Schema(description = "商品类型")
    private String goodsType;

    @Column(length = 64)
    @Pattern(regexp = "[A-Za-z]{3}@[0-9]{2}", message = "批次格式只能是3位字母+@+2位数字", groups = CreateGroup.class)
    @Schema(description = "商品批次")
    private String goodsBatch;

    @Column(nullable = false)
    @GeneratedValue(generator = Generators.CURRENT_TIME)
    @Schema(description = "上架时间")
    private Long shelveTime;

    @Column
    @Schema(description = "下架时间")
    private Long downShelveTime;

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
        if (super.getId() == null) {
            generateId();
        }
        return super.getId();
    }

    public void generateId() {
        String id = generateHexId(goodsName, goodsBatch);
        setId(id);
    }

    public static String generateHexId(String goodsName, String goodsBatch) {
        return DigestUtils.md5Hex(String.join(goodsName, "|", goodsBatch));
    }

}
