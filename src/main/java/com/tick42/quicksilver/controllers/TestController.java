package com.tick42.quicksilver.controllers;

import com.tick42.quicksilver.config.Scheduler;
import com.tick42.quicksilver.services.base.GitHubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.FixedRateTask;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/test")
public class TestController {

    private final GitHubService gitHubService;

    @Autowired
    public TestController(GitHubService gitHubService) {
        this.gitHubService = gitHubService;
    }

    @GetMapping()
    public void test(ScheduledTaskRegistrar taskRegistrar) {
        gitHubService.createScheduledTask(taskRegistrar);
    }
}
