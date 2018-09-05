package com.tick42.quicksilver.controllers;

import com.tick42.quicksilver.models.Spec.GitHubSettingSpec;
import com.tick42.quicksilver.services.base.GitHubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api")
public class GitHubController {

    private final GitHubService gitHubService;

    @Autowired
    public GitHubController(GitHubService gitHubService) {
        this.gitHubService = gitHubService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/auth/github")
    public void gitHubSetting(ScheduledTaskRegistrar taskRegistrar, @Valid @RequestBody GitHubSettingSpec gitHubSettingSpec) {
        gitHubService.createScheduledTask(taskRegistrar, gitHubSettingSpec);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity handleInvalidGitHubSettingSpecException(MethodArgumentNotValidException e) {
        e.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getBindingResult()
                        .getFieldErrors()
                        .stream()
                        .map(x -> x.getDefaultMessage())
                        .toArray());
    }
}
