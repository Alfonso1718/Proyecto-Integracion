
package aiss.bitbucketminer.model.bitbucketMiner.Issue;

import aiss.githubminer.model.gitminer.Issue.Link;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Links {

    @JsonProperty("self")
    private Link self;
    @JsonProperty("html")
    private Link html;
    @JsonProperty("comments")
    private Link comments;
    @JsonProperty("attachments")
    private Link attachments;
    @JsonProperty("watch")
    private Link watch;
    @JsonProperty("vote")
    private Link vote;

    @JsonProperty("self")
    public Link getSelf() {
        return self;
    }

    @JsonProperty("self")
    public void setSelf(Link self) {
        this.self = self;
    }

    @JsonProperty("html")
    public Link getHtml() {
        return html;
    }

    @JsonProperty("html")
    public void setHtml(Link html) {
        this.html = html;
    }

    @JsonProperty("comments")
    public Link getComments() {
        return comments;
    }

    @JsonProperty("comments")
    public void setComments(Link comments) {
        this.comments = comments;
    }

    @JsonProperty("attachments")
    public Link getAttachments() {
        return attachments;
    }

    @JsonProperty("attachments")
    public void setAttachments(Link attachments) {
        this.attachments = attachments;
    }

    @JsonProperty("watch")
    public Link getWatch() {
        return watch;
    }

    @JsonProperty("watch")
    public void setWatch(Link watch) {
        this.watch = watch;
    }

    @JsonProperty("vote")
    public Link getVote() {
        return vote;
    }

    @JsonProperty("vote")
    public void setVote(Link vote) {
        this.vote = vote;
    }

}
