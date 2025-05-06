package aiss.githubminer.service;

import aiss.githubminer.model.gitminer.Commit;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommitService {

    public List<Commit> getCommitsFromProject(String owner, String repo, int sinceCommits, int maxPages) {
        return null;
    }
}
