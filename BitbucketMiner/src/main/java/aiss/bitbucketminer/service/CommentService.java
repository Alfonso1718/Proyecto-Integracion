package aiss.bitbucketminer.service;

import aiss.bitbucketminer.model.bitbucketMiner.Comment.Comment;
import aiss.bitbucketminer.model.bitbucketMiner.Comment.Values;
import aiss.bitbucketminer.model.gitminer.GitminerComment;
import aiss.bitbucketminer.model.gitminer.GitminerUser;
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
public class CommentService {

    @Autowired
    RestTemplate restTemplate;

    @Value("${bitbucket.username}")
    private String username;

    @Value("${bitbucket.token}")
    private String token;

    private final String uri = "https://api.bitbucket.org/2.0/repositories/";

    public List<GitminerComment> getComments(String workspace, String repoSlug, Integer githubIssueId) {
        String commentsUri = uri + workspace + "/" + repoSlug + "/issues/" + githubIssueId + "/comments";

        try {
            HttpHeaders headers = createAuthHeaders();
            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<Values> response = restTemplate.exchange(
                    commentsUri,
                    HttpMethod.GET,
                    entity,
                    Values.class
            );

            Values res = response.getBody();
            if (res == null) return new ArrayList<>();

            return res.getValues().stream()
                    .filter(comment -> comment.getContent().getRaw() != null)
                    .map(this::mapComment)
                    .collect(Collectors.toList());

        } catch (HttpClientErrorException | ResourceAccessException ex) {
            throw ex; // será gestionado por el handler global
        } catch (Exception ex) {
            throw ex; // también lo captura el handler genérico
        }
    }

    private HttpHeaders createAuthHeaders() {
        String credentials = username + ":" + token;
        String encoded = Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic " + encoded);
        return headers;
    }

    public GitminerComment mapComment(Comment bitbucketComment) {
        return new GitminerComment(
                bitbucketComment.getId().toString(),
                bitbucketComment.getContent().getRaw(),
                mapUser(),
                bitbucketComment.getCreatedOn(),
                bitbucketComment.getUpdatedOn()
        );
    }

    private GitminerUser mapUser() {
        return new GitminerUser(
            UUID.randomUUID().toString(),
            "user",
            "username",
            "",
            ""
        );
    }
}
