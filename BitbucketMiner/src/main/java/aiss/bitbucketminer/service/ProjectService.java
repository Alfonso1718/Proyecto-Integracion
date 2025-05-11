package aiss.bitbucketminer.service;

import aiss.bitbucketminer.model.bitbucketMiner.Project.Project;
import aiss.bitbucketminer.model.gitminer.GitminerProject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class ProjectService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    public CommitService commitService;

    @Autowired
    public IssueService issueService;

    @Value("${bitbucket.username}")
    private String username;

    @Value("${bitbucket.token}")
    private String token;

    public final String uri = "https://api.bitbucket.org/2.0/repositories/";


    public GitminerProject getEmptyProject(String workspace, String repoSlug) {
        String baseUri = uri + workspace + "/" + repoSlug;

        try {
            HttpHeaders headers = createAuthHeaders();
            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<Project> response = restTemplate.exchange(
                    baseUri,
                    HttpMethod.GET,
                    entity,
                    Project.class
            );

            Project project = response.getBody();

            if (project == null) {
                throw new RuntimeException("El proyecto recibido es null para: " + baseUri);
            }

            GitminerProject projectFinal = new GitminerProject(
                    project.getUuid(),
                    project.getName(),
                    project.getLinks().getHtml().toString()
            );
            projectFinal.setCommits(null);
            projectFinal.setIssues(null);

            return projectFinal;
        } catch (HttpClientErrorException | ResourceAccessException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error inesperado al obtener el proyecto desde Bitbucket", e);
        }
    }

    public GitminerProject buildProject(String workspace, String repoSlug, int nCommits, int nIssues, int maxPages) {
        GitminerProject project = getEmptyProject(workspace, repoSlug);
        project.setCommits(commitService.getCommits(workspace, repoSlug, nCommits, maxPages));
        project.setIssues(issueService.getIssues(workspace, repoSlug, nIssues, maxPages));
        return project;
    }

    private HttpHeaders createAuthHeaders() {
        String credentials = username + ":" + token;
        String encoded = Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic " + encoded);
        return headers;
    }
}
