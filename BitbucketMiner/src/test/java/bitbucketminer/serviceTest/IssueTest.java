package bitbucketminer.serviceTest;

import aiss.bitbucketminer.model.bitbucketMiner.Issue.Issue;
import aiss.bitbucketminer.model.gitminer.GitminerIssue;
import aiss.bitbucketminer.service.IssueService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class IssueTest {

    @Autowired
    IssueService issueService;

    @Test
    void getIssues() {
        List<GitminerIssue> issues = issueService.getIssues("gentlero", "bitbucket-api", 5,2);
        System.out.println(issues);
    }
}
