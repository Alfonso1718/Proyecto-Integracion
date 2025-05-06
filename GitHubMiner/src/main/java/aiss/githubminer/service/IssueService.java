package aiss.githubminer.service;

import aiss.githubminer.model.gitminer.Issue;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IssueService {

    public List<Issue> getIssuesFromProject(String owner, String repo, int sinceIssues, int maxPages) {
        return null;
    }
}
