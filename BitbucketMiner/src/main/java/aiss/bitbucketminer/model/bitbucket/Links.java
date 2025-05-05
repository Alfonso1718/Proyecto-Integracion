
package aiss.bitbucketminer.model.bitbucket;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Links {

    @JsonProperty("self")
    private Link self;
    @JsonProperty("html")
    private Link html;
    @JsonProperty("avatar")
    private Link avatar;
    @JsonProperty("pullrequests")
    private Link pullrequests;
    @JsonProperty("commits")
    private Link commits;
    @JsonProperty("forks")
    private Link forks;
    @JsonProperty("watchers")
    private Link watchers;
    @JsonProperty("downloads")
    private Link downloads;
    @JsonProperty("clone")
    private List<Link> clone;
    @JsonProperty("hooks")
    private Link hooks;

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

    @JsonProperty("avatar")
    public Link getAvatar() {
        return avatar;
    }

    @JsonProperty("avatar")
    public void setAvatar(Link avatar) {
        this.avatar = avatar;
    }

    @JsonProperty("pullrequests")
    public Link getPullrequests() {
        return pullrequests;
    }

    @JsonProperty("pullrequests")
    public void setPullrequests(Link pullrequests) {
        this.pullrequests = pullrequests;
    }

    @JsonProperty("commits")
    public Link getCommits() {
        return commits;
    }

    @JsonProperty("commits")
    public void setCommits(Link commits) {
        this.commits = commits;
    }

    @JsonProperty("forks")
    public Link getForks() {
        return forks;
    }

    @JsonProperty("forks")
    public void setForks(Link forks) {
        this.forks = forks;
    }

    @JsonProperty("watchers")
    public Link getWatchers() {
        return watchers;
    }

    @JsonProperty("watchers")
    public void setWatchers(Link watchers) {
        this.watchers = watchers;
    }

    @JsonProperty("downloads")
    public Link getDownloads() {
        return downloads;
    }

    @JsonProperty("downloads")
    public void setDownloads(Link downloads) {
        this.downloads = downloads;
    }

    @JsonProperty("clone")
    public List<Link> getClone() {
        return clone;
    }

    @JsonProperty("clone")
    public void setClone(List<Link> clone) {
        this.clone = clone;
    }

    @JsonProperty("hooks")
    public Link getHooks() {
        return hooks;
    }

    @JsonProperty("hooks")
    public void setHooks(Link hooks) {
        this.hooks = hooks;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Links.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("self");
        sb.append('=');
        sb.append(((this.self == null)?"<null>":this.self));
        sb.append(',');
        sb.append("html");
        sb.append('=');
        sb.append(((this.html == null)?"<null>":this.html));
        sb.append(',');
        sb.append("avatar");
        sb.append('=');
        sb.append(((this.avatar == null)?"<null>":this.avatar));
        sb.append(',');
        sb.append("pullrequests");
        sb.append('=');
        sb.append(((this.pullrequests == null)?"<null>":this.pullrequests));
        sb.append(',');
        sb.append("commits");
        sb.append('=');
        sb.append(((this.commits == null)?"<null>":this.commits));
        sb.append(',');
        sb.append("forks");
        sb.append('=');
        sb.append(((this.forks == null)?"<null>":this.forks));
        sb.append(',');
        sb.append("watchers");
        sb.append('=');
        sb.append(((this.watchers == null)?"<null>":this.watchers));
        sb.append(',');
        sb.append("downloads");
        sb.append('=');
        sb.append(((this.downloads == null)?"<null>":this.downloads));
        sb.append(',');
        sb.append("clone");
        sb.append('=');
        sb.append(((this.clone == null)?"<null>":this.clone));
        sb.append(',');
        sb.append("hooks");
        sb.append('=');
        sb.append(((this.hooks == null)?"<null>":this.hooks));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}
