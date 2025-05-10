package aiss.bitbucketminer.service;

import aiss.bitbucketminer.model.bitbucketMiner.User.User;
import aiss.bitbucketminer.model.gitminer.GitminerUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserService {

    @Autowired
    RestTemplate restTemplate;
    private final String uri = "https://api.bitbucket.org/2.0/users/";

    public GitminerUser parseUser(String uuid) {

        String baseUri = uri + uuid;

        User usuario = restTemplate.getForObject(baseUri, User.class);

        // TODO EXCEPCION QUE USUARIO NO SEA NULL
        return new GitminerUser(
                usuario.getUuid(),
                usuario.getDisplayName(),
                usuario.getDisplayName(),
                null, // no aparece en pojo
                null // no aparece en pojo
        );
    }

}