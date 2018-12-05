package info.smartkit.cloud.streaming.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.GsonBuilder;
import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steemj.exceptions.SteemResponseException;
import info.smartkit.cloud.streaming.dto.JsonString;
import info.smartkit.cloud.streaming.dto.SteemPost;
import info.smartkit.cloud.streaming.services.GitlabService;
import info.smartkit.cloud.streaming.services.SteemService;
import io.swagger.annotations.ApiOperation;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Project;
import org.gitlab4j.api.webhook.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.gitlab4j.api.Constants.SortOrder.DESC;

@RestController
// @see: https://github.com/gmessner/gitlab4j-api
@RequestMapping(value = "v1/cloud/stream/gitlab")
public class GitlabController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GitlabController.class);

    @Autowired
    private SteemService steemService;

    @Autowired
    private GitlabService gitlabService;

    @RequestMapping(method = RequestMethod.GET, value = "/info")
    @ApiOperation(value = "Response a string describing Gitlab info.")
//	@ApiImplicitParams({@ApiImplicitParam(name="Authorization", value="Authorization DESCRIPTION")})
    public @ResponseBody
    JsonString info() {
        // Create a GitLabApi instance to communicate with your GitLab server using GitLab API V3
//        GitLabApi gitLabApi = new GitLabApi(GitLabApi.ApiVersion.V4, "http://your.gitlab.server.com", "YOUR_PRIVATE_TOKEN");
        return new JsonString(GitLabApi.ApiVersion.V4.toString());
    }
    @RequestMapping(method = RequestMethod.GET, value = "/events")
    @ApiOperation(value = "Response a string describing Gitlab events api.")
//	@ApiImplicitParams({@ApiImplicitParam(name="Authorization", value="Authorization DESCRIPTION")})
    public @ResponseBody
    JsonString events() throws GitLabApiException, SteemResponseException, SteemCommunicationException, SteemInvalidTransactionException {
        //@see: https://docs.gitlab.com/ee/user/profile/personal_access_tokens.html
//        GitLabApi gitLabApi = new GitLabApi("https://gitlab.com", "P2-gzAw_edSDuZbV4JVu");
        CompletableFuture<GitLabApi> gitLabApi = gitlabService.config("://gitlab.com", "P2-gzAw_edSDuZbV4JVu");
        gitLabApi.thenRunAsync(() -> {
            LOGGER.debug("gitlabService.config success.");
//            try {
//                CompletableFuture<List<org.gitlab4j.api.models.Event>> gitlabEvents = gitlabService.getEvents().thenAcceptAsync();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } catch (GitLabApiException e) {
//                e.printStackTrace();
//            }
        });
        //Get a list of Events for the authenticated user
//        Date after = new Date(0); // After Epoch
//        Date before = new Date(); // Before now
//        List<org.gitlab4j.api.models.Event> events = gitLabApi.getEventsApi().getAuthenticatedUserEvents(null, null, before, after, DESC);
//        steemService.config("lorechain",null);
//        for(org.gitlab4j.api.models.Event event: events){
//            SteemPost steemPost = new SteemPost();
//            steemPost.setTitle(event.getTitle());
//            steemPost.setContent(event.getData().toString());//WhoWhereWhatWhenHowHowmuch
//            steemPost.setTags(new String[]{event.getAuthorUsername(),event.getTargetTitle(),event.getActionName()});
//            steemService.post(steemPost);
//        }
//        // Iterate through the pages and print out the name and description
//        ObjectMapper mapper = new ObjectMapper();
//        String jsonEvents = null;
//        try {
//            jsonEvents = mapper.writeValueAsString(events);
//            LOGGER.info("GitLabApiEvents:"+jsonEvents);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }

//        return new JsonString(jsonEvents);
        return new JsonString("");
    }

}
