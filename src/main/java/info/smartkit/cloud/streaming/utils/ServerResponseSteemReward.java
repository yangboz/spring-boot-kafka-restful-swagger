package info.smartkit.cloud.streaming.utils;

import eu.bittrade.libs.steemj.base.models.operations.ClaimRewardBalanceOperation;

public class ServerResponseSteemReward extends TimedResponse<ClaimRewardBalanceOperation> {
    private String name;
    public ServerResponseSteemReward(String name) {
        this.name = name;
    }
}
