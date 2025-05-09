
package aiss.bitbucketminer.model.bitbucketMiner.Comment;

import aiss.bitbucketminer.model.bitbucketMiner.Issue.Link;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Links {

    @JsonProperty("self")
    private Link self;
    @JsonProperty("html")
    private Link html;
    @JsonProperty("code")
    private Link code;

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

    @JsonProperty("code")
    public Link getCode() {
        return code;
    }

    @JsonProperty("code")
    public void setCode(Link code) {
        this.code = code;
    }

}
