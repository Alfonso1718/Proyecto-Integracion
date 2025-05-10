package bitbucketminer.serviceTest;

import aiss.bitbucketminer.model.bitbucketMiner.User.User;
import aiss.bitbucketminer.model.gitminer.GitminerUser;
import aiss.bitbucketminer.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserTest {

    @Autowired
    UserService userService;

    @Test
    void getUser() {
        GitminerUser usuario = userService.parseUser("a8b3fd12-d213-4ec4-b3b6-f26fd08d8b1d");
        System.out.println(usuario);
    }

}
