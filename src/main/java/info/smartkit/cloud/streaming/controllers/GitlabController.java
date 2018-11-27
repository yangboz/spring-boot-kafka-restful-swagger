package info.smartkit.cloud.streaming.controllers;

import info.smartkit.cloud.streaming.dto.JsonString;
import io.swagger.annotations.ApiOperation;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Project;
import org.gitlab4j.api.webhook.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

import static org.gitlab4j.api.Constants.SortOrder.DESC;

@RestController
// @see: https://github.com/gmessner/gitlab4j-api
@RequestMapping(value = "v1/cloud/stream/gitlab")
public class GitlabController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GitlabController.class);

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
    JsonString events() throws GitLabApiException {
        GitLabApi gitLabApi = new GitLabApi("http://your.gitlab.server.com", "YOUR_PRIVATE_TOKEN");
        //Get a list of Events for the authenticated user
        Date after = new Date(0); // After Epoch
        Date before = new Date(); // Before now
        List<org.gitlab4j.api.models.Event> events = gitLabApi.getEventsApi().getAuthenticatedUserEvents(null, null, before, after, DESC);
        // Iterate through the pages and print out the name and description
        LOGGER.info("GitLabApiEvents:"+events.toString());
        return new JsonString(events.toString());
    }

}
