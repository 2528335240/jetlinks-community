package org.jetlinks.community.practice.manager.web;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hswebframework.web.authorization.annotation.Resource;
import org.hswebframework.web.crud.web.reactive.ReactiveServiceCrudController;
import org.jetlinks.community.practice.manager.entity.GoodsManageEntity;
import org.jetlinks.community.practice.manager.service.GoodsManageService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author Wen-Tao
 * @see
 * @since 2.2
 */
@RestController
@RequestMapping("goods/crud")
@AllArgsConstructor
@Getter
@Resource(id="goodsManage",name = "商品控制器")
@Tag(name ="商品增删改查")
public class GoodsManageController implements ReactiveServiceCrudController<GoodsManageEntity,String> {
    private final GoodsManageService service;


}
