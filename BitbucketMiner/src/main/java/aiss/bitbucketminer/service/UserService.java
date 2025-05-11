package aiss.bitbucketminer.service;

import aiss.bitbucketminer.model.bitbucketMiner.User.User;
import aiss.bitbucketminer.model.gitminer.GitminerUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;


@Service
public class UserService {

    @Autowired
    RestTemplate restTemplate;

    @Value("${bitbucket.username}")
    private String username;

    @Value("${bitbucket.token}")
    private String token;

    private final String uri = "https://api.bitbucket.org/2.0/users/{uuid}";

    public GitminerUser parseUser(String userId) {
        //String baseUri = uri + "{" + userId + "}";
        //String baseUri = uri + userId;
        try {
            HttpHeaders headers = createAuthHeaders();
            HttpEntity<String> entity = new HttpEntity<>(headers);

            // Solution 1: Use a map to provide variable values
            Map<String, String> uriVariables = new HashMap<>();
            uriVariables.put("uuid", userId);

            ResponseEntity<User> response = restTemplate.exchange(
                    uri,
                    HttpMethod.GET,
                    entity,
                    User.class,
                    uriVariables
            );

            User user = response.getBody();

            if (user == null) {
                throw new RuntimeException("El usuario recibido es null para UUID: " + userId);
            }

            return new GitminerUser(
                    user.getUuid(),
                    user.getDisplayName(),
                    user.getDisplayName(),
                    null, // Email no disponible
                    null  // Avatar URL no disponible -> se puede obtener usando el link avatar de los liks??
            );
        } catch (HttpClientErrorException | ResourceAccessException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error inesperado al obtener el usuario desde Bitbucket", e);
        }
    }

    private HttpHeaders createAuthHeaders() {
        String credentials = username + ":" + token;
        String encoded = Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic " + encoded);
        return headers;
    }
}
