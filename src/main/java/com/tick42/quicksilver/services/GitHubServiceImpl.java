package com.tick42.quicksilver.services;

import com.tick42.quicksilver.models.GitHubModel;
import com.tick42.quicksilver.repositories.base.GenericRepository;
import com.tick42.quicksilver.services.base.GitHubService;
import org.kohsuke.github.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class GitHubServiceImpl implements GitHubService{

    private final GenericRepository<GitHubModel> gitHubRepository;
    private final GitHub gitHub;

    @Autowired
    public GitHubServiceImpl(GenericRepository<GitHubModel> gitHubRepository, GitHub gitHub) {
        this.gitHubRepository = gitHubRepository;
        this.gitHub = gitHub;
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
                System.out.println(lastCommit);
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
    @Scheduled(fixedDelay = 5000) //todo -- one day?
    public void updateExtensionDetails() {
        List<GitHubModel> gitHubModels = gitHubRepository.findAll();
        gitHubModels.forEach(gitHub -> {
            System.out.println("updating... " + gitHub.getId());
            setRemoteDetails(gitHub);
            gitHubRepository.update(gitHub);
        });
    }
}
