package org.jetlinks.community.practice.manager.timers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetlinks.community.practice.manager.config.TimerConfig;
import org.jetlinks.community.practice.manager.entity.OrderManageEntity;
import org.jetlinks.community.practice.manager.service.OrderManageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;

import javax.annotation.PreDestroy;
import java.time.Duration;


@Component
@RequiredArgsConstructor
@Slf4j
public class OrderTimers implements CommandLineRunner {

    private Disposable disposable;

    private final OrderManageService orderManageService;

    private final   TimerConfig timerConfig;


    private Mono<Integer> delete(Duration duration){
        return orderManageService
            .createDelete()
            .where()
            .lt(OrderManageEntity::getCreateTime,System.currentTimeMillis()-duration.toMillis())
            .execute();
    }


    @PreDestroy
    public Mono<Void> shutdown(){
        //停止定时任务
        if(disposable!=null){
            disposable.dispose();
        }
     return null;
    }





    @Override
    public void run(String... args) throws Exception {

        disposable= timerConfig.getTimerSpec().flux().concatMap(item -> {
            log.info("-----------定时器执行了----------------");
            log.info("-----------定时器执行cron:{}",timerConfig.getTimerSpec().getCron());

            return this.delete(Duration.ofDays(30));
        }).subscribe();
//        disposable=  Flux.interval(Duration.ZERO, Duration.ofSeconds(3))
//            .onBackpressureDrop()
//            .concatMap(item -> {
//                log.info("-----------定时器执行了----------------");
//                return Flux.empty();
//            }).subscribe();
    }



}
