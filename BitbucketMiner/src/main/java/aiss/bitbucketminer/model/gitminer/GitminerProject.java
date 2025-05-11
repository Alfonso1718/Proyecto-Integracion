
package aiss.bitbucketminer.model.gitminer;

import java.util.ArrayList;
import java.util.List;

public class GitminerProject {

    public String id;
    public String name;
    public String web_url;
    private List<GitminerCommit> commits;
    private List<GitminerIssue> issues;

    // CONSTRUCTOR PARA PARSEO
    public GitminerProject(String id, String name, String web_url) {
        this.id = id;
        this.name = name;
        this.web_url = web_url;
        this.commits = new ArrayList<>();
        this.issues = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWeb_url() {
        return web_url;
    }

    public void setWeb_url(String web_url) {
        this.web_url = web_url;
    }

    public List<GitminerCommit> getCommits() {
        return commits;
    }

    public void setCommits(List<GitminerCommit> commits) {
        this.commits = commits;
    }

    public List<GitminerIssue> getIssues() {
        return issues;
    }

    public void setIssues(List<GitminerIssue> issues) {
        this.issues = issues;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(GitminerProject.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("id");
        sb.append('=');
        sb.append(((this.id == null)?"<null>":this.id));
        sb.append(',');
        sb.append("commits");
        sb.append('=');
        sb.append(((this.commits == null)?"<null>":this.commits));
        sb.append(',');
        sb.append("issues");
        sb.append('=');
        sb.append(((this.issues == null)?"<null>":this.issues));
        sb.append(',');

        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }
}
