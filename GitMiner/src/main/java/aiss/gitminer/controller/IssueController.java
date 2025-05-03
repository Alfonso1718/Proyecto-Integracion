package aiss.gitminer.controller;

import aiss.gitminer.model.Issue;
import aiss.gitminer.repository.IssueRepository;
import aiss.gitminer.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gitminer/issues")
public class IssueController {

    private IssueRepository issueRepository;

    @Autowired
    public IssueController(IssueRepository issueRepository) { this.issueRepository = issueRepository; }

    @GetMapping
    public List<Issue> getIssueByAuthor(@RequestParam(value = "author", required = false) String author,
                                        @RequestParam(value = "state", required = false) String state) {

        List<Issue> issues = issueRepository.findAll();

        if (author != null) {
            issues.stream().filter(i -> i.getAuthor().equals(author)).toList();
        } else if (state != null) {
            issues.stream().filter(i -> i.getState().equals(state)).toList();
        }

        return issues;
    }

    @GetMapping("/{id}")
    public Issue getIssueById(@PathVariable(value = "id") String id) { return issueRepository.findById(id).get(); }

}
