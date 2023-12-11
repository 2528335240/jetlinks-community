package org.jetlinks.community.practice.manager.websocket;

import lombok.AllArgsConstructor;
import org.hswebframework.ezorm.rdb.mapping.ReactiveRepository;
import org.jetlinks.community.gateway.external.SubscribeRequest;
import org.jetlinks.community.gateway.external.SubscriptionProvider;
import org.jetlinks.community.practice.manager.entity.OrderManageEntity;
import org.jetlinks.community.practice.manager.service.EventBusHandle;
import org.jetlinks.core.event.EventBus;
import org.jetlinks.core.event.Subscription;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

/**
 * @author Wen-Tao
 * @see
 * @since 2.2
 */
@Component
@AllArgsConstructor
public class MyWebsocket implements SubscriptionProvider {
    private final EventBus eventBus;

    private final ReactiveRepository<OrderManageEntity, String> entityRepository;
    @Override
    public String id() {
        return "pay_order";
    }

    @Override
    public String name() {
        return "订单支付订阅";
    }

    @Override
    public String[] getTopicPattern() {
        return new String[]{
            "/pay_order/*"
        };
    }

    @Override
    public Flux<?> subscribe(SubscribeRequest request) {
//            String id = TopicUtils.getPathVariables("/pay_order/{id}",request.getTopic())
//                .get("id");
//        Duration interval=request.getDuration("interval").orElse(null);
//        if(null!=interval){
//            return Flux
//                .interval(Duration.ZERO,interval)
//                .onBackpressureDrop()
//                .concatMap(i -> entityRepository.findById(id));
//        }
        return Flux.concat(
            //查询数据库的最新数据
//            entityRepository.findById(id),
            //订阅本地或者集群其他节点的保存事件
            eventBus.subscribe(
                Subscription
                    .builder()
                    .topics(EventBusHandle.TOPIC_PAY_ORDER)
                    //订阅者ID
                    .subscriberId("wt")
                    //订阅本地
                    .local()
                    //订阅集群
                    .broker()
                    .build(),
              OrderManageEntity.class
            )
        );
    }
}
