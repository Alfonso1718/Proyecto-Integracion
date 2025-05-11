package aiss.bitbucketminer.controller;

import aiss.bitbucketminer.model.gitminer.GitminerProject;
import aiss.bitbucketminer.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@RestController
@RequestMapping("/bitbucket")
public class BitbucketController {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    public ProjectService projectService;

    @Value("${bitbucket.username}")
    private String username;

    @Value("${bitbucket.token}")
    private String token;

    private final String gitminerUri = "http://localhost:8080/gitminer/projects";

    // OPERACION GET
    @GetMapping("/{workspace}/{repo_slug}")
    public GitminerProject fetchRawProject(
            @PathVariable String workspace,
            @PathVariable("repo_slug") String repoSlug,
            @RequestParam(defaultValue = "5") int nCommits,
            @RequestParam(defaultValue = "5") int nIssues,
            @RequestParam(defaultValue = "2") int maxPages) {

        return projectService.buildProject(workspace, repoSlug, nCommits, nIssues, maxPages);
    }

    // OPERACION POST
    @PostMapping("/{workspace}/{repo_slug}")
    @ResponseStatus(HttpStatus.CREATED)
    public GitminerProject fetchTransformAndSend(
            @PathVariable String workspace,
            @PathVariable("repo_slug") String repoSlug,
            @RequestParam(defaultValue = "5") int nCommits,
            @RequestParam(defaultValue = "5") int nIssues,
            @RequestParam(defaultValue = "2") int maxPages) {

        // FETCH
        GitminerProject bitbucketProject = projectService.buildProject(workspace, repoSlug, nCommits, nIssues, maxPages);

        // HEADERS con autenticación Basic
        HttpHeaders headers = createAuthHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        //HttpEntity<?> req = new HttpEntity<>(bitbucketProject, headers);

        // SEND TO GITMINER
        //ResponseEntity<GitminerProject> res = restTemplate.exchange(
        //        gitminerUri, HttpMethod.POST, req, GitminerProject.class);

        HttpEntity<GitminerProject> req = new HttpEntity<>(bitbucketProject, headers);
        ResponseEntity<GitminerProject> res = restTemplate.exchange(gitminerUri, HttpMethod.POST, req, GitminerProject.class);

        return res.getBody();
    }

    private HttpHeaders createAuthHeaders() {
        String credentials = username + ":" + token;
        String encoded = Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic " + encoded);
        return headers;
    }
}

