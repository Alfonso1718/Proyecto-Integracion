package bitbucketminer.serviceTest;

import aiss.bitbucketminer.model.gitminer.GitminerCommit;
import aiss.bitbucketminer.service.CommitService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class CommitTest {

    @Autowired
    CommitService commitService;

    @Test
    void getCommits() {
        List<GitminerCommit> commits = commitService.getCommits("gentlero", "bitbucket-api", 5, 2);
        System.out.println(commits);
    }
}
