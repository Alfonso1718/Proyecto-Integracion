package aiss.githubminer.controller;

import aiss.githubminer.model.gitminer.Project;
import aiss.githubminer.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/github")
public class ProjectController {

    @Autowired
    public ProjectService projectService;
    
    @Autowired
    public RestTemplate restTemplate;

    private final String gitminerUri = "http://localhost:8080/gitminer/projects";


    @PostMapping("/{owner}/{repoName}")
    public Project sendProject(
            @PathVariable String owner,
            @PathVariable String repoName,
            @RequestParam(defaultValue = "2") int sinceCommits,
            @RequestParam(defaultValue = "20") int sinceIssues,
            @RequestParam(defaultValue = "2") int maxPages ) {

        Project project = projectService.getProjectToBeSend(owner, repoName, sinceCommits, sinceIssues, maxPages);

        HttpEntity<Project> req = new HttpEntity<>(project);
        ResponseEntity<Project> res = restTemplate.exchange(gitminerUri, HttpMethod.POST, req, Project.class);

        return res.getBody();
    }

    @GetMapping("/{owner}/{repoName}")
    public Project getSendProject(
            @PathVariable String owner,
            @PathVariable String repoName,
            @RequestParam(defaultValue = "2") int sinceCommits,
            @RequestParam(defaultValue = "20") int sinceIssues,
            @RequestParam(defaultValue = "2") int maxPages ) {

        return projectService.getProjectToBeSend(owner, repoName, sinceCommits, sinceIssues, maxPages);
    }

}