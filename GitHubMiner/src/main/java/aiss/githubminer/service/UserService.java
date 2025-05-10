package aiss.githubminer.service;

import aiss.githubminer.model.githubMiner.comments.AuthorComment;
import aiss.githubminer.model.githubMiner.issues.AuthorIssue;
import aiss.githubminer.model.githubMiner.issues.UserAuthorGithubMiner;
import aiss.githubminer.model.gitminer.UserGitMiner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserService {

    @Autowired
    RestTemplate restTemplate;

    @Value("${github.token}")
    private String token;


    public final String uri = "https://api.github.com/users/";

    private HttpEntity<String> createAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        return new HttpEntity<>(headers);
    }



    public UserGitMiner getUser(String login) {

        String baseUri = uri + login;
        HttpEntity<String> entity = createAuthEntity();

        UserAuthorGithubMiner userAuthorGithubMiner = restTemplate.exchange(
                baseUri, org.springframework.http.HttpMethod.GET, entity, UserAuthorGithubMiner.class
        ).getBody();
        if (userAuthorGithubMiner == null) {
            return null;
        }


        return new UserGitMiner(
                userAuthorGithubMiner.getId().toString(),
                userAuthorGithubMiner.getLogin(),
                userAuthorGithubMiner.getName(),
                userAuthorGithubMiner.getAvatarUrl(),
                userAuthorGithubMiner.getUrl()
        );
    }

    public UserGitMiner parseUser (AuthorIssue author){
        if (author == null) {
            return null;
        }
        return getUser(author.getLogin());
    }



    public UserGitMiner parseUser(AuthorComment authorComment) {
        return getUser(authorComment.getLogin());
    }
}
