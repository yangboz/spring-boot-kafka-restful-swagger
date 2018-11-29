package info.smartkit.cloud.streaming.controllers;

import eu.bittrade.libs.steemj.SteemJ;
import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.base.models.Asset;
import eu.bittrade.libs.steemj.base.models.Permlink;
import eu.bittrade.libs.steemj.base.models.operations.CommentOperation;
import eu.bittrade.libs.steemj.enums.AssetSymbolType;
import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steemj.exceptions.SteemResponseException;
import info.smartkit.cloud.streaming.dto.JsonString;
import info.smartkit.cloud.streaming.dto.KafkaMessage;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.gitlab4j.api.GitLabApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;

@RestController
// @see: https://github.com/marvin-we/steem-java-api-wrapper
@RequestMapping(value = "v1/cloud/stream/steem")
public class SteemController {

    private static org.apache.logging.log4j.Logger LOG = LogManager.getLogger(SteemController.class);

   private SteemJ steemJ;

    @RequestMapping(method = RequestMethod.GET, value = "/info")
    @ApiOperation(value = "Response a string describing Steem info.")
//	@ApiImplicitParams({@ApiImplicitParam(name="Authorization", value="Authorization DESCRIPTION")})
    public @ResponseBody
    JsonString info() throws SteemResponseException, SteemCommunicationException {
        //@see: https://github.com/marvin-we/steem-java-api-wrapper/blob/master/sample/src/main/java/my/sample/project/SteemJUsageExample.java
        // Create a new apiWrapper with your config object.
        steemJ = new SteemJ();
        return new JsonString(steemJ.getHardforkVersion().toString());
    }

    @RequestMapping(value = "/post", method = RequestMethod.POST)
    @ApiOperation(httpMethod = "POST", value = "Response a string describing if the Steem posting is successfully triggled.")
    public void post(@RequestBody @Valid KafkaMessage kafkaMessage) throws SteemResponseException, SteemCommunicationException, SteemInvalidTransactionException {
        //
        /*
         * Write a new post.
         *
         * Title = "Test of SteemJ 0.4.0" Content =
         * "Test using SteemJ 0. ..... " Tags = "test", "dontvote"
         *
         */
        CommentOperation myNewPost = steemJ.createPost("Test of SteemJ 0.4.0",
                "Test using SteemJ 0.4.0 by @dez1337 with a link to "
                        + "https://github.com/marvin-we/steem-java-api-wrapper "
                        + "and an image ![SteemJV2Logo](https://imgur.com/bIhZlYT.png).",
                new String[] { "test", "dontvote" });
        LOG.info(
                "SteemJ has generated some additional values for my new post. One good example is the permlink {} that I may need later on.",
                myNewPost.getPermlink().getLink());
        // Let the default account ("steemj") resteem a post".
//        steemJ.reblog(new AccountName("dez1337"),
        /*
         * Delete the newly created post.
         */
//        steemJ.deletePostOrComment(myNewPost.getParentPermlink());

    }
    @RequestMapping(value = "/vote", method = RequestMethod.POST)
    @ApiOperation(httpMethod = "POST", value = "Response a string describing if the Steem vote is successfully triggled.")
    public void vote(@RequestBody @Valid KafkaMessage kafkaMessage) {
        //
        LOG.info("");
    }
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    @ApiOperation(httpMethod = "POST", value = "Response a string describing if the Steem comment is successfully triggled.")
    public void comment(@RequestBody @Valid KafkaMessage kafkaMessage) throws SteemResponseException, SteemCommunicationException, SteemInvalidTransactionException {
        //
        /*
         * Write a new comment.
         *
         * Author of the post to reply to = "steemj" Permlink of the post to
         * reply to = "testofsteemj040" Title = "Test of SteemJ 0.4.0"
         * Content = "Test using SteemJ 0. ..... " Tags = "test"
         *
         */
        steemJ.createComment(new AccountName("steemj"), new Permlink("testofsteemj040"),
                "Example comment without a link but with a @user .", new String[] { "test" });
    }

    @RequestMapping(value = "/follow", method = RequestMethod.POST)
    @ApiOperation(httpMethod = "POST", value = "Response a string describing if the Steem follow is successfully triggled.")
    public void follow(@RequestBody @Valid KafkaMessage kafkaMessage) throws SteemResponseException, SteemCommunicationException, SteemInvalidTransactionException {
        //
        // Let the default account ("steemj") follow "cyriana"
        steemJ.follow(new AccountName("cyriana"));
        // Let the default account ("steemj") unfollow "cyriana"
//        steemJ.unfollow(new AccountName("cyriana"));
    }

    @RequestMapping(value = "/transfer", method = RequestMethod.POST)
    @ApiOperation(httpMethod = "POST", value = "Response a string describing if the Steem transfer is successfully triggled.")
    public void transfer(@RequestBody @Valid KafkaMessage kafkaMessage) {
        //
        /*
         * Let the default account transfer 1.0 SBD to @dez1337.
         */
//        steemJ.transfer(new AccountName("dez1337"), new Asset(new BigDecimal("1.000"), AssetSymbolType.STEEM), "Hello @dez1337 - I've send you one STEEM.");
    }

    @RequestMapping(value = "/delegateVestingShares", method = RequestMethod.POST)
    @ApiOperation(httpMethod = "POST", value = "Response a string describing if the Steem delegateVestingShares is successfully triggled.")
    public void delegateVestingShares(@RequestBody @Valid KafkaMessage kafkaMessage) throws SteemResponseException, SteemCommunicationException, SteemInvalidTransactionException {
        //
        /*
         * Let the default account delegate 10.0 VESTS to @dez1337.
         */
        steemJ.delegateVestingShares(new AccountName("dez1337"), new Asset(10L, AssetSymbolType.VESTS));
    }

    @RequestMapping(value = "/claimRewards", method = RequestMethod.POST)
    @ApiOperation(httpMethod = "POST", value = "Response a string describing if the Steem claimRewards is successfully triggled.")
    public void claimRewards(@RequestBody @Valid KafkaMessage kafkaMessage) throws SteemResponseException, SteemCommunicationException, SteemInvalidTransactionException {
        //

        /*
         * Claim the rewards of the default account.
         */
        steemJ.claimRewards();
    }

}
