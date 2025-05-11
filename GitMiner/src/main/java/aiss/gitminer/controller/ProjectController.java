package aiss.gitminer.controller;

import aiss.gitminer.model.Project;
import aiss.gitminer.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/gitminer/projects")
public class ProjectController {

    private ProjectRepository projectRepository;

    @Autowired
    public ProjectController(ProjectRepository projectRepository) { this.projectRepository = projectRepository; }

    @GetMapping
    public List<Project> getProjects() {
        return projectRepository.findAll();
    }

    @GetMapping("/{id}")
    public Project getProject(@PathVariable(value = "id") String id) {
        return projectRepository.findById(id).orElse(null);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Project createProject(@RequestBody @Valid Project project) {

        return projectRepository.save(project);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateProject(@RequestBody @Valid Project updatedProject, @PathVariable String id) {

        Optional<Project> project = projectRepository.findById(id);

        Project _project = project.get();
        _project.setName(updatedProject.getName());
        _project.setWebUrl(updatedProject.getWebUrl());
        _project.setCommits(updatedProject.getCommits());
        _project.setIssues(updatedProject.getIssues());
        projectRepository.save(_project);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCommit(@PathVariable String id) {

        if(projectRepository.existsById(id)) {
            projectRepository.deleteById(id);
        }
    }

}
