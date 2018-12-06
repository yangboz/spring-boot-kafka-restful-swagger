package info.smartkit.cloud.streaming.utils;

import org.gitlab4j.api.models.Event;

import java.util.List;

public class ServerResponseGitlabEvents extends TimedResponse<List<Event>> {
    private String name;
    public ServerResponseGitlabEvents(String name) {
        this.name = name;
    }
}
