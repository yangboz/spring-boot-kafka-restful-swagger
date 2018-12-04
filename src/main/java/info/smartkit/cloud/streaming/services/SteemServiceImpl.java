package info.smartkit.cloud.streaming.services;

import eu.bittrade.libs.steemj.SteemJ;
import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.base.models.operations.CommentOperation;
import eu.bittrade.libs.steemj.configuration.SteemJConfig;
import eu.bittrade.libs.steemj.enums.PrivateKeyType;
import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steemj.exceptions.SteemResponseException;
import info.smartkit.cloud.streaming.controllers.SteemController;
import info.smartkit.cloud.streaming.dto.SteemPost;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.logging.log4j.LogManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SteemServiceImpl implements SteemService{

    private static org.apache.logging.log4j.Logger LOG = LogManager.getLogger(SteemServiceImpl.class);

    private SteemJ steemJ;

    @Override
    public SteemJ config(String accountName, List<ImmutablePair<PrivateKeyType, String>> privateKeys) throws SteemResponseException, SteemCommunicationException {
        //@see: https://github.com/marvin-we/steem-java-api-wrapper/blob/master/sample/src/main/java/my/sample/project/SteemJUsageExample.java
        // Change the default settings if needed:
        SteemJConfig myConfig = SteemJConfig.getInstance();
        myConfig.setResponseTimeout(100000);
        myConfig.setDefaultAccount(new AccountName("lorechain"));

        // Add and manage private keys:
        if(privateKeys==null) {
            privateKeys = new ArrayList<>();
            privateKeys.add(new ImmutablePair<>(PrivateKeyType.POSTING, "5JpoKqAapBg5REaJjhfiNvaz1LPDUatYoVi3RkStVBmaGfaH3xJ"));
            privateKeys.add(new ImmutablePair<>(PrivateKeyType.MEMO, "5JPbtCPyEwKszPKwsbLLZvLeZB5Fv6YhRQFU1bdyaARWgetkbXr"));
        }// ... add more keys if needed.

        myConfig.getPrivateKeyStorage().addAccount(myConfig.getDefaultAccount(), privateKeys);

        // Create a new apiWrapper with your config object.
        steemJ = new SteemJ();
        return steemJ;
    }

    @Override
    public CommentOperation post(SteemPost steemPost) throws SteemResponseException, SteemCommunicationException, SteemInvalidTransactionException {
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
        CommentOperation myNewPost = steemJ.createPost(steemPost.getTitle(), steemPost.getContent(), steemPost.getTags());
        LOG.info(
                "SteemJ has generated some additional values for my new post. One good example is the permlink {} that I may need later on.",
                myNewPost.getPermlink().getLink());
        return myNewPost;
    }

    @Override
    public SteemJ getSteemJ() {
        return this.steemJ;
    }
}
