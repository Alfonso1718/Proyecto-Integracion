package bitbucketminer.serviceTest;

import aiss.bitbucketminer.model.gitminer.GitminerComment;
import aiss.bitbucketminer.service.CommentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class CommentTest {

    @Autowired
    CommentService commentService;

    @Test
    void getCommentsOfIssue() {
        List<GitminerComment> comments = commentService.getComments("gentlero", "bitbucket-api", 81);
        System.out.println(comments.size());
    }
}
