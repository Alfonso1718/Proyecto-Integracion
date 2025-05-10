package aiss.githubminer.service;

import aiss.githubminer.model.githubMiner.project.ProjectGithubMiner;
import aiss.githubminer.model.gitminer.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
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


    public final String uri = "https://api.github.com/repos/";
    @Value("${github.token}")
    private String token;

    public Project getProject(String owner, String repo) {

        String baseUri = uri + owner + "/" + repo;
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        HttpEntity<String> entity = new HttpEntity<>(headers);



        ResponseEntity<ProjectGithubMiner> response = restTemplate.exchange(baseUri, org.springframework.http.HttpMethod.GET, entity, ProjectGithubMiner.class);
        ProjectGithubMiner projectGithubMiner = response.getBody();


        assert projectGithubMiner != null;
        Project project = new Project(
            projectGithubMiner.getId().toString(), projectGithubMiner.getName(), projectGithubMiner.getHtmlUrl()
        );

        project.setCommits(null);
        project.setIssues(null);

        return project;
    }

    public Project getProjectToBeSend(String owner, String repoName, int sinceCommits, int sinceIssues, int maxPages) {

        Project project = getProject(owner, repoName);

        project.setCommits(commitService.getCommitsFromProject(owner, repoName, sinceCommits, maxPages));
        project.setIssues(issueService.getIssuesFromProject(owner, repoName, sinceIssues, maxPages));

        return project;
    }
}
