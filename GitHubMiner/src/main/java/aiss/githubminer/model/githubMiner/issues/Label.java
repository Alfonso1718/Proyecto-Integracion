package aiss.githubminer.model.githubMiner.issues;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Label {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("node_id")
    private String node_id;
    @JsonProperty("url")
    private String url;
    @JsonProperty("name")
    private String name;
    @JsonProperty("color")
    private String color;
    @JsonProperty("default")
    private String por_defecto;
    @JsonProperty("description")
    private String description;

    @JsonProperty("id")
    public Long getId() {
        return id;
    }
    @JsonProperty("id")
    public void setId(Long id) {
        this.id = id;
    }
    @JsonProperty("node_id")
    public String getNode_id() {
        return node_id;
    }
    @JsonProperty("node_id")
    public void setNode_id(String node_id) {
        this.node_id = node_id;
    }
    @JsonProperty("url")
    public String getUrl() {
        return url;
    }
    @JsonProperty("url")
    public void setUrl(String url) {
        this.url = url;
    }
    @JsonProperty("name")
    public String getName() {
        return name;
    }
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }
    @JsonProperty("color")
    public String getColor() {
        return color;
    }
    @JsonProperty("color")
    public void setColor(String color) {
        this.color = color;
    }
    @JsonProperty("default")
    public String getPor_defecto() {
        return por_defecto;
    }
    @JsonProperty("default")
    public void setPor_defecto(String por_defecto) {
        this.por_defecto = por_defecto;
    }
    @JsonProperty("description")
    public String getDescription() {
        return description;
    }
    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Label{" +
                "id=" + id +
                ", node_id='" + node_id + '\'' +
                ", url='" + url + '\'' +
                ", name='" + name + '\'' +
                ", color='" + color + '\'' +
                ", por_defecto='" + por_defecto + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

}

