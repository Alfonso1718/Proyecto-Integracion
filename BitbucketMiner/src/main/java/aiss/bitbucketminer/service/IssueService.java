package aiss.bitbucketminer.service;

import aiss.bitbucketminer.model.bitbucketMiner.Issue.Issue;
import aiss.bitbucketminer.model.gitminer.GitminerIssue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class IssueService {

    @Autowired
    RestTemplate restTemplate;
    @Autowired
    CommentService commentService;
    @Autowired
    UserService userService;
    @Value("${github.token}")
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

                HttpHeaders headers = new HttpHeaders();
                headers.set("Authorization", "Bearer " + token);
                HttpEntity<String> entity = new HttpEntity<>(headers);

                ResponseEntity<Issue[]> res = restTemplate.exchange(
                        issueUri,
                        HttpMethod.GET,
                        entity,
                        Issue[].class
                );

                Issue[] githubIssues = res.getBody();
                if (githubIssues == null || githubIssues.length == 0) {
                    break;
                }

                issuesToBeMapped.addAll(Arrays.asList(githubIssues));
            }

            return issuesToBeMapped.stream()
                    .map(issue -> mapIssue(issue, workspace, repoSlug))
                    .toList();

        } catch (HttpClientErrorException | ResourceAccessException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error inesperado al obtener issues desde Bitbucket", e);
        }
    }

    public GitminerIssue mapIssue(Issue githubIssue, String workspace, String repoSlug) {
        GitminerIssue issue = new GitminerIssue(
                githubIssue.getId().toString(),
                githubIssue.getTitle(),
                githubIssue.getContent().toString(),
                githubIssue.getState(),
                githubIssue.getCreatedOn(),
                githubIssue.getUpdatedOn(),
                githubIssue.getUpdatedOn(),
                Collections.singletonList(githubIssue.getState()),
                userService.parseUser(githubIssue.getAssignee().getUuid()),
                userService.parseUser(githubIssue.getReporter().getUuid()),
                githubIssue.getVotes(),
                Collections.emptyList()
        );
        issue.setComments(commentService.getComments(workspace, repoSlug, githubIssue.getId()));
        return issue;
    }
}