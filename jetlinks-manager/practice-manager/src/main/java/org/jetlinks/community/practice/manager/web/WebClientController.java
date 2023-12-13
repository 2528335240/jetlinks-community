package org.jetlinks.community.practice.manager.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hswebframework.web.api.crud.entity.QueryParamEntity;
import org.hswebframework.web.authorization.annotation.Resource;
import org.jetlinks.community.practice.manager.web.request.SaveUser;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Wen-Tao
 * @see
 * @since 2.2
 */
@RestController
@RequestMapping("/web")
@AllArgsConstructor
@Getter
@Resource(id="myWebClient",name = "订单管理控制器")
@Tag(name ="webClient模块")
public class WebClientController {



    @GetMapping("/get")
    @Operation(summary = "获取token")
    public Mono<Map> getToken( ){
        Map<String, Object> body = new HashMap<>();
        body.put("username","admin");
        body.put("password","admin");
        body.put("remember","true");
     return    WebClient.builder().build()
                 .post()
                 .uri("http://127.0.0.1:9000/api/authorize/login")
                        .accept(MediaType.APPLICATION_JSON)
                        .bodyValue(body)
                 .retrieve()
                 .bodyToMono(Map.class);

    }

    @GetMapping("/getDetailList")
    @Operation(summary = "获取用户分页列表")
    public Mono<Object> getDetailList(@RequestParam String token){
        QueryParamEntity queryParamEntity = new QueryParamEntity();
        queryParamEntity.setPageSize(12);
        return    WebClient.builder().build()
                           .post()
                           .uri("http://127.0.0.1:9000/api/user/detail/_query")
                           .accept(MediaType.APPLICATION_JSON)
                            .header("X-Access-Token",token)
                           .bodyValue(queryParamEntity)
                           .retrieve()
                           .bodyToMono(Object.class);
    }

    @PostMapping("/creatUser")
    @Operation(summary = "新增用户")
    public Mono<Object> creatUser(@RequestParam String token, @RequestBody SaveUser saveUser){

        return    WebClient.builder().build()
                           .post()
                           .uri("http://127.0.0.1:9000/api/user/detail/_create")
                           .accept(MediaType.APPLICATION_JSON)
                           .header("X-Access-Token",token)
                           .bodyValue(saveUser)
                           .retrieve()
                           .bodyToMono(Object.class);
    }


}
