package aiss.bitbucketminer.service;

import aiss.bitbucketminer.model.bitbucketMiner.Issue.Issue;
import aiss.bitbucketminer.model.gitminer.GitminerIssue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;
import java.util.Base64;

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

                ResponseEntity<Issue[]> res = restTemplate.exchange(
                        issueUri,
                        HttpMethod.GET,
                        entity,
                        Issue[].class
                );

                Issue[] bitbucketIssues = res.getBody();
                if (bitbucketIssues == null || bitbucketIssues.length == 0) {
                    break;
                }

                issuesToBeMapped.addAll(Arrays.asList(bitbucketIssues));
            }

            return issuesToBeMapped.stream()
                    .map(issue -> mapIssue(issue, workspace, repoSlug))
                    .collect(Collectors.toList());

        } catch (HttpClientErrorException | ResourceAccessException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error inesperado al obtener issues desde Bitbucket", e);
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
        GitminerIssue issue = new GitminerIssue(
                bitbucketIssue.getId().toString(),
                bitbucketIssue.getTitle(),
                bitbucketIssue.getContent().toString(),
                bitbucketIssue.getState(),
                bitbucketIssue.getCreatedOn(),
                bitbucketIssue.getUpdatedOn(),
                bitbucketIssue.getUpdatedOn(),
                Collections.singletonList(bitbucketIssue.getState()),
                userService.parseUser(bitbucketIssue.getAssignee().getUuid()),
                userService.parseUser(bitbucketIssue.getReporter().getUuid()),
                bitbucketIssue.getVotes(),
                Collections.emptyList()
        );
        issue.setComments(commentService.getComments(workspace, repoSlug, bitbucketIssue.getId()));
        return issue;
    }
}
