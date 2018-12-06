package info.smartkit.cloud.streaming.services;

import eu.bittrade.libs.steemj.SteemJ;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Event;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.gitlab4j.api.Constants.SortOrder.DESC;

@Service
public class GitlabServiceImpl implements GitlabService {

    public GitLabApi getGitLabApi() {
        return gitLabApi;
    }

    private GitLabApi gitLabApi;

    @Override
//    @Async("asyncExecutor")
    public GitLabApi config(String uri, String privateToken) {
        this.gitLabApi = new GitLabApi("https://gitlab.com", "P2-gzAw_edSDuZbV4JVu");
//        return CompletableFuture.completedFuture(gitLabApi);
        return gitLabApi;
    }


    @Override
    @Async("asyncExecutor")
    public CompletableFuture<List<Event>> getEvents() throws GitLabApiException {
        //Get a list of Events for the authenticated user
        Date after = new Date(0); // After Epoch
        Date before = new Date(); // Before now
        List<org.gitlab4j.api.models.Event> events = gitLabApi.getEventsApi().getAuthenticatedUserEvents(null, null, before, after, DESC);
        return CompletableFuture.completedFuture(events);
    }
}
