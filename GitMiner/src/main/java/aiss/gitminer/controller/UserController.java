package aiss.gitminer.controller;

import aiss.gitminer.model.Project;
import aiss.gitminer.model.User;
import aiss.gitminer.repository.ProjectRepository;
import aiss.gitminer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/gitminer/users")
public class UserController {

    private UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) { this.userRepository = userRepository; }

    @GetMapping
    public List<User> getProjects() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public User getProject(@PathVariable(value = "id") String id) {
        return userRepository.findById(id).orElse(null);
    }

}
