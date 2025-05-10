package aiss.githubminer.service;

import aiss.githubminer.model.githubMiner.commits.CommitsGithubMiner;
import aiss.githubminer.model.gitminer.Commit;
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
public class CommitService {

    @Autowired
    RestTemplate restTemplate;

    private final String uri = "https://api.github.com/repos/";
    @Value("${github.token}")
    private String token;



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

            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(token);

            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<CommitsGithubMiner[]> res = restTemplate.exchange(
                    commitUri,
                    HttpMethod.GET,
                    entity,
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

        String[] mensaje = githubCommit.getCommit().getMessage().split("\n\n");

        String title = mensaje.length > 0 ? mensaje[0].strip() : "";
        String description = mensaje.length > 1 ? mensaje[1].strip() : "";

        Commit commit = new Commit(
                githubCommit.getNodeId().toString(),
                title,
                description,
                githubCommit.getCommit().getAuthor().getName(),
                githubCommit.getCommit().getAuthor().getEmail(),
                githubCommit.getCommit().getAuthor().getDate(),
                githubCommit.getUrl()
        );

        return commit;

    }

}
