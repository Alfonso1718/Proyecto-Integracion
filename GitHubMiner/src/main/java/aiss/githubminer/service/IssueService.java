package aiss.githubminer.service;

import aiss.githubminer.model.githubMiner.issues.IssuesGithubMiner;
import aiss.githubminer.model.gitminer.Issue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class IssueService {

    @Autowired
    RestTemplate restTemplate;
    @Autowired
    CommentService commentService;
    @Autowired
    UserService userService;

    private final String uri = "https://api.github.com/repos/";

    public List<Issue> getIssuesFromProject(String owner, String repo, int sinceIssues, int maxPages) {

        LocalDate date = LocalDate.now().minusDays(sinceIssues);
        int size = 30;
        List<IssuesGithubMiner> issuesToBeMapped = new ArrayList<>();

        IssuesGithubMiner[] githubIssues = null;
        for (int i = 1; i <= maxPages; i++) {
            String issueUri = uri + owner + "/" + repo + "/issues?state=all"
                    + "&since=" + date
                    + "&page=" + i
                    + "&per_page=" + size;

            ResponseEntity<IssuesGithubMiner[]> res = restTemplate.exchange(
                    issueUri,
                    HttpMethod.GET,
                    null,
                    IssuesGithubMiner[].class
            );

            githubIssues = res.getBody();
            if (githubIssues == null || githubIssues.length == 0) {
                break; // Se coloca el break por si no hay mas resultados que no siga irerando
            }

            issuesToBeMapped.addAll(Arrays.asList(githubIssues));
        }

        return issuesToBeMapped.stream()
                .map(this::mapIssue)
                .toList();
    }

    public Issue mapIssue(IssuesGithubMiner githubIssue) {

        Issue issue = new Issue(
            githubIssue.getId().toString(),
            githubIssue.getTitle(),
            githubIssue.getBody(),
            githubIssue.getState(),
            githubIssue.getCreatedAt(),
            githubIssue.getUpdatedAt(),
            githubIssue.getClosedAt().toString(),
            githubIssue.getLabels().stream().map(x -> x.toString()).toList(),
            userService.parseUser(githubIssue.getUser()),
            userService.parseUser(githubIssue.getAssignee()),
            githubIssue.getReactions().getTotalCount(),
            null
        );
        issue.setComments(commentService.getCommentsFromIssue(githubIssue.getNumber()));
        return issue;
    }

}
