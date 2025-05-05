
package aiss.githubminer.model.githubMiner.commits;

import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.*;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "author",
    "committer",
    "message",
    "tree",
    "url",
    "comment_count",
    "verification"
})
@Generated("jsonschema2pojo")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Commit {

    @JsonProperty("author")
    @Valid
    private Author author;
    @JsonProperty("committer")
    @Valid
    private Committer committer;
    @JsonProperty("message")
    private String message;

    @JsonProperty("author")
    public Author getAuthor() {
        return author;
    }

    @JsonProperty("author")
    public void setAuthor(Author author) {
        this.author = author;
    }

    @JsonProperty("committer")
    public Committer getCommitter() {
        return committer;
    }

    @JsonProperty("committer")
    public void setCommitter(Committer committer) {
        this.committer = committer;
    }

    @JsonProperty("message")
    public String getMessage() {
        return message;
    }

    @JsonProperty("message")
    public void setMessage(String message) {
        this.message = message;
    }

}
