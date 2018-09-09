package com.tick42.quicksilver.config;

import com.tick42.quicksilver.services.base.GitHubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.FixedRateTask;
import org.springframework.scheduling.config.ScheduledTask;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

@Configuration
@ConfigurationProperties(prefix = "app.schedule")
public class ScheduleConfig implements SchedulingConfigurer {

    private String threadPrefix;

    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    @Autowired
    private GitHubService gitHubService;

    @Bean
    public ThreadPoolTaskScheduler createThreadPoolTaskScheduler() {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(10);
        threadPoolTaskScheduler.setThreadNamePrefix(threadPrefix);
        threadPoolTaskScheduler.initialize();
        return threadPoolTaskScheduler;
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        gitHubService.createScheduledTask(taskRegistrar, null);
    }

    public String getThreadPrefix() {
        return threadPrefix;
    }

    public void setThreadPrefix(String threadPrefix) {
        this.threadPrefix = threadPrefix;
    }
}