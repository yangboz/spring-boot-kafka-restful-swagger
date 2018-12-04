package info.smartkit.cloud.streaming.services;

import eu.bittrade.libs.steemj.SteemJ;
import eu.bittrade.libs.steemj.base.models.operations.CommentOperation;
import eu.bittrade.libs.steemj.enums.PrivateKeyType;
import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steemj.exceptions.SteemResponseException;
import info.smartkit.cloud.streaming.dto.SteemPost;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.stereotype.Service;

import java.util.List;

public interface SteemService {
    SteemJ config(String accountName, List<ImmutablePair<PrivateKeyType, String>> privateKeys) throws SteemResponseException, SteemCommunicationException;
    CommentOperation post(SteemPost steemPost) throws SteemResponseException, SteemCommunicationException, SteemInvalidTransactionException;
    SteemJ getSteemJ();
}
