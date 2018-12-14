package info.smartkit.cloud.streaming.services;

import eu.bittrade.libs.steemj.SteemJ;
import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.base.models.operations.ClaimRewardBalanceOperation;
import eu.bittrade.libs.steemj.base.models.operations.CommentOperation;
import eu.bittrade.libs.steemj.configuration.SteemJConfig;
import eu.bittrade.libs.steemj.enums.PrivateKeyType;
import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steemj.exceptions.SteemResponseException;
import info.smartkit.cloud.streaming.dto.SteemPost;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.logging.log4j.LogManager;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class SteemServiceImpl implements SteemService{

    private static org.apache.logging.log4j.Logger LOG = LogManager.getLogger(SteemServiceImpl.class);

    private SteemJ steemJ;
    private String accountName="lorechain";//default testing account;

    @Override
//    @Async("asyncExecutor")
    public SteemJ config(String accountName, List<ImmutablePair<PrivateKeyType, String>> privateKeys) throws SteemResponseException, SteemCommunicationException {
//        return CompletableFuture.completedFuture(steemJ);
        this.accountName = accountName;
        return configForSteemJ(accountName, privateKeys);
    }

    private SteemJ configForSteemJ(String accountName, List<ImmutablePair<PrivateKeyType, String>> privateKeys) throws SteemCommunicationException, SteemResponseException {
        //@see: https://github.com/marvin-we/steem-java-api-wrapper/blob/master/sample/src/main/java/my/sample/project/SteemJUsageExample.java
        //@see: https://github.com/marvin-we/steem-java-api-wrapper/wiki/SteemJConfig
        // Change the default settings if needed:
        SteemJConfig myConfig = SteemJConfig.getInstance();
        myConfig.setResponseTimeout(100000);
        LOG.info("default account name for steemJ config:"+ this.accountName);
        myConfig.setDefaultAccount(new AccountName(this.accountName));

        // Add and manage private keys:
        if(privateKeys==null) {
            privateKeys = new ArrayList<>();
            privateKeys.add(new ImmutablePair<>(PrivateKeyType.POSTING, "5JpoKqAapBg5REaJjhfiNvaz1LPDUatYoVi3RkStVBmaGfaH3xJ"));
            privateKeys.add(new ImmutablePair<>(PrivateKeyType.MEMO, "5JPbtCPyEwKszPKwsbLLZvLeZB5Fv6YhRQFU1bdyaARWgetkbXr"));
        }// ... add more keys if needed.

        myConfig.getPrivateKeyStorage().addAccount(myConfig.getDefaultAccount(), privateKeys);

        // Create a new apiWrapper with your config object.
        this.steemJ = new SteemJ();
        return steemJ;
    }

    @Override
    @Async("asyncExecutor")
    public CompletableFuture<CommentOperation> post(SteemPost steemPost) throws SteemResponseException, SteemCommunicationException, SteemInvalidTransactionException {
        /*
         * Write a new post.
         *
         * Title = "Test of SteemJ 0.4.0" Content =
         * "Test using SteemJ 0. ..... " Tags = "test", "dontvote"
         *
         */
//        CommentOperation myNewPost = steemJ.createPost("Test of SteemJ 0.4.0",
//                "Test using SteemJ 0.4.0 by @dez1337 with a link to "
//                        + "https://github.com/marvin-we/steem-java-api-wrapper "
//                        + "and an image ![SteemJV2Logo](https://imgur.com/bIhZlYT.png).",
//                new String[] { "test", "dontvote" });
        CommentOperation myNewPost = this.getSteemJ().createPost(steemPost.getTitle(), steemPost.getContent(), steemPost.getTags());
        LOG.info(
                "SteemJ has generated some additional values for my new post. One good example is the permlink {} that I may need later on.",
                myNewPost.getPermlink().getLink());
        return CompletableFuture.completedFuture(myNewPost);
    }

    @Override
    public SteemJ getSteemJ() throws SteemResponseException, SteemCommunicationException {
        //config at first.
        this.steemJ = configForSteemJ(this.accountName,null);
        return this.steemJ;
    }

    @Override
    public CompletableFuture<ClaimRewardBalanceOperation> claimRewards(AccountName accountName) throws SteemResponseException, SteemCommunicationException, SteemInvalidTransactionException {
        ClaimRewardBalanceOperation claimRewardBalanceOperation = this.getSteemJ().claimRewards(accountName);
        return CompletableFuture.completedFuture(claimRewardBalanceOperation);
    }
}
