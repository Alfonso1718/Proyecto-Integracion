package aiss.bitbucketminer.service;

import aiss.bitbucketminer.model.bitbucketMiner.Commit.Commit;
import aiss.bitbucketminer.model.gitminer.GitminerCommit;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CommitService {

    @Autowired
    RestTemplate restTemplate;

    @Value("${github.token}")
    private String token;

    private final String uri = "https://api.bitbucket.org/2.0/repositories/";

    public List<GitminerCommit> getCommits(String workspace, String repoSlug, int nCommits, int maxPages) {
        int size = (nCommits > 0) ? nCommits : 5;
        int pages = (maxPages > 0) ? maxPages : 2;
        List<Commit> commitsToBeMapped = new ArrayList<>();

        for (int i = 1; i <= pages; i++) {
            String commitUri = uri + workspace + "/" + repoSlug + "/commits?page=" + i + "&pagelen=" + size;
            try {
                HttpHeaders headers = new HttpHeaders();
                headers.set("Authorization", "Bearer " + token);
                HttpEntity<String> entity = new HttpEntity<>(headers);

                ResponseEntity<Commit[]> res = restTemplate.exchange(
                        commitUri,
                        HttpMethod.GET,
                        entity,
                        Commit[].class
                );

                Commit[] bitbucketCommits = res.getBody();
                if (bitbucketCommits == null || bitbucketCommits.length == 0) {
                    break;
                }

                commitsToBeMapped.addAll(Arrays.asList(bitbucketCommits));

            } catch (HttpClientErrorException | ResourceAccessException ex) {
                // Delegado automáticamente al manejador global (@ControllerAdvice)
                throw ex;
            } catch (Exception ex) {
                // Otras excepciones también serán capturadas por el handler global
                throw ex;
            }
        }

        return commitsToBeMapped.stream()
                .map(this::mapCommit)
                .toList();
    }

    public GitminerCommit mapCommit(Commit bitbucketCommit) {
        return new GitminerCommit(
                bitbucketCommit.getHash(),
                bitbucketCommit.getRepository().getName(), // Título
                bitbucketCommit.getMessage(),
                bitbucketCommit.getAuthor().getUser().getDisplayName(),
                bitbucketCommit.getAuthor().getRaw(), // Email
                bitbucketCommit.getDate(),
                bitbucketCommit.getLinks().getHtml().getHref()
        );
    }
}