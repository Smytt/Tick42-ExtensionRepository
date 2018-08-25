package com.tick42.quicksilver.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.impl.client.HttpClientBuilder;
import org.kohsuke.github.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping(value = "/test")
public class TestController {

    @GetMapping()
    public void test() {
        try {
            GitHub gitHub = GitHub.connect("Smytt", "5c1a77eec3047ae6b562a55a7c0e4d4735cb38ef");
            GHRepository repo = gitHub.getRepository("octocat/Hello-World");
            System.out.println(repo.getPullRequests(GHIssueState.OPEN).size());
            System.out.println(repo.getIssues(GHIssueState.OPEN).size());
            repo.listCommits().forEach(commit -> {
                try {
                    System.out.println(commit.getCommitDate());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
