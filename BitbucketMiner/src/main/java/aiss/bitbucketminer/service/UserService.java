package aiss.bitbucketminer.service;

import aiss.bitbucketminer.model.bitbucketMiner.User.User;
import aiss.bitbucketminer.model.gitminer.GitminerUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@Service
public class UserService {

    @Autowired
    RestTemplate restTemplate;

    @Value("${github.token}")
    private String token;

    private final String uri = "https://api.bitbucket.org/2.0/users/";

    public GitminerUser parseUser(String uuid) {
        String baseUri = uri + uuid;
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + token);
            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<User> response = restTemplate.exchange(
                    baseUri,
                    HttpMethod.GET,
                    entity,
                    User.class
            );

            User user = response.getBody();

            if (user == null) {
                throw new RuntimeException("El usuario recibido es null para UUID: " + uuid);
            }

            return new GitminerUser(
                    user.getUuid(),
                    user.getDisplayName(),
                    user.getDisplayName(),
                    null, // no aparece en pojo
                    null  // no aparece en pojo
            );
        } catch (HttpClientErrorException | ResourceAccessException e) {
            throw e; // Manejado por BitbucketExceptionHandler
        } catch (Exception e) {
            throw new RuntimeException("Error inesperado al obtener el usuario desde Bitbucket", e);
        }
    }
}