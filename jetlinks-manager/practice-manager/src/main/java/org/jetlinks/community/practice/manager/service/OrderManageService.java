package org.jetlinks.community.practice.manager.service;

import org.hswebframework.web.api.crud.entity.QueryParamEntity;
import org.hswebframework.web.bean.FastBeanCopier;
import org.hswebframework.web.crud.service.GenericReactiveCrudService;

import org.jetlinks.community.practice.manager.entity.GoodsManageEntity;
import org.jetlinks.community.practice.manager.entity.OrderManageEntity;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

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
    //查出所有订单，
    //查出ids，
    //筛选
    //返回次数
    public Flux<OrderManageEntity> getList(String orderSerialNumber){
      return  createQuery()
          .where()
          .$like$(OrderManageEntity::getOrderSerialNumber,orderSerialNumber)
          .fetch();


    }

//    public Mono<Integer> getCountOrderNumByGoodsBatch(String goodsBatch ){
//        goodsService.createQuery().where(GoodsManageEntity::getGoodsBatch,goodsBatch).fetch().collectList()
//        createQuery().fetch()
//            .filter( item -> item.getGoodsIds().retainAll( ))
//
//    }
}
