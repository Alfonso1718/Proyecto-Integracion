package aiss.gitminer.controller;

import aiss.gitminer.model.Commit;
import aiss.gitminer.repository.CommitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/gitminer/commits")
public class CommitController {

    private CommitRepository commitRepository;

    @Autowired
    public CommitController(CommitRepository commitRepository) { this.commitRepository = commitRepository; }

    @GetMapping
    public List<Commit> getCommits() {
        return commitRepository.findAll();
    }

    @GetMapping("/{id}")
    public Commit getCommit(@PathVariable(value = "id") String id) { return commitRepository.findById(id).get(); }

}
