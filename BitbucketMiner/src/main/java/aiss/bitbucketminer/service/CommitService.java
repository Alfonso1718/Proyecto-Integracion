package aiss.bitbucketminer.service;

import aiss.bitbucketminer.model.bitbucketMiner.Commit.Commit;
import aiss.bitbucketminer.model.bitbucketMiner.Commit.Values;
import aiss.bitbucketminer.model.gitminer.GitminerCommit;
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
public class CommitService {

    @Autowired
    RestTemplate restTemplate;

    @Value("${bitbucket.username}")
    private String username;

    @Value("${bitbucket.token}")
    private String token;

    private final String uri = "https://api.bitbucket.org/2.0/repositories/";

    public List<GitminerCommit> getCommits(String workspace, String repoSlug, int nCommits, int maxPages) {
        int size = (nCommits > 0) ? nCommits : 5;
        int pages = (maxPages > 0) ? maxPages : 2;
        List<Commit> commitsToBeMapped = new ArrayList<>();

        for (int i = 1; i <= pages; i++) {
            String commitUri = uri + workspace + "/" + repoSlug + "/commits?page=" + i + "&pagelen=" + size;
            try {
                HttpHeaders headers = createAuthHeaders();
                HttpEntity<String> entity = new HttpEntity<>(headers);

                ResponseEntity<Values> res = restTemplate.exchange(
                        commitUri,
                        HttpMethod.GET,
                        entity,
                        Values.class
                );

                List<Commit> bitbucketCommits = res.getBody().getValues();
                if (bitbucketCommits == null || bitbucketCommits.isEmpty()) {
                    break;
                }

                commitsToBeMapped.addAll(bitbucketCommits);

            } catch (HttpClientErrorException | ResourceAccessException ex) {
                throw ex;
            } catch (Exception ex) {
                throw ex;
            }
        }

        return commitsToBeMapped.stream()
                .map(this::mapCommit)
                .collect(Collectors.toList());
    }

    private HttpHeaders createAuthHeaders() {
        String credentials = username + ":" + token;
        String encoded = Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic " + encoded);
        return headers;
    }

    public GitminerCommit mapCommit(Commit bitbucketCommit) {
        return new GitminerCommit(
                bitbucketCommit.getHash(),
                bitbucketCommit.getRepository().getName(),
                bitbucketCommit.getMessage(),
                bitbucketCommit.getAuthor().getUser().getDisplayName(),
                bitbucketCommit.getAuthor().getRaw(),
                bitbucketCommit.getDate(),
                bitbucketCommit.getLinks().getHtml().getHref()
        );
    }
}
