package aiss.bitbucketminer.service;

import aiss.bitbucketminer.model.bitbucketMiner.Project.Project;
import aiss.bitbucketminer.model.gitminer.GitminerProject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ProjectService {

    @Autowired
    RestTemplate restTemplate;
    @Autowired
    public CommitService commitService;
    @Autowired
    public IssueService issueService;

    public final String uri = "https://api.bitbucket.org/2.0/repositories/";

    public GitminerProject getEmptyProject(String workspace, String repoSlug) {

        String baseUri = uri + workspace + "/" + repoSlug;

        Project project = restTemplate.getForObject(baseUri, Project.class);

        assert project != null;
        GitminerProject projectFinal = new GitminerProject(project.getUuid(), project.getName(), project.getLinks().getHtml().toString());
        projectFinal.setCommits(null);
        projectFinal.setIssues(null);

        return projectFinal;
    }


    public GitminerProject buildProject(String workspace, String repoSlug, int nCommits, int nIssues, int maxPages) {

        GitminerProject project = getEmptyProject(workspace, repoSlug);

        project.setCommits(commitService.getCommits(workspace, repoSlug, nCommits, maxPages));
        project.setIssues(issueService.getIssues(workspace, repoSlug, nIssues, maxPages));

        return project;
    }

}