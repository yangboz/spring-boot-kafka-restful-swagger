package info.smartkit.cloud.streaming.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steemj.exceptions.SteemResponseException;
import info.smartkit.cloud.streaming.dto.JsonString;
import info.smartkit.cloud.streaming.dto.SteemPost;
import info.smartkit.cloud.streaming.services.GitlabService;
import info.smartkit.cloud.streaming.services.SteemService;
import info.smartkit.cloud.streaming.utils.ServerResponseGitlabEvents;
import info.smartkit.cloud.streaming.utils.TimedResponse;
import io.swagger.annotations.ApiOperation;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;
import java.util.List;
import java.util.concurrent.CompletableFuture;

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
    @RequestMapping(method = RequestMethod.GET, value = "/config")
    @ApiOperation(value = "Response a string describing Gitlab config api.")
//	@ApiImplicitParams({@ApiImplicitParam(name="Authorization", value="Authorization DESCRIPTION")})
    public @ResponseBody
    JsonString config() throws GitLabApiException, SteemResponseException, SteemCommunicationException, SteemInvalidTransactionException, InterruptedException {
        //@see: https://docs.gitlab.com/ee/user/profile/personal_access_tokens.html
        GitLabApi gitLabConfig = gitlabService.config("://gitlab.com", "P2-gzAw_edSDuZbV4JVu");
        return new JsonString(gitLabConfig.getApiVersion().toString());
    }
    @RequestMapping(method = RequestMethod.GET, value = "/events")
    @ApiOperation(value = "Response a string describing Gitlab events api.")
//	@ApiImplicitParams({@ApiImplicitParam(name="Authorization", value="Authorization DESCRIPTION")})
    public @ResponseBody
    CompletableFuture<TimedResponse<List<org.gitlab4j.api.models.Event>>> events(boolean withSteemAwards) throws GitLabApiException, SteemResponseException, SteemCommunicationException, SteemInvalidTransactionException, InterruptedException {
        //
        if(withSteemAwards){

        }

        long start = System.currentTimeMillis();
        ServerResponseGitlabEvents response = new ServerResponseGitlabEvents(Thread.currentThread().getName());
        return gitlabService.getEvents()
                .thenApply(events -> {
                    response.setData(events);
                    response.setTimeMs(System.currentTimeMillis() - start);
                    response.setCompletingThread(Thread.currentThread().getName());
                    return response;
                });

//        CompletableFuture.allOf(gitLabConfig,steemJConfig).join();
    }

    private void steemPosting(List<org.gitlab4j.api.models.Event> events) throws SteemResponseException, SteemCommunicationException, SteemInvalidTransactionException {
        LOGGER.info(events.toString());
        // Iterate through the pages and print out the name and description
        ObjectMapper mapper = new ObjectMapper();
        String jsonEvents = null;
        try {
            jsonEvents = mapper.writeValueAsString(events);
            LOGGER.info("GitLabApiEvents JSON string:"+jsonEvents);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        for(org.gitlab4j.api.models.Event event: events) {
                SteemPost steemPost = new SteemPost();
                steemPost.setTitle(event.getProjectId().toString());
            //who_when_where_what_how_howmuch
                String contentFormat = who_when_where_what_how_howmuch(event.getAuthorUsername()
                        ,event.getCreatedAt().toString()
                        ,event.getProjectId().toString()
                        ,event.getActionName()
                        ,event.getAuthor().getAvatarUrl()
                        , "");
//                        ,event.getData().equals(null)?"":event.getData().toString()
//                        ,event.getNote().equals(null)?"":event.getNote().toString());
                steemPost.setContent(contentFormat);
                steemPost.setTags(new String[]{event.getActionName(),event.getAuthorUsername()});
//                if(event.getData()!= null) {
//                    steemPost.setContent(event.getData().toString());
//                }
//                steemPost.setTags(new String[]{event.getAuthorUsername(), event.getTargetTitle(), event.getActionName()});
                steemService.post(steemPost);
        }

    }
    private String who_when_where_what_how_howmuch(String who, String when, String where, String what, String how, String howmuch){
        Object[] args = {who, when, where, what, how, howmuch};
        MessageFormat fmt = new MessageFormat("{0} in the {1}, at {2} for {3} , is {4} {5}.");
        return fmt.format(args);
    }
}
