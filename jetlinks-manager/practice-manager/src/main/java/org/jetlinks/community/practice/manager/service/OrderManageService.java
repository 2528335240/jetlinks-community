package org.jetlinks.community.practice.manager.service;

import org.hswebframework.web.crud.service.GenericReactiveCrudService;
import org.jetlinks.community.practice.manager.entity.GoodsManageEntity;
import org.jetlinks.community.practice.manager.entity.OrderManageEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

/**
 * @author Wen-Tao
 * @see
 * @since 2.2
 */
@Service
public class OrderManageService extends GenericReactiveCrudService<OrderManageEntity,String> {

    private final GoodsManageService goodsService;

    public OrderManageService(GoodsManageService goodsService) {
        this.goodsService = goodsService;
    }

    public Mono<Integer> getNumByGoodsBatch(String goodsBatch) {

        return goodsService.createQuery()
            .where(GoodsManageEntity::getGoodsBatch,goodsBatch)
            .fetch()
            .collectList()
            .flatMap(goods->
                         this.createQuery()
                             .where()
                             .in(OrderManageEntity::getGoodsId,goods.stream().map(GoodsManageEntity::getId).collect(Collectors.toList()))
                             .count()
            );
    }

}
