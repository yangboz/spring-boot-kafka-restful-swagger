package info.smartkit.cloud.streaming.services;

import eu.bittrade.libs.steemj.SteemJ;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Event;
import org.gitlab4j.api.models.Issue;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface GitlabService {
    GitLabApi config(String uri, String privateToken);
    CompletableFuture<List<Event>> getEvents() throws InterruptedException, GitLabApiException;
    CompletableFuture<Issue> createIssue(Integer projectId, String title, String description) throws GitLabApiException;
}