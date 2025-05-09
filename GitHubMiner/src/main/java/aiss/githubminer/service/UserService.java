package aiss.githubminer.service;

import aiss.githubminer.model.githubMiner.comments.AuthorComment;
import aiss.githubminer.model.githubMiner.issues.AuthorIssue;
import aiss.githubminer.model.githubMiner.issues.UserAuthorGithubMiner;
import aiss.githubminer.model.gitminer.UserGitMiner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserService {

    @Autowired
    RestTemplate restTemplate;

    public final String uri = "https://api.github.com/users/";

    public UserGitMiner getUser(String login) {

        String baseUri = uri + login;

        UserAuthorGithubMiner userAuthorGithubMiner = restTemplate.getForObject(baseUri, UserAuthorGithubMiner.class);

        return new UserGitMiner(
                userAuthorGithubMiner.getId().toString(),
                userAuthorGithubMiner.getLogin(),
                userAuthorGithubMiner.getName(),
                userAuthorGithubMiner.getAvatarUrl(),
                userAuthorGithubMiner.getUrl()
        );
    }

    public UserGitMiner parseUser (AuthorIssue author){
        return getUser(author.getLogin());
    }

    public UserGitMiner parseUser(AuthorComment authorComment) {
        return getUser(authorComment.getLogin());
    }
}
