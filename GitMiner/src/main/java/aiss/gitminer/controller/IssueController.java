package aiss.gitminer.controller;

import aiss.gitminer.model.Issue;
import aiss.gitminer.repository.IssueRepository;
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
    public List<Issue> getIssueByAuthor(@RequestParam(value = "authorId", required = false) String authorId,
                                        @RequestParam(value = "state", required = false) String state) {



        if (authorId != null) {
            return issueRepository.findAll().stream().filter(i -> i.getAuthor().getId().equals(authorId)).toList();
        } else if (state != null) {
            return issueRepository.findAll().stream().filter(i -> i.getState().equals(state)).toList();
        } else {
            return issueRepository.findAll();
        }


    }

    @GetMapping("/{id}")
    public Issue getIssueById(@PathVariable(value = "id") String id) { return issueRepository.findById(id).get(); }

}