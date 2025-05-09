package aiss.githubminer.service;

import aiss.githubminer.model.githubMiner.issues.AuthorIssue;
import aiss.githubminer.model.githubMiner.issues.UserIssueGithubMiner;
import aiss.githubminer.model.gitminer.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserService {

    @Autowired
    RestTemplate restTemplate;

    public final String uri = "https://api.github.com/users/";

    public User parseUser (AuthorIssue author){

        String baseUri = uri + author.getLogin();

        UserIssueGithubMiner userIssueGithubMiner = restTemplate.getForObject(baseUri, UserIssueGithubMiner.class);

        return new User(
            userIssueGithubMiner.getId().toString(),
            userIssueGithubMiner.getLogin(),
            userIssueGithubMiner.getName(),
            userIssueGithubMiner.getAvatarUrl(),
            userIssueGithubMiner.getUrl()
        );
    }
}
