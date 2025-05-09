package aiss.bitbucketminer.service;

import org.springframework.stereotype.Service;

@Service
public class CommitService {

    @Autowired
    RestTemplate restTemplate;

    private final String uri = "https://api.bitbucket.org/2.0/repositories/";

    public List<Commit> getCommitsFromProject(String workspace, String repoSlug, int nCommits, int maxPages) {
        int size = nCommits;
        List<CommitsBitbucket> commitsToBeMapped = new ArrayList<>();

        CommitsBitbucket[] bitbucketCommits = null;
        for (int i = 1; i <= maxPages; i++) {
            String commitUri = uri + workspace + "/" + repoSlug + "/commits?page=" + i + "&pagelen=" + size;

            ResponseEntity<CommitsBitbucket[]> res = restTemplate.exchange(
                    commitUri,
                    HttpMethod.GET,
                    null,
                    CommitsBitbucket[].class
            );

            bitbucketCommits = res.getBody();
            if (bitbucketCommits == null || bitbucketCommits.length == 0) {
                break;
            }

            commitsToBeMapped.addAll(Arrays.asList(bitbucketCommits));
        }
        return commitsToBeMapped.stream()
                .map(this::mapCommit)
                .toList();
    }

    public Commit mapCommit(CommitsBitbucket bitbucketCommit) {
        Commit commit = new Commit(
                bitbucketCommit.getHash(),
                bitbucketCommit.getMessage(),
                "", // Detalle opcional
                bitbucketCommit.getAuthor().getUser().getDisplayName(),
                bitbucketCommit.getAuthor().getRaw(),
                bitbucketCommit.getDate(),
                bitbucketCommit.getLinks().getHtml().getHref()
        );

        return commit;
    }

}
