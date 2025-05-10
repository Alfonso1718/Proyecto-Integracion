package aiss.githubminer.service;

import aiss.githubminer.model.githubMiner.issues.IssuesGithubMiner;
import aiss.githubminer.model.gitminer.Issue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
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
    @Value("${github.token}")
    private String token;



    public List<Issue> getIssuesFromProject(String owner, String repo, int sinceIssues, int maxPages) {

        LocalDate date = LocalDate.now().minusDays(sinceIssues);
        int size = 20;
        List<IssuesGithubMiner> issuesToBeMapped = new ArrayList<>();

        IssuesGithubMiner[] githubIssues = null;
        for (int i = 1; i <= maxPages; i++) {
            String issueUri = uri + owner + "/" + repo + "/issues?state=all"
                    + "&since=" + date
                    + "&page=" + i
                    + "&per_page=" + size;

            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + token);
            HttpEntity<String> entity = new HttpEntity<>(headers);



            ResponseEntity<IssuesGithubMiner[]> res = restTemplate.exchange(
                    issueUri,
                    HttpMethod.GET,
                    entity,
                    IssuesGithubMiner[].class
            );

            githubIssues = res.getBody();
            if (githubIssues == null || githubIssues.length == 0) {
                break; // Se coloca el break por si no hay mas resultados que no siga irerando
            }

            issuesToBeMapped.addAll(Arrays.asList(githubIssues));
        }

        return issuesToBeMapped.stream()
                .map(e -> mapIssue(owner, repo, e))
                .toList();
    }

    public Issue mapIssue(String owner, String repo, IssuesGithubMiner githubIssue) {

        Issue issue = new Issue(
            githubIssue.getId().toString(),
            githubIssue.getTitle(),
            githubIssue.getBody(),
            githubIssue.getState(),
            githubIssue.getCreatedAt(),
            githubIssue.getUpdatedAt(), githubIssue.getClosedAt() != null ? githubIssue.getClosedAt().toString() : null,
            githubIssue.getLabels(),
            userService.parseUser(githubIssue.getUser()),
            userService.parseUser(githubIssue.getAssignee()),
            githubIssue.getReactions().getTotalCount(),
            null);
        issue.setComments(
                commentService.getCommentsFromIssue(owner, repo, githubIssue.getNumber()) != null
                        ? commentService.getCommentsFromIssue(owner, repo, githubIssue.getNumber())
                        : new ArrayList<>()

        );


        return issue;
    }

}
