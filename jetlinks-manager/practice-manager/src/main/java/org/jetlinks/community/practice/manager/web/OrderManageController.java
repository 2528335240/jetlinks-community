package org.jetlinks.community.practice.manager.web;

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
import org.jetlinks.community.practice.manager.config.TimerConfig;
import org.jetlinks.community.practice.manager.entity.GoodsManageEntity;
import org.jetlinks.community.practice.manager.entity.OrderDto;
import org.jetlinks.community.practice.manager.entity.OrderManageEntity;
import org.jetlinks.community.practice.manager.service.OrderManageService;
import org.jetlinks.community.practice.manager.timers.OrderTimers;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;


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
    private final QueryHelper queryHelper;

    private final TimerConfig timerConfig;

    private final OrderTimers orderTimers;



   @GetMapping("/getList")
   @Operation(summary = "条件分页查询订单信息")
    public Mono<PagerResult<OrderDto>> getList(@Parameter QueryParamEntity queryParam){
       return queryHelper.select(OrderDto.class)
           .all(OrderManageEntity.class)
           .as(GoodsManageEntity::getGoodsName,OrderDto::setGoodsName)
           .as(GoodsManageEntity::getGoodsType,OrderDto::setGoodsType)
           .from(OrderManageEntity.class)
           .leftJoin(GoodsManageEntity.class, relation-> relation.is(GoodsManageEntity::getId,OrderManageEntity::getGoodsId))
           .where(queryParam)
           .fetchPaged();

    }

    @GetMapping("/getListx")
    @Operation(summary = "根据商品批次查询关联的订单的数量接口")
    public Mono<Integer> getList(@Parameter String goodsBatch){
       return service.getNumByGoodsBatch(goodsBatch);

    }

    @GetMapping("/shoutDownTimer")
    @Operation(summary = "停止定时器")
    public Mono<Void> shoutDownTimer(){
       return orderTimers.shutdown();
    }

    @PostMapping("/startTimer")
    @Operation(summary = "启动定时器")
    public Mono<Void> startTimer(@RequestBody TimerConfig timerConfigs) throws Exception {
       timerConfig.setTimerSpec(timerConfigs.getTimerSpec());
        orderTimers.run();
        return Mono.empty();
    }




}
