package info.smartkit.cloud.streaming.dto;

import java.util.Arrays;

public class SteemComment {
    private String accountName;
    private String permlink;
    private String content;
    private String[] tags;

    public SteemComment(String accountName, String permlink, String content, String[] tags) {
        this.accountName = accountName;
        this.permlink = permlink;
        this.content = content;
        this.tags = tags;
    }

    public SteemComment() {
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getPermlink() {
        return permlink;
    }

    public void setPermlink(String permlink) {
        this.permlink = permlink;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "SteemComment{" +
                "accountName='" + accountName + '\'' +
                ", permlink='" + permlink + '\'' +
                ", content='" + content + '\'' +
                ", tags=" + Arrays.toString(tags) +
                '}';
    }
}
