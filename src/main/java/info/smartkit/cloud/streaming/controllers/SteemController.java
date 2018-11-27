package info.smartkit.cloud.streaming.controllers;

import eu.bittrade.libs.steemj.SteemJ;
import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;
import eu.bittrade.libs.steemj.exceptions.SteemResponseException;
import info.smartkit.cloud.streaming.dto.JsonString;
import io.swagger.annotations.ApiOperation;
import org.gitlab4j.api.GitLabApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
// @see: https://github.com/marvin-we/steem-java-api-wrapper
@RequestMapping(value = "v1/cloud/stream/steem")
public class SteemController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SteemController.class);

    @RequestMapping(method = RequestMethod.GET, value = "/info")
    @ApiOperation(value = "Response a string describing Steem info.")
//	@ApiImplicitParams({@ApiImplicitParam(name="Authorization", value="Authorization DESCRIPTION")})
    public @ResponseBody
    JsonString info() throws SteemResponseException, SteemCommunicationException {
        //@see: https://github.com/marvin-we/steem-java-api-wrapper/blob/master/sample/src/main/java/my/sample/project/SteemJUsageExample.java
        // Create a new apiWrapper with your config object.
        SteemJ steemJ = new SteemJ();
        return new JsonString(steemJ.getHardforkVersion().toString());
    }
}
