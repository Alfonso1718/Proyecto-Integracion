# Proyecto-Integracion
Proyecto de Integracion AISS GitMiner

gitminer

controller/
    ProjectController.java   ← POST /projects, GET /projects
    CommitController.java    ← GET /commits, /commits/{id}
    IssueController.java     ← GET /issues, /issues/{id}, ?status=open
    CommentController.java   ← GET /comments, /comments/{id}

model/
    Project.java
    Commit.java
    Issue.java
    Comment.java

repository/
    ProjectRepository.java
    CommitRepository.java
    IssueRepository.java
    CommentRepository.java
    
service/      ← (opcional, para lógica interna)
    ProjectService.java
     GitMinerApp.java
