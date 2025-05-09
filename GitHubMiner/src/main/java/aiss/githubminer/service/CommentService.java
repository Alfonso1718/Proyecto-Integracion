package aiss.githubminer.service;

import aiss.githubminer.model.githubMiner.comments.CommentsGithubMiner;
import aiss.githubminer.model.gitminer.Comment;
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
}
