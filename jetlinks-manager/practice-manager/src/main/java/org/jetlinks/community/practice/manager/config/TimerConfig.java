package org.jetlinks.community.practice.manager.config;

import lombok.Getter;
import lombok.Setter;
import org.jetlinks.community.TimerSpec;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 定时器的配置类
 * @author Wen-Tao
 * @see
 * @since 2.2
 */
@Component
@ConfigurationProperties(prefix="timer")
@Getter
@Setter
public class TimerConfig {
    private TimerSpec timerSpec;

}
