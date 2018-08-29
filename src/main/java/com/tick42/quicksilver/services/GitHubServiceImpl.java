package com.tick42.quicksilver.services;

import com.tick42.quicksilver.config.Scheduler;
import com.tick42.quicksilver.models.GitHubModel;
import com.tick42.quicksilver.models.Settings;
import com.tick42.quicksilver.repositories.base.GenericRepository;
import com.tick42.quicksilver.repositories.base.SettingsRepository;
import com.tick42.quicksilver.services.base.GitHubService;
import org.kohsuke.github.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.FixedRateTask;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class GitHubServiceImpl implements GitHubService {

    private final GenericRepository<GitHubModel> gitHubRepository;
    private final GitHub gitHub;
    private final Scheduler scheduler;
    private final ThreadPoolTaskScheduler threadPoolTaskScheduler;
    private final SettingsRepository settingsRepository;

    @Autowired
    public GitHubServiceImpl(GenericRepository<GitHubModel> gitHubRepository, GitHub gitHub, Scheduler scheduler, ThreadPoolTaskScheduler threadPoolTaskScheduler, SettingsRepository settingsRepository) {
        this.gitHubRepository = gitHubRepository;
        this.gitHub = gitHub;
        this.scheduler = scheduler;
        this.threadPoolTaskScheduler = threadPoolTaskScheduler;
        this.settingsRepository = settingsRepository;
    }

    @Override
    public void setRemoteDetails(GitHubModel gitHubModel) {
        try {
            GHRepository repo = gitHub.getRepository(gitHubModel.getUser() + "/" + gitHubModel.getRepo());
            int pulls = repo.getPullRequests(GHIssueState.OPEN).size();
            int issues = repo.getIssues(GHIssueState.OPEN).size() - pulls;
            Date lastCommit = null;
            List<GHCommit> commits = repo.listCommits().asList();
            if (commits.size() > 0) {
                lastCommit = commits.get(0).getCommitDate();
            }
            gitHubModel.setPullRequests(pulls);
            gitHubModel.setOpenIssues(issues);
            gitHubModel.setLastCommit(lastCommit);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public GitHubModel generateGitHub(String link) {
        String[] githubCred = link.replaceAll("https://github.com/", "").split("/");
        String user = githubCred[0];
        String repo = githubCred[1];
        GitHubModel gitHubModel = new GitHubModel(link, user, repo);
        setRemoteDetails(gitHubModel);
        return gitHubModel;
    }

    @Override
    public void updateExtensionDetails() {
        List<GitHubModel> gitHubModels = gitHubRepository.findAll();
        gitHubModels.forEach(gitHub -> {
            System.out.println("updating... " + gitHub.getId());
            setRemoteDetails(gitHub);
            gitHubRepository.update(gitHub);
        });
    }

    @Override
    public void createScheduledTask(ScheduledTaskRegistrar taskRegistrar) {

        if (scheduler.getTask() != null) {
            scheduler.getTask().cancel();
        }

        Settings settings = settingsRepository.findById(1);

        if (!settings.getEnableSync()) {
            return;
        }

        FixedRateTask updateGitHubData = new FixedRateTask(new Runnable() {
            @Override
            public void run() {
                updateExtensionDetails();
            }
        }, settings.getGitHubRefresh(), settings.getInitialWait());

        taskRegistrar.setTaskScheduler(threadPoolTaskScheduler);
        scheduler.setTask(taskRegistrar.scheduleFixedRateTask(updateGitHubData));
    }
}
