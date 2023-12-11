package org.jetlinks.community.practice.manager.service;

import org.hswebframework.web.api.crud.entity.QueryParamEntity;
import org.hswebframework.web.crud.service.GenericReactiveCrudService;
import org.jetlinks.community.practice.manager.builder.GoodsBuilder;
import org.jetlinks.community.practice.manager.entity.GoodsManageEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author Wen-Tao
 * @since 2.2
 */
@Service
public class GoodsManageService extends GenericReactiveCrudService<GoodsManageEntity,String> {
//    private final QueryHelper queryHelper;

//    public GoodsManageService(QueryHelper queryHelper) {
//        this.queryHelper = queryHelper;
//    }


    public Mono<List<GoodsManageEntity>> getNoOrderGoodsList(QueryParamEntity queryParam) {
        return  createQuery().and(GoodsManageEntity::getGoodsName, GoodsBuilder.termType, queryParam.getTerms())
            .fetch()
            .collectList();

    }
}
