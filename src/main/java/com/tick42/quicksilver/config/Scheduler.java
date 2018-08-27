package com.tick42.quicksilver.config;

import org.springframework.scheduling.config.ScheduledTask;
import org.springframework.stereotype.Component;

@Component
public class Task {

    private ScheduledTask scheduledTask;

    public Task() {

    }

    public ScheduledTask getScheduledTask() {
        return scheduledTask;
    }

    public void setScheduledTask(ScheduledTask scheduledTask) {
        this.scheduledTask = scheduledTask;
    }
}
