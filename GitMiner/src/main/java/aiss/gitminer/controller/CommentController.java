package aiss.gitminer.controller;

import aiss.gitminer.model.Issue;
import aiss.gitminer.repository.CommentRepository;
import aiss.gitminer.repository.IssueRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import aiss.gitminer.model.Comment;

import java.util.List;

@RestController
@RequestMapping("/gitminer")
public class CommentController {

    private CommentRepository commentRepository;
    private IssueRepository issueRepository;

    public CommentController(CommentRepository commentRepository, IssueRepository issueRepository) {
        this.commentRepository = commentRepository;
        this.issueRepository = issueRepository;
    }

    @GetMapping("/comments")
    public List<Comment> getComments() { return commentRepository.findAll(); }

    @GetMapping("/comments/{id}")
    public Comment getComment(@PathVariable(name = "id") String id) { return commentRepository.findById(id).get(); }

    @GetMapping("/issues/{id}/comments")
    public List<Comment> getIssueComments(@PathVariable(name = "id") String id) {
        Issue issue = issueRepository.findById(id).get();
        return issue.getComments();
    }
}
