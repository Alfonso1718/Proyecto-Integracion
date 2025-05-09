package aiss.bitbucketminer.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
public class AuthHeaderBuilder {
    @Value("${bitbucket.token}")

    private String token;

    public HttpEntity<String> buildAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer" + token);
        return new HttpEntity<>(headers);
    }
}
