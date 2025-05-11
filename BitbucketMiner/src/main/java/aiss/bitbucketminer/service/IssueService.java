package aiss.bitbucketminer.service;

import aiss.bitbucketminer.model.bitbucketMiner.Issue.Issue;
import aiss.bitbucketminer.model.bitbucketMiner.Issue.Values;
import aiss.bitbucketminer.model.bitbucketMiner.User.User;
import aiss.bitbucketminer.model.gitminer.GitminerIssue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.awt.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Base64;
import aiss.bitbucketminer.model.gitminer.Label;

@Service
public class IssueService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    CommentService commentService;

    @Autowired
    UserService userService;

    @Value("${bitbucket.username}")
    private String username;

    @Value("${bitbucket.token}")
    private String token;

    private final String uri = "https://api.bitbucket.org/2.0/repositories/";

    public List<GitminerIssue> getIssues(String workspace, String repoSlug, int nIssues, int maxPages) {
        int size = (nIssues > 0) ? nIssues : 5;
        int pages = (maxPages > 0) ? maxPages : 2;

        List<Issue> issuesToBeMapped = new ArrayList<>();

        try {
            for (int i = 1; i <= pages; i++) {
                String issueUri = uri + workspace + "/" + repoSlug + "/issues?state=all"
                        + "&page=" + i
                        + "&pagelen=" + size;

                HttpHeaders headers = createAuthHeaders();
                HttpEntity<String> entity = new HttpEntity<>(headers);

                ResponseEntity<Values> res = restTemplate.exchange(
                        issueUri,
                        HttpMethod.GET,
                        entity,
                        Values.class
                );

                List<Issue> bitbucketIssues = res.getBody().getValues();
                if (bitbucketIssues == null || bitbucketIssues.isEmpty()) {
                    break;
                }

                issuesToBeMapped.addAll(bitbucketIssues);
            }

            return issuesToBeMapped.stream()
                    .map(issue -> mapIssue(issue, workspace, repoSlug))
                    .collect(Collectors.toList());

        } catch (HttpClientErrorException | ResourceAccessException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace(); // imprime el error original en consola
            throw new RuntimeException("Error inesperado al obtener issues desde Bitbucket: " + e.getMessage(), e);
        }
    }

    private HttpHeaders createAuthHeaders() {
        String credentials = username + ":" + token;
        String encoded = Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic " + encoded);
        return headers;
    }

    public GitminerIssue mapIssue(Issue bitbucketIssue, String workspace, String repoSlug) {

        // CREAMOS LAS ETIQUETAS CON CLASES EXISTENTES
        List<String> labels = new ArrayList<>();
        if (bitbucketIssue.getKind() != null) labels.add(bitbucketIssue.getKind());
        if (bitbucketIssue.getPriority() != null) labels.add(bitbucketIssue.getPriority());
        if (bitbucketIssue.getComponent() != null && bitbucketIssue.getComponent().getType() != null)
            labels.add(bitbucketIssue.getComponent().getType());

        // COMPROBAMOS SI HAY REPORTER Y ASIGNADO
        User assignee = bitbucketIssue.getAssignee();
        User reporter = bitbucketIssue.getReporter();

        // Test Label
        Long id = 7777L;
        Label l = new Label(id, "Nombre", "ff22", "prueba");
        List<Label> lb = new ArrayList<>();


        GitminerIssue issue = new GitminerIssue(
                String.valueOf(bitbucketIssue.getId()),
                bitbucketIssue.getTitle(),
                bitbucketIssue.getContent().getRaw(),
                bitbucketIssue.getState(),
                bitbucketIssue.getCreatedOn(),
                bitbucketIssue.getUpdatedOn(),
                bitbucketIssue.getUpdatedOn(),
                lb,
                assignee != null ? userService.parseUser(bitbucketIssue.getAssignee().getUuid()) : null,
                reporter != null ? userService.parseUser(bitbucketIssue.getReporter().getUuid()) : null,
                bitbucketIssue.getVotes(),
                Collections.emptyList()
        );
        issue.setComments(commentService.getComments(workspace, repoSlug, bitbucketIssue.getId()));
        return issue;
    }
}
