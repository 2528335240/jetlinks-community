package org.jetlinks.community.practice.manager.service;

import org.hswebframework.web.crud.service.GenericReactiveCrudService;
import org.jetlinks.community.practice.manager.entity.GoodsManageEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Wen-Tao
 * @since 2.2
 */
@Service
public class GoodsManageService extends GenericReactiveCrudService<GoodsManageEntity,String> {

}
