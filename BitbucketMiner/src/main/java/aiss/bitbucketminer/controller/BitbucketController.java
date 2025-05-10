package aiss.bitbucketminer.controller;

import aiss.bitbucketminer.model.gitminer.GitminerProject;
import aiss.bitbucketminer.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/bitbucket")
public class BitbucketController {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    public ProjectService projectService;

    @Value("${github.token}")
    private String token;

    private final String gitminerUri = "http://localhost:8081/gitminer/projects";

    // OPERACION GET
    @GetMapping("/{workspace}/{repo_slug}")
    public GitminerProject fetchRawProject (
            @PathVariable String workspace,
            @PathVariable ("repo_slug") String repoSlug,
            @RequestParam (defaultValue = "5") int nCommits,
            @RequestParam (defaultValue = "5") int nIssues,
            @RequestParam (defaultValue = "2") int maxPages) {

        return projectService.buildProject(workspace, repoSlug, nCommits, nIssues, maxPages);
    }

    // OPERACION POST
    @PostMapping("/{workspace}/{repo_slug}")
    public GitminerProject fetchTransformAndSend(
            @PathVariable String workspace,
            @PathVariable ("repo_slug") String repoSlug,
            @RequestParam (defaultValue = "5") int nCommits,
            @RequestParam (defaultValue = "5") int nIssues,
            @RequestParam (defaultValue = "2") int maxPages) {

        // FETCH
        GitminerProject bitbucketProject = projectService.buildProject(workspace, repoSlug, nCommits, nIssues, maxPages);

        // HEADERS, TOKEN
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<?> req = new HttpEntity<>(bitbucketProject, headers);

        // SEND TO GITMINER
         ResponseEntity<GitminerProject> res = restTemplate.exchange(gitminerUri, HttpMethod.POST, req, GitminerProject.class);

        return res.getBody();
    }
}
