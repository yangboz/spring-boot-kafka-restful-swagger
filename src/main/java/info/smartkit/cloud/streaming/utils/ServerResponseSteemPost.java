package info.smartkit.cloud.streaming.utils;

import eu.bittrade.libs.steemj.base.models.operations.CommentOperation;

public class ServerResponseSteemPost extends TimedResponse<CommentOperation> {
    private String name;
    public ServerResponseSteemPost(String name) {
        this.name = name;
    }
}
