package bitbucketminer;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
class BitbucketminerApplicationTests {

	@Test
	void contextLoads() {
		RestTemplate restTemplate = new RestTemplateBuilder().build();

	}

}
