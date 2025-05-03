package aiss.githubminer.controller;

import aiss.githubminer.service.GitMinerClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/apipath")
public class ProjectController {

    private final GitMinerClient gitMinerClient;

    public ProjectController(GitMinerClient gitMinerClient) {
        this.gitMinerClient = gitMinerClient;
    }

    @PostMapping("/{owner}/{repoName}")
    public ResponseEntity<Void> extraerYEnviarProyecto(
            @PathVariable String owner,
            @PathVariable String repoName,
            @RequestParam(defaultValue = "2") int sinceCommits,
            @RequestParam(defaultValue = "20") int sinceIssues,
            @RequestParam(defaultValue = "2") int maxPages
    ) {
        // Aquí iría la lógica para:
        // 1. Obtener datos de GitHub
        // 2. Transformarlos al modelo esperado
        // 3. Enviarlos a GitMiner
        gitMinerClient.enviarDatosAGitMiner(null);
        return ResponseEntity.ok().build();
    }


}