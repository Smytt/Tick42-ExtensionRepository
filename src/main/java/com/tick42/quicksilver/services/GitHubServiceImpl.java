package com.tick42.quicksilver.services;

import com.tick42.quicksilver.config.Scheduler;
import com.tick42.quicksilver.exceptions.GitHubRepositoryException;
import com.tick42.quicksilver.models.GitHubModel;
import com.tick42.quicksilver.models.Spec.GitHubSettingSpec;
import com.tick42.quicksilver.repositories.base.GitHubRepository;
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
import java.util.prefs.Preferences;

@Service
public class GitHubServiceImpl implements GitHubService {

    private final GitHubRepository gitHubRepository;
    private final GitHub gitHub;
    private final Scheduler scheduler;
    private final ThreadPoolTaskScheduler threadPoolTaskScheduler;
    private Preferences prefs;


    @Autowired
    public GitHubServiceImpl(GitHubRepository gitHubRepository, Scheduler scheduler, ThreadPoolTaskScheduler threadPoolTaskScheduler) throws IOException {
        this.gitHubRepository = gitHubRepository;
        this.scheduler = scheduler;
        this.threadPoolTaskScheduler = threadPoolTaskScheduler;
        prefs = Preferences.userRoot().node(this.getClass().getName());
        this.gitHub = GitHub.connect(prefs.get("username", "-"), prefs.get("token", "-"));
    }

    @Override
    public void setRemoteDetails(GitHubModel gitHubModel) {
        try {
            GHRepository repo = null;
            try {
                repo = gitHub.getRepository(gitHubModel.getUser() + "/" + gitHubModel.getRepo());
            } catch (Exception e) {
                e.printStackTrace();
                throw new GitHubRepositoryException("Couldn't connect to " + gitHubModel.getLink() +". Check URL.");
            }
            try {
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
                gitHubModel.setLastSuccess(new Date());
            }
            catch (Exception e) {
                e.printStackTrace();
                throw new GitHubRepositoryException("Connected to " + gitHubModel.getLink() + " but couldn't fetch data.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            gitHubModel.setFailMessage(e.getMessage());
            gitHubModel.setLastFail(new Date());
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
            System.out.println("updating... " + gitHub.getRepo());
            setRemoteDetails(gitHub);
            gitHubRepository.update(gitHub);
        });
    }

    @Override
    public void createScheduledTask(ScheduledTaskRegistrar taskRegistrar, GitHubSettingSpec gitHubSettingSpec) {

        if (gitHubSettingSpec == null) return;

        Integer rate = gitHubSettingSpec.getRate();
        Integer wait = gitHubSettingSpec.getWait();
        String token = gitHubSettingSpec.getToken();
        String username = gitHubSettingSpec.getUsername();

        prefs.putInt("updateRate", rate);
        prefs.putInt("updateWait", wait);
        prefs.put("token", token);
        prefs.put("username", username);

        if (scheduler.getTask() != null) {
            scheduler.getTask().cancel();
        }

        FixedRateTask updateGitHubData = new FixedRateTask(new Runnable() {
            @Override
            public void run() {
                updateExtensionDetails();
            }
        }, prefs.getInt("updateRate", 1), prefs.getInt("updateWait", 1));

        taskRegistrar.setTaskScheduler(threadPoolTaskScheduler);
        scheduler.setTask(taskRegistrar.scheduleFixedRateTask(updateGitHubData));
    }

    @Override
    public GitHubSettingSpec getSettings() {
        GitHubSettingSpec currentSettings = new GitHubSettingSpec();
        currentSettings.setToken(prefs.get("token", "-"));
        currentSettings.setUsername(prefs.get("username", "-"));
        currentSettings.setRate(prefs.getInt("updateRate", 60000 * 60));
        currentSettings.setWait(prefs.getInt("updateWait", 60000 * 60));
        return currentSettings;
    }
}
