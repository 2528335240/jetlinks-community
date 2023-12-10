package org.jetlinks.community.practice.manager.service;

import org.hswebframework.web.crud.service.GenericReactiveCrudService;
import org.jetlinks.community.practice.manager.entity.OrderManageEntity;
import org.springframework.stereotype.Service;

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



//    public Flux<OrderManageEntity> getList(String orderSerialNumber){
//      return  createQuery()
//          .where()
//          .$like$(OrderManageEntity::getOrderSerialNumber,orderSerialNumber)
//          .fetch();
//
//
//    }
//
//    public Mono<Integer> getCountOrderNumByGoodsBatch(String goodsBatch ){
//      return goodsService.createQuery()
//                   .where(GoodsManageEntity::getGoodsBatch,goodsBatch)
//          .fetch()
//          .map(GoodsManageEntity::g)
//          .flatMap(e ->
//             this.createQuery()
//                 .where(OrderManageEntity::getId,e)
//                 .fetch()
//               )
//          .distinct(OrderManageEntity::getId)
//          .collectList()
//          .map(List::size);
//
//    }
//
//    public Mono<Integer> generateOrder(OrderInfo orderInfo) {
//
//     return    goodsService.createQuery()
//                    .where()
//                    .in(GoodsManageEntity::getId,orderInfo.getGoodsIds())
//                    .fetch().collectList()
//                    .flatMap( list ->{
//                        OrderInfo copy1 = FastBeanCopier.copy(orderInfo, OrderInfo.class);
//                        copy1.setGoodsList(list);
//                        OrderManageEntity orderManage = FastBeanCopier.copy(copy1, OrderManageEntity.class);
//
//                        List<GoodsManageEntity> goodsManageEntityList = list.stream().peek(item -> item.setOrderId(orderManage.getId())).collect(Collectors.toList());
//                        orderManage.setGoodsList(goodsManageEntityList);
//
//                         goodsService.save(goodsManageEntityList).subscribe();
//                        return     this.insert(orderManage);
//                    });
////                    .map(list->{
////                        goodsService.save(list);
//////                        goodsService.save(list);
////                        orderInfo.setGoodsList(list);
//////                        OrderManageEntity orderManage = FastBeanCopier.copy(orderInfo, OrderManageEntity.class);
//////                       this.insert(orderManage);
////F
////                        return list;
////                    });
////
////         return insert(FastBeanCopier.copy(orderInfo,OrderManageEntity.class));
//
//
//
//    }
}
