package org.jetlinks.community.practice.manager.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hswebframework.ezorm.core.param.QueryParam;
import org.hswebframework.web.crud.events.EntityBeforeQueryEvent;
import org.hswebframework.web.crud.events.EntityModifyEvent;
import org.jetlinks.community.practice.manager.entity.OrderManageEntity;
import org.jetlinks.core.event.EventBus;
import org.jetlinks.core.event.Subscription;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;

import javax.annotation.PreDestroy;
import java.util.stream.Collectors;

/**
 * @author Wen-Tao
 * @see
 * @since 2.2
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class EventBusHandle {

    private final EventBus eventBus;
    public final  static  String TOPIC_SELECT="/order/crud/getList";
    public final static String TOPIC_PAY_ORDER="/pay_order/update";
//    @EventListener
    public void springHandleEvent(EntityBeforeQueryEvent<OrderManageEntity> event){
        event.async(this.sendNotify(event.getParam()));
    }

    private Mono<Void> sendNotify(QueryParam param) {
        log.info("-----------------正在发布订单查询前事件-----------");
        return Mono
            .just(param)
            .flatMap(e -> eventBus.publish(TOPIC_SELECT,e))
            .then();

    }

    @EventListener
    public void payOrderHandleEvent(EntityModifyEvent<OrderManageEntity> event){
        log.info("-----------------有新的订单信息改变-----------");
        event.async(Mono
            .just(event.getAfter().stream().filter(item-> "paid".equals(item.getStatus().getValue())).map(i->{
                log.info("----------改变的订单信息有{}-----",i);
               return i;
            }).collect(Collectors.toList()).get(0))
            .flatMap(e -> eventBus.publish(TOPIC_PAY_ORDER,e))
            .then());
    }


    //注解方式订阅事件总线消息
//    @Subscribe(TOPIC_SELECT)
    public Mono<Void> springHandleEventQuery(QueryParam queryParam){
        log.info("-----------------正在订阅事件-----------");
        log.info("内容为:{}",queryParam);
        return Mono.empty();
    }


    //通过eventBus进行总订阅
    private Disposable disposable;
//    @PostConstruct
    public void init(){

        disposable=
            eventBus.subscribe(
                Subscription
                    .builder()
                    .topics(TOPIC_SELECT)
                    .subscriberId("test-event")
                    .local()
                    .broker()
                    .build(),
                payload ->{
                 QueryParam queryParam= (QueryParam) payload.decode();
                 log.info("------------eventBus订阅-------------");
                 log.info("内容为{}",queryParam);
                 return Mono.empty();
                }
            );
    }

    @PreDestroy
    public void shutdown() {
        //取消订阅
        if (disposable != null) {
            disposable.dispose();
        }
    }

}
