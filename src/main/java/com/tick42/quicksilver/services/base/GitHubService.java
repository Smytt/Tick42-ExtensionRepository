package com.tick42.quicksilver.services.base;

import com.tick42.quicksilver.models.GitHubModel;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

public interface GitHubService {
    void setRemoteDetails(GitHubModel gitHubModel);

    GitHubModel generateGitHub(String link);

    void updateExtensionDetails();


    void createScheduledTask(ScheduledTaskRegistrar taskRegistrar, Integer rate, Integer wait);
}
