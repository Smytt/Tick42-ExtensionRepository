package com.tick42.quicksilver.repositories.base;

import com.tick42.quicksilver.models.GitHubModel;

import java.util.List;

public interface GitHubRepository {

    GitHubModel update(GitHubModel model);

    List<GitHubModel> findAll();

}
