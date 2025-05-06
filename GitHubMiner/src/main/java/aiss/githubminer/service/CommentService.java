package aiss.githubminer.service;

import aiss.githubminer.controller.ProjectController;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    public void enviarDatosAGitMiner(ProjectController project) {
        // Aquí usas WebClient o RestTemplate para hacer un POST a GitMiner
    }
}
