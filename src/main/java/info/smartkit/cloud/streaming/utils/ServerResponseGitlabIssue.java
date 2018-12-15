package info.smartkit.cloud.streaming.utils;

import org.gitlab4j.api.models.Event;
import org.gitlab4j.api.models.Issue;

import java.util.List;

public class ServerResponseGitlabIssue extends TimedResponse<Issue> {
    private String name;
    public ServerResponseGitlabIssue(String name) {
        this.name = name;
    }
}
