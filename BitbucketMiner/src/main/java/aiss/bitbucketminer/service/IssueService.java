package aiss.bitbucketminer.service;

import aiss.bitbucketminer.model.bitbucketMiner.Issue.Issue;
import aiss.bitbucketminer.model.gitminer.GitminerIssue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
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

    private final String uri = "https://api.bitbucket.org/2.0/repositories/";

public List<GitminerIssue> getIssues(String workspace, String repoSlug, int nIssues, int maxPages) {
    int size = (nIssues > 0) ? nIssues : 5; // POR DEFECTO 5 POR PAGINA
    int pages = (maxPages > 0) ? maxPages : 2; // POR DEFECTO 2 POR PAGINA

    List<Issue> issuesToBeMapped = new ArrayList<>();

    for (int i = 1; i <= pages; i++) {
        String issueUri = uri + workspace + "/" + repoSlug + "/issues?state=all"
                + "&page=" + i
                + "&pagelen=" + size;

        ResponseEntity<Issue[]> res = restTemplate.exchange(
                issueUri,
                HttpMethod.GET,
                null,
                Issue[].class
        );

        Issue[] githubIssues = res.getBody();
        if (githubIssues == null || githubIssues.length == 0) {
            break; // No hay más issues
        }

        issuesToBeMapped.addAll(Arrays.asList(githubIssues));
    }

    return issuesToBeMapped.stream()
            .map(issue -> mapIssue(issue, workspace, repoSlug))
            .toList();
    }

    public GitminerIssue mapIssue(Issue githubIssue,String workspace, String repoSlug) {

        GitminerIssue issue = new GitminerIssue(
                githubIssue.getId().toString(),
                githubIssue.getTitle(),
                githubIssue.getContent().toString(),
                githubIssue.getState(),
                githubIssue.getCreatedOn(),
                githubIssue.getUpdatedOn(),
                githubIssue.getUpdatedOn(), // closedAt (asumimos ultima fecha de actualizacion como la de fin)
                Collections.singletonList(githubIssue.getState()), // asumimos que el estado es una etiqueta (labels en el modelo de gitminer)
                userService.parseUser(githubIssue.getReporter().getUuid(),
                                        githubIssue.getReporter().getDisplayName(), // displayName
                                        githubIssue.getReporter().getDisplayName(), // name pero ponemos lo mismo
                                        null,null), // no aparece en los pojo

                userService.parseUser(githubIssue.getReporter().getUuid(),
                                        githubIssue.getReporter().getDisplayName(), // displayName
                                        githubIssue.getReporter().getDisplayName(), // name pero ponemos lo mismo
                                        null,null), // no aparece en los pojo
                githubIssue.getVotes(),
                Collections.emptyList()
        );
        issue.setComments(commentService.getComments(githubIssue, workspace, repoSlug));
        return issue;
    }

}