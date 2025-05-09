
package aiss.githubminer.model.githubMiner.commits;

import com.fasterxml.jackson.annotation.*;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;

@JsonInclude(JsonInclude.Include.NON_NULL)


@Generated("jsonschema2pojo")
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommitsGithubMiner {

    @JsonProperty("sha")
    private String sha;
    @JsonProperty("node_id")
    private String nodeId;
    @JsonProperty("commit")
    @Valid
    private CommitFromGithub commitFromGithub;
    @JsonProperty("url")
    private String url;


    @JsonProperty("sha")
    public String getSha() {
        return sha;
    }

    @JsonProperty("sha")
    public void setSha(String sha) {
        this.sha = sha;
    }

    @JsonProperty("node_id")
    public String getNodeId() {
        return nodeId;
    }

    @JsonProperty("node_id")
    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    @JsonProperty("commit")
    public CommitFromGithub getCommit() {
        return commitFromGithub;
    }

    @JsonProperty("commit")
    public void setCommit(CommitFromGithub commitFromGithub) {
        this.commitFromGithub = commitFromGithub;
    }

    @JsonProperty("url")
    public String getUrl() {
        return url;
    }

    @JsonProperty("url")
    public void setUrl(String url) {
        this.url = url;
    }


}
