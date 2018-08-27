package com.tick42.quicksilver.config;

import org.springframework.scheduling.config.ScheduledTask;
import org.springframework.stereotype.Component;

@Component
public class Scheduler {

    private ScheduledTask task;

    public Scheduler() {

    }

    public ScheduledTask getTask() {
        return task;
    }

    public void setTask(ScheduledTask task) {
        this.task = task;
    }
}
