package info.smartkit.cloud.streaming.controllers;

import eu.bittrade.libs.steemj.SteemJ;
import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.base.models.Asset;
import eu.bittrade.libs.steemj.base.models.Permlink;
import eu.bittrade.libs.steemj.base.models.operations.CommentOperation;
import eu.bittrade.libs.steemj.base.models.operations.TransferOperation;
import eu.bittrade.libs.steemj.configuration.SteemJConfig;
import eu.bittrade.libs.steemj.enums.AssetSymbolType;
import eu.bittrade.libs.steemj.enums.PrivateKeyType;
import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steemj.exceptions.SteemResponseException;
import info.smartkit.cloud.streaming.dto.*;
import info.smartkit.cloud.streaming.services.SteemService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.logging.log4j.LogManager;
import org.gitlab4j.api.GitLabApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
// @see: https://github.com/marvin-we/steem-java-api-wrapper
@RequestMapping(value = "v1/cloud/stream/steem")
public class SteemController {

    private static org.apache.logging.log4j.Logger LOG = LogManager.getLogger(SteemController.class);

    @Autowired
    private SteemService steemService;

    @RequestMapping(method = RequestMethod.GET, value = "/info")
    @ApiOperation(value = "Response a string describing Steem info.")
//	@ApiImplicitParams({@ApiImplicitParam(name="Authorization", value="Authorization DESCRIPTION")})
    public @ResponseBody
    JsonString info() throws SteemResponseException, SteemCommunicationException {
        return new JsonString(steemService.getSteemJ().getHardforkVersion().toString());
    }
    @RequestMapping(method = RequestMethod.GET, value = "/config")
    @ApiOperation(value = "Response a string describing Steem config.")
//	@ApiImplicitParams({@ApiImplicitParam(name="Authorization", value="Authorization DESCRIPTION")})
    public @ResponseBody
    JsonString config(String accountName
//            List<ImmutablePair<PrivateKeyType, String>> privateKeys
    ) throws SteemResponseException, SteemCommunicationException {
//   LOG.info("SteemJ config keys:"+privateKeys.toString());
        SteemJ steemJ = steemService.config(accountName,null);
        return new JsonString(steemJ.toString());
    }

    @RequestMapping(value = "/post", method = RequestMethod.POST)
    @ApiOperation(httpMethod = "POST", value = "Response a string describing if the Steem posting is successfully triggled.")
    public JsonString post(@RequestBody @Valid SteemPost steemPost) throws SteemResponseException, SteemCommunicationException, SteemInvalidTransactionException {

        CommentOperation postResult = steemService.post(steemPost);
        // Let the default account ("steemj") resteem a post".
//        steemJ.reblog(new AccountName("dez1337"),
        /*
         * Delete the newly created post.
         */
//        steemJ.deletePostOrComment(myNewPost.getParentPermlink());
        return new JsonString(postResult.getPermlink().getLink().toString());

    }
    @RequestMapping(value = "/vote", method = RequestMethod.POST)
    @ApiOperation(httpMethod = "POST", value = "Response a string describing if the Steem vote is successfully triggled.")
    public void vote(@RequestBody @Valid SteemVote steemVote) throws SteemResponseException, SteemCommunicationException, SteemInvalidTransactionException {
        //
        /*
         * Upvote the post
         * "steem-java-api-learned-to-speak-graphene-update-5" written by
         * "dez1337" using short 100% of the defaultAccounts voting power.
         */
        steemService.getSteemJ().vote(new AccountName(steemVote.getAccountName()), new Permlink(steemVote.getPermlink()),
                steemVote.getPercentage());
        /*
         * Remove the vote made earlier.
         */
//        steemJ.cancelVote(new AccountName("dez1337"),
//                new Permlink("steem-java-api-learned-to-speak-graphene-update-5"));
    }
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    @ApiOperation(httpMethod = "POST", value = "Response a string describing if the Steem comment is successfully triggled.")
    public void comment(@RequestBody @Valid SteemComment steemComment) throws SteemResponseException, SteemCommunicationException, SteemInvalidTransactionException {
        //
        /*
         * Write a new comment.
         *
         * Author of the post to reply to = "steemj" Permlink of the post to
         * reply to = "testofsteemj040" Title = "Test of SteemJ 0.4.0"
         * Content = "Test using SteemJ 0. ..... " Tags = "test"
         *
         */
//        steemService.getSteemJ().createComment(new AccountName("steemj"), new Permlink("testofsteemj040"),
//                "Example comment without a link but with a @user .", new String[] { "test" });
        steemService.getSteemJ().createComment(new AccountName(steemComment.getAccountName()), new Permlink(steemComment.getPermlink()),
                steemComment.getContent(), steemComment.getTags());
    }

    @RequestMapping(value = "/follow", method = RequestMethod.POST)
    @ApiOperation(httpMethod = "POST", value = "Response a string describing if the Steem follow is successfully triggled.")
    public void follow(@RequestBody @Valid String accountName) throws SteemResponseException, SteemCommunicationException, SteemInvalidTransactionException {
        //
        // Let the default account ("steemj") follow "cyriana"
        steemService.getSteemJ().follow(new AccountName(accountName));
        // Let the default account ("steemj") unfollow "cyriana"
//        steemJ.unfollow(new AccountName("cyriana"));
    }

    @RequestMapping(value = "/transfer", method = RequestMethod.POST)
    @ApiOperation(httpMethod = "POST", value = "Response a string describing if the Steem transfer is successfully triggled.")
    public JsonString transfer(@RequestBody @Valid SteemTransfer steemTransfer) throws SteemResponseException, SteemCommunicationException, SteemInvalidTransactionException {
        //
        /*
         * Let the default account transfer 1.0 SBD to @dez1337.
         */
//        TransferOperation transferOpe = steemService.getSteemJ().transfer(
//                new AccountName(steemTransfer.getAccountName()),
//                new Asset(new BigDecimal(steemTransfer.getValue()), AssetSymbolType.STEEM), steemTransfer.getMemo());
        TransferOperation transferOpe = steemService.getSteemJ().transfer(
                new AccountName(steemTransfer.getAccountName()),
                new Asset(steemTransfer.getValue(), AssetSymbolType.STEEM), steemTransfer.getMemo());
        return new JsonString(transferOpe.toString());
    }

    @RequestMapping(value = "/delegateVestingShares", method = RequestMethod.POST)
    @ApiOperation(httpMethod = "POST", value = "Response a string describing if the Steem delegateVestingShares is successfully triggled.")
    public void delegateVestingShares(@RequestBody @Valid String accountName, long amount) throws SteemResponseException, SteemCommunicationException, SteemInvalidTransactionException {
        //
        /*
         * Let the default account delegate 10.0 VESTS to @dez1337.
         */
        steemService.getSteemJ().delegateVestingShares(new AccountName(accountName), new Asset(amount, AssetSymbolType.VESTS));
    }

    @RequestMapping(value = "/claimRewards", method = RequestMethod.POST)
    @ApiOperation(httpMethod = "POST", value = "Response a string describing if the Steem claimRewards is successfully triggled.")
    public void claimRewards(@RequestBody @Valid String accountName) throws SteemResponseException, SteemCommunicationException, SteemInvalidTransactionException {
        //

        /*
         * Claim the rewards of the default account.
         */
        steemService.getSteemJ().claimRewards();
    }

}
