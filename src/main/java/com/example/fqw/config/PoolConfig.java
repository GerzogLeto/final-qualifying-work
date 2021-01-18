package com.example.fqw.config;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

//pool.config.corePoolSize=2
//pool.config.maxPoolSize=6
//pool.config.threadNamePrefix=taskExecutor
@ConfigurationProperties(prefix = "pool.config")
@Configuration
public class PoolConfig {
    @Setter
    private int corePoolSize;
    @Setter
    private int maxPoolSize;
    @Setter
    private String threadNamePrefix;

    @Bean
@Qualifier("executor")//    @Primary
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setThreadNamePrefix(threadNamePrefix);
        executor.initialize();
        return executor;
    }
}
