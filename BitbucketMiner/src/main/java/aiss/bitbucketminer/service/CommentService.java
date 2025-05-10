package aiss.bitbucketminer.service;

import aiss.bitbucketminer.model.bitbucketMiner.Comment.Comment;
import aiss.bitbucketminer.model.bitbucketMiner.Issue.Issue;
import aiss.bitbucketminer.model.gitminer.GitminerComment;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommentService {

    @Autowired
    RestTemplate restTemplate;

    private final String uri = "https://api.bitbucket.org/2.0/repositories/";

    public List<GitminerComment> getComments(Issue githubIssue, String workspace, String repoSlug) {
        List<GitminerComment> gitminerComments = new ArrayList<>();

        String commentsUri = uri + workspace + "/" + repoSlug + "/issues/" + githubIssue.getId() + "/comments";

        Comment[] comments = restTemplate.getForObject(commentsUri, Comment[].class);

        if (comments == null) {
            return Collections.emptyList();
        }

        for (Comment comment : comments) {
            GitminerComment gitminerComment = new GitminerComment(
                    comment.getId().toString(),
                    comment.getContent().toString(), // BODY
                    (User) comment.getUser(), // AUTOR(? TODO
                    comment.getCreatedOn(),
                    comment.getCreatedOn()
            );
            gitminerComments.add(gitminerComment);
        }
        return gitminerComments;
    }

}
