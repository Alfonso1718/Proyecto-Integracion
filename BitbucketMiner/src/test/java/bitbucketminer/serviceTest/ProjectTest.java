package bitbucketminer.serviceTest;

import aiss.bitbucketminer.model.gitminer.GitminerProject;
import aiss.bitbucketminer.service.ProjectService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProjectTest {

    @Autowired
    ProjectService projectService;

    @Test
    void getProject() {
        GitminerProject project = projectService.getEmptyProject("gentlero", "bitbucket-api");
        System.out.println(project);
    }
}
