package com.tick42.quicksilver.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tick42.quicksilver.models.GitHub;
import com.tick42.quicksilver.repositories.base.GenericRepository;
import com.tick42.quicksilver.services.base.GitHubService;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class GitHubServiceImpl implements GitHubService{

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final GenericRepository<GitHub> gitHubRepository;

    @Autowired
    public GitHubServiceImpl(HttpClient httpClient, ObjectMapper objectMapper, GenericRepository<GitHub> gitHubRepository) {
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
        this.gitHubRepository = gitHubRepository;
    }

    @Override
    public void getDetails(GitHub gitHub) {
        loadPullRequests(gitHub);
        loadLastCommit(gitHub);
        loadOpenIssues(gitHub);
    }

    @Override
    public GitHub generateGitHub(String link) {
        String[] githubCred = link.replaceAll("https://github.com/", "").split("/");
        String user = githubCred[0];
        String repo = githubCred[1];
        GitHub gitHub = new GitHub(link, user, repo);
        getDetails(gitHub);
        return gitHub;
    }

    private void loadLastCommit(GitHub gitHub) {
        Date date = null;
        HttpGet request = new HttpGet("https://api.github.com/repos/" + gitHub.getUser() + "/" + gitHub.getRepo() + "/commits?per_page=1");
        request.addHeader("Authorization", "Bearer 5c1a77eec3047ae6b562a55a7c0e4d4735cb38ef ");
        try {
            HttpResponse response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                try (InputStream stream = entity.getContent()) {
                    BufferedReader reader =
                            new BufferedReader(new InputStreamReader(stream));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        Pattern pattern = Pattern.compile("\"author\":.*?\"date\":\"(.+?)T(.+?)Z\"}");
                        Matcher matcher = pattern.matcher(line);
                        System.out.println(line);
                        while (matcher.find()) {
                            String timeOfYear = matcher.group(1);
                            String timeOfDay = matcher.group(2);
                            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(timeOfYear + " " + timeOfDay);
                        }
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        gitHub.setLastCommit(date);
    }

    private void loadPullRequests(GitHub gitHub) {
        int pulls = 0;
        HttpHead request = new HttpHead("https://api.github.com/repos/" + gitHub.getUser() + "/" + gitHub.getRepo() + "/pulls?per_page=1");
        request.addHeader("Authorization", "Bearer 5c1a77eec3047ae6b562a55a7c0e4d4735cb38ef ");
        try {
            HttpResponse response = httpClient.execute(request);
            Header[] headers = response.getHeaders("link");

            if (headers.length == 0) {
                pulls = 0;
            }

            else {
                String value = headers[0].getElements()[1].getValue();
                Pattern pattern = Pattern.compile("&page=(\\d+)>");
                Matcher matcher = pattern.matcher(value);
                while (matcher.find()) {
                    pulls = Integer.parseInt(matcher.group(1));
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        gitHub.setPullRequests(pulls);
    }

    private void loadOpenIssues(GitHub gitHub) {
        int issues = 0;
        HttpGet request = new HttpGet("https://api.github.com/repos/" + gitHub.getUser() + "/" + gitHub.getRepo());
        request.addHeader("Authorization", "Bearer 5c1a77eec3047ae6b562a55a7c0e4d4735cb38ef ");
        try {
            HttpResponse response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                try (InputStream stream = entity.getContent()) {
                    BufferedReader reader =
                            new BufferedReader(new InputStreamReader(stream));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        HashMap<String, Object> test = objectMapper.readValue(line, HashMap.class);
                        issues = Integer.parseInt(test.get("open_issues_count").toString());
                    }
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        gitHub.setOpenIssues(issues);
    }

    @Override
    @Scheduled(fixedDelay = 360000) //todo -- one day?
    public void updateExtensionDetails() {
        List<GitHub> gitHubs = gitHubRepository.findAll();
        gitHubs.forEach(gitHub -> {
            System.out.println("updating... " + gitHub.getId());
            getDetails(gitHub);
            gitHubRepository.update(gitHub);
        });
    }
}
