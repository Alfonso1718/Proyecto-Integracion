package aiss.bitbucketminer.model.bitbucketMiner.Issue;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Content {

    @JsonProperty("raw")
    private String raw;
    @JsonProperty("markup")
    private String markup;
    @JsonProperty("html")
    private String html;

    @JsonProperty("raw")
    public String getRaw() {
        return raw;
    }

    @JsonProperty("raw")
    public void setRaw(String raw) {
        this.raw = raw;
    }

    @JsonProperty("markup")
    public String getMarkup() {
        return markup;
    }

    @JsonProperty("markup")
    public void setMarkup(String markup) {
        this.markup = markup;
    }

    @JsonProperty("html")
    public String getHtml() {
        return html;
    }

    @JsonProperty("html")
    public void setHtml(String html) {
        this.html = html;
    }

}
