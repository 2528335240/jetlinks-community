package org.jetlinks.community.practice.manager.controller;

import com.google.common.base.Function;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hswebframework.web.api.crud.entity.PagerResult;
import org.hswebframework.web.api.crud.entity.QueryParamEntity;
import org.hswebframework.web.authorization.annotation.Resource;
import org.hswebframework.web.crud.query.QueryHelper;
import org.hswebframework.web.crud.web.reactive.ReactiveServiceCrudController;
import org.jetlinks.community.practice.manager.entity.GoodsManageEntity;
import org.jetlinks.community.practice.manager.entity.OrderManageEntity;
import org.jetlinks.community.practice.manager.service.GoodsManageService;
import org.jetlinks.community.practice.manager.service.OrderManageService;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Conditional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Wen-Tao
 * @since 2.2
 */
@RestController
@RequestMapping("/order/crud")
@AllArgsConstructor
@Getter
@Resource(id="orderManage",name = "订单管理控制器")
@Tag(name ="订单增删改查")
public class OrderManageController implements ReactiveServiceCrudController<OrderManageEntity,String> {
    private final OrderManageService service;
    private final GoodsManageService goodsService;
    private final QueryHelper queryHelper;
   @GetMapping("/getList")
   @Operation(summary = "根据订单号模糊查询")
    public Flux<OrderManageEntity> getList(String num){
        return service.getList(num);
    }

}
