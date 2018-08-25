package com.tick42.quicksilver.services.base;

import com.tick42.quicksilver.models.GitHubModel;
import org.springframework.scheduling.annotation.Scheduled;

public interface GitHubService {
    void setRemoteDetails(GitHubModel gitHubModel);

    GitHubModel generateGitHub(String link);

    @Scheduled(fixedDelay = 360000) //todo -- one day?
    void updateExtensionDetails();
}
