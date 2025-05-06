package aiss.githubminer.service;

import aiss.githubminer.model.githubMiner.issues.AuthorIssue;
import aiss.githubminer.model.gitminer.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public User parseUser (AuthorIssue author){

        return new User();
    }
}
