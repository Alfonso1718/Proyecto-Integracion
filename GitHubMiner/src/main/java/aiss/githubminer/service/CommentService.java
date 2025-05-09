package aiss.githubminer.service;

import aiss.githubminer.model.githubMiner.comments.CommentsGithubMiner;
import aiss.githubminer.model.gitminer.Comment;
import aiss.githubminer.model.gitminer.Issue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.List;

@Service
public class CommentService {

    @Autowired
    UserService userService;
    @Autowired
    RestTemplate restTemplate;

    public final String uri = "https://api.github.com/repos/";

    public List<Comment> getCommentsFromIssue(Integer issueId) {

        String baseUri = uri + issueId + "/comments";

        CommentsGithubMiner commentsGithubMiner = restTemplate.getForObject(baseUri, CommentsGithubMiner.class);

        // TODO
        // Tiene que recuperar la lista de comments (cambiar casi todo el metodo)

        return new Comment(
            commentsGithubMiner.getId().toString(),
            commentsGithubMiner.getBody(),
            userService.parseUser(commentsGithubMiner.getUser()),
            commentsGithubMiner.getCreatedAt(),
            commentsGithubMiner.getUpdatedAt()
        );
    }

//    public List<Comment> getCommentsFromIssue(String owner, String repo, int sinceIssues, int maxPages, Integer issueId) {
//
//        List<Issue> issues = issueService.getIssuesFromProject(owner, repo, sinceIssues, maxPages);
//
//        List<Comment> commentsToBeMapped = issues.stream()
//                .filter(e->e.getId().equals(issueId))
//                .findFirst()
//                .get()
//                .getComments();
//
//        return commentsToBeMapped.stream()
//                .map(this::mapComment)
//                .toList();
//    }
//
//    public Comment mapComment(Comment commentUnparsed) {
//        Comment comment = new Comment(
//
//        );
//        return comment;
//    }
}

