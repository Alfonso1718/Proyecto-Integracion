package aiss.githubminer.service;

import aiss.githubminer.controller.ProjectController;
import aiss.githubminer.model.gitminer.Comment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    public void enviarDatosAGitMiner(ProjectController project) {
        // Aquí usas WebClient o RestTemplate para hacer un POST a GitMiner
    }

    public List<Comment> getCommentsFromIssue(Integer number) {
        return null;
    }
}
