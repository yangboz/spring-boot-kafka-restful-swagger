package info.smartkit.cloud.streaming.services;

import eu.bittrade.libs.steemj.SteemJ;
import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.base.models.operations.ClaimRewardBalanceOperation;
import eu.bittrade.libs.steemj.base.models.operations.CommentOperation;
import eu.bittrade.libs.steemj.enums.PrivateKeyType;
import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steemj.exceptions.SteemResponseException;
import info.smartkit.cloud.streaming.dto.SteemPost;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface SteemService {
    SteemJ config(String accountName, List<ImmutablePair<PrivateKeyType, String>> privateKeys) throws SteemResponseException, SteemCommunicationException;
    CompletableFuture<CommentOperation> post(SteemPost steemPost) throws SteemResponseException, SteemCommunicationException, SteemInvalidTransactionException;
    SteemJ getSteemJ() throws SteemResponseException, SteemCommunicationException;
    CompletableFuture<ClaimRewardBalanceOperation> claimRewards(AccountName accountName) throws SteemResponseException, SteemCommunicationException, SteemInvalidTransactionException;
}
