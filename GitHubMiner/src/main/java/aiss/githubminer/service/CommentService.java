package aiss.githubminer.service;

import aiss.githubminer.controller.ProjectController;
import aiss.githubminer.model.githubMiner.issues.IssuesGithubMiner;
import aiss.githubminer.model.gitminer.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    IssueService issueService;
    @Autowired
    RestTemplate restTemplate;

    public List<Comment> getCommentsFromIssue(Integer issueId) {

    }
}
