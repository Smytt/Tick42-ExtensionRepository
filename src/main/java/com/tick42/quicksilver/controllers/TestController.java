package com.tick42.quicksilver.controllers;

import com.tick42.quicksilver.config.Scheduler;
import com.tick42.quicksilver.services.base.GitHubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.FixedRateTask;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.prefs.Preferences;

@RestController
@RequestMapping(value = "/test")
public class TestController {

    private Preferences prefs;

    private int refreshRate;

    private final GitHubService gitHubService;

    @Autowired
    public TestController(GitHubService gitHubService) {
        this.gitHubService = gitHubService;
    }

    @GetMapping()
    public void test(ScheduledTaskRegistrar taskRegistrar) {
    }

    @GetMapping("/{rate}/{wait}")
    public void test1(ScheduledTaskRegistrar taskRegistrar, @PathVariable(name = "rate") int rate, @PathVariable(name = "wait") int wait) {
        gitHubService.createScheduledTask(taskRegistrar, rate, wait);
    }
}
