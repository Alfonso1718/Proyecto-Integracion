
package aiss.bitbucketminer.model.bitbucketMiner.Commit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Commit {

    @JsonProperty("rendered")
    private Rendered rendered;
    @JsonProperty("hash")
    private String hash;
    @JsonProperty("repository")
    private Repository repository;
    @JsonProperty("links")
    private Links links;
    @JsonProperty("author")
    private Author author;
    @JsonProperty("summary")
    private Summary summary;
    @JsonProperty("participants")
    private List<Object> participants;
    @JsonProperty("parents")
    private List<Parent> parents;
    @JsonProperty("date")
    private String date;
    @JsonProperty("message")
    private String message;
    @JsonProperty("type")
    private String type;

    @JsonProperty("rendered")
    public Rendered getRendered() {
        return rendered;
    }

    @JsonProperty("rendered")
    public void setRendered(Rendered rendered) {
        this.rendered = rendered;
    }

    @JsonProperty("hash")
    public String getHash() {
        return hash;
    }

    @JsonProperty("hash")
    public void setHash(String hash) {
        this.hash = hash;
    }

    @JsonProperty("repository")
    public Repository getRepository() {
        return repository;
    }

    @JsonProperty("repository")
    public void setRepository(Repository repository) {
        this.repository = repository;
    }

    @JsonProperty("links")
    public Links getLinks() {
        return links;
    }

    @JsonProperty("links")
    public void setLinks(Links links) {
        this.links = links;
    }

    @JsonProperty("author")
    public Author getAuthor() {
        return author;
    }

    @JsonProperty("author")
    public void setAuthor(Author author) {
        this.author = author;
    }

    @JsonProperty("summary")
    public Summary getSummary() {
        return summary;
    }

    @JsonProperty("summary")
    public void setSummary(Summary summary) {
        this.summary = summary;
    }

    @JsonProperty("participants")
    public List<Object> getParticipants() {
        return participants;
    }

    @JsonProperty("participants")
    public void setParticipants(List<Object> participants) {
        this.participants = participants;
    }

    @JsonProperty("parents")
    public List<Parent> getParents() {
        return parents;
    }

    @JsonProperty("parents")
    public void setParents(List<Parent> parents) {
        this.parents = parents;
    }

    @JsonProperty("date")
    public String getDate() {
        return date;
    }

    @JsonProperty("date")
    public void setDate(String date) {
        this.date = date;
    }

    @JsonProperty("message")
    public String getMessage() {
        return message;
    }

    @JsonProperty("message")
    public void setMessage(String message) {
        this.message = message;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

}
