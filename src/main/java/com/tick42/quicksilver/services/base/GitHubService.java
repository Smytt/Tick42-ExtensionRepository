package com.tick42.quicksilver.services.base;

import com.tick42.quicksilver.models.Extension;
import com.tick42.quicksilver.models.GitHub;
import org.springframework.scheduling.annotation.Scheduled;

public interface GitHubService {
    void getDetails(GitHub gitHub);

    GitHub generateGitHub(String link);

    @Scheduled(fixedDelay = 360000) //todo -- one day?
    void updateExtensionDetails();
}
