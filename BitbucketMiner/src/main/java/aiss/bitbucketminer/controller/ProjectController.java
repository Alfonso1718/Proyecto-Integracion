package aiss.bitbucketminer.controller;

import aiss.bitbucketminer.service.CommitService;
import aiss.bitbucketminer.service.IssueService;
import aiss.bitbucketminer.service.ProjectService;
import aiss.bitbucketminer.model.gitminer.Project;
import aiss.bitbucketminer.util.Transform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/bitbucketminer")
public class Controller {

    private final RestTemplate restTemplate;
    private final ProjectService projectService;
    private final CommitService commitService;
    private final IssueService issueService;
    private final Transform transform;

    @Autowired
    public Controller(ProjectService projectService,
                      CommitService commitService,
                      IssueService issueService,
                      Transform transform,
                      RestTemplate restTemplate) {
        this.projectService = projectService;
        this.commitService = commitService;
        this.issueService = issueService;
        this.transform = transform;
        this.restTemplate = restTemplate;
    }

    @PostMapping("/{workspace}/{repo_slug}")
    public ResponseEntity<?> getProjectData(
            @PathVariable String workspace,
            @PathVariable("repo_slug") String repoSlug,
            @RequestParam(value = "nCommits", defaultValue = "5") int nCommits,
            @RequestParam(value = "nIssues", defaultValue = "5") int nIssues,
            @RequestParam(value = "maxPages", defaultValue = "2") int maxPages
    ) {
        // Obtener el proyecto con commits e issues usando los servicios
        Project project = projectService.buildProject(workspace, repoSlug, nCommits, nIssues, maxPages);

        // (Opcional) Transformar a otro formato si es necesario
        return ResponseEntity.ok(project);
    }
}

