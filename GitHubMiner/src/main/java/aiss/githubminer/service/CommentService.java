package aiss.githubminer.service;

import aiss.githubminer.model.githubMiner.comments.CommentsGithubMiner;
import aiss.githubminer.model.gitminer.Comment;
import aiss.githubminer.model.gitminer.UserGitMiner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    RestTemplate restTemplate;

    public final String uri = "https://api.github.com/repos/";

    public List<Comment> getCommentsFromIssue(String owner, String repo, Integer issueId) {

        String baseUri = uri + owner + "/" + repo + "/" + "issues/" + issueId + "/comments";
<<<<<<< Updated upstream
=======
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        HttpEntity<String> entity = new HttpEntity<>(headers);
<<<<<<< Updated upstream
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes

        ResponseEntity<CommentsGithubMiner[]> commentsGithubMiner = restTemplate.exchange(
                baseUri,
                HttpMethod.GET,
                null,
                CommentsGithubMiner[].class);

        CommentsGithubMiner[] res = commentsGithubMiner.getBody();
        if (res == null) return new ArrayList<>();

        List<CommentsGithubMiner> commentsToBeMapped = new ArrayList<>();

        commentsToBeMapped.addAll(Arrays.asList(res));

        return commentsToBeMapped.stream()
                .map(this :: mapComment)
                .toList();
    }

    public Comment mapComment(CommentsGithubMiner gitHubComment) {
        Comment res = new Comment(
                gitHubComment.getId().toString(),
                gitHubComment.getBody(),
                new UserGitMiner(gitHubComment.getUser().getId().toString(),
                        gitHubComment.getUser().getLogin(),
                        gitHubComment.getUser().getLogin(),
                        gitHubComment.getUser().getAvatarUrl(),
                        gitHubComment.getUser().getUrl()),
                gitHubComment.getCreatedAt(),
                gitHubComment.getUpdatedAt()
        );
        return res;
    }

}

