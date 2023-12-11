package org.jetlinks.community.practice.manager.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hswebframework.web.crud.events.EntityBeforeSaveEvent;
import org.hswebframework.web.exception.BusinessException;
import org.jetlinks.community.practice.manager.entity.GoodsManageEntity;
import org.jetlinks.community.practice.manager.entity.OrderManageEntity;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

/**
 * 基于springEvent 注册与订单相关的事件
 * @author Wen-Tao
 * @see
 * @since 2.2
 */
@Component
@AllArgsConstructor
@Slf4j
public class SpringEventHandle {

    private final GoodsManageService goodsManageService;
    //生成订单时检测是否有对应商品
    @EventListener
    public void checkGoodsIdExist(EntityBeforeSaveEvent<OrderManageEntity> event){
        log.warn("我来了");
        event.async(


               goodsManageService.createQuery()
                                 .in(GoodsManageEntity::getId, event
                                     .getEntity()
                                     .stream()
                                     .map(OrderManageEntity::getGoodsId)
                                     .collect(Collectors.toList()))
                   .fetch()
                   .switchIfEmpty(Mono.error(new BusinessException("没有该商品")))
                                 .then()

           );
    }


//    private final EventBus eventBus;
//    private final String TOPIC_SELECT="/order/crud/getList";
//    @EventListener
//    public void springHandleEvent(EntityBeforeQueryEvent<OrderManageEntity> event){
//        event.async(this.sendNotify(event.getParam()));
//    }
//
//    private Mono<Void> sendNotify(QueryParam param) {
//        log.info("-----------------正在发布事件-----------");
//        return Mono
//            .just(param)
//            .flatMap(e -> eventBus.publish(TOPIC_SELECT,e))
//            .then();
//
//    }
//    //注解方式订阅事件总线消息
//    @Subscribe(TOPIC_SELECT)
//    public Mono<Void> springHandleEventQuery(QueryParam queryParam){
//        log.info("-----------------正在订阅事件-----------");
//        log.info("内容为:{}",queryParam);
//        return Mono.empty();
//    }

}
