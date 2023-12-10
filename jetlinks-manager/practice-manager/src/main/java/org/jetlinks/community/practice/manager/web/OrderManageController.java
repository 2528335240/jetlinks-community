package org.jetlinks.community.practice.manager.web;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hswebframework.web.authorization.annotation.Resource;
import org.hswebframework.web.crud.query.QueryHelper;
import org.hswebframework.web.crud.web.reactive.ReactiveServiceCrudController;
import org.jetlinks.community.practice.manager.entity.OrderManageEntity;
import org.jetlinks.community.practice.manager.service.OrderManageService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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
//   @GetMapping("/getListBySerialNumber")
//   @Operation(summary = "根据订单流水号模糊查询订单信息")
//    public Flux<OrderManageEntity> getList(String orderSerialNumber){
//       return queryHelper.select()
//    }


}
