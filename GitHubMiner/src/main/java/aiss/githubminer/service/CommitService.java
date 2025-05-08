package aiss.githubminer.service;

import aiss.githubminer.model.githubMiner.commits.CommitsGithubMiner;
import aiss.githubminer.model.gitminer.Commit;
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
public class CommitService {

    @Autowired
    RestTemplate restTemplate;
    @Autowired
    UserService userService;
    @Autowired
    CommentService commentService;

    private final String uri = "https://api.github.com/repos/";

    public List<Commit> getCommitsFromProject(String owner, String repo, int sinceCommits, int maxPages) {

        LocalDate date = LocalDate.now().minusDays(sinceCommits);
        int size = 30;
        List<CommitsGithubMiner> commitsToBeMapped = new ArrayList<>();

        CommitsGithubMiner[] githubCommits = null;
        for (int i = 0; i <= maxPages; i++) {
            String commitUri = uri + owner + "/" + repo + "/commits?state=all"
                    + "&since=" + date
                    + "&page=" + i
                    + "&per_page=" + size;

            ResponseEntity<CommitsGithubMiner[]> res = restTemplate.exchange(
                    commitUri,
                    HttpMethod.GET,
                    null,
                    CommitsGithubMiner[].class
            );

            githubCommits = res.getBody();
            if (githubCommits == null || githubCommits.length == 0) {
                break;
            }

            commitsToBeMapped.addAll(Arrays.asList(githubCommits));
        }

        return commitsToBeMapped.stream()
                .map(this :: mapCommit)
                .toList();
    }

    public Commit mapCommit(CommitsGithubMiner githubCommit) {
        Commit commit = new Commit(
                githubCommit,
                githubCommit,
                githubCommit,
                githubCommit,
                githubCommit,
                githubCommit,
                githubCommit
        );
        return commit;
    }
}
