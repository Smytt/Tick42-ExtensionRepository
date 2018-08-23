package com.tick42.quicksilver.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tick42.quicksilver.models.Extension;
import com.tick42.quicksilver.services.base.GitHubService;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class GitHubServiceImpl implements GitHubService{

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    @Autowired
    public GitHubServiceImpl(HttpClient httpClient, ObjectMapper objectMapper) {
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
    }

    @Override
    public void getDetails(Extension extension) {
        String githubLink = extension.getGithub();
        String[] githubCred = githubLink.replaceAll("https://github.com/", "").split("/");
        String user = githubCred[0];
        String repo = githubCred[1];
        extension.setPullRequests(getPullRequests(user, repo));
        extension.setLastCommit(getLastCommit(user, repo));
        extension.setOpenIssues(getOpenIssues(user, repo));
    }

    private Date getLastCommit(String user, String repo) {
        Date date = null;
        HttpGet request = new HttpGet("https://api.github.com/repos/" + user + "/" + repo + "/commits?per_page=1");
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
        return date;
    }

    private int getPullRequests(String user, String repo) {
        int pulls = 0;
        HttpHead request = new HttpHead("https://api.github.com/repos/" + user + "/" + repo + "/pulls?per_page=1");
        try {
            HttpResponse response = httpClient.execute(request);
            Header[] headers = response.getHeaders("link");

            if (headers.length == 0) {
                System.out.println(0);
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

        return pulls;
    }

    private int getOpenIssues(String user, String repo) {
        int issues = 0;
        HttpGet request = new HttpGet("https://api.github.com/repos/" + user + "/" + repo);
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

        return issues;
    }
}
