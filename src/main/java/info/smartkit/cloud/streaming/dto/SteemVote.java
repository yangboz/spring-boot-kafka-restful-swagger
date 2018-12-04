package info.smartkit.cloud.streaming.dto;

public class SteemVote {
    private String accountName;
    private String permlink;
    private short percentage;

    public SteemVote() {
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

    public short getPercentage() {
        return percentage;
    }

    public void setPercentage(short percentage) {
        this.percentage = percentage;
    }

    public SteemVote(String accountName, String permlink, short percentage) {
        this.accountName = accountName;
        this.permlink = permlink;
        this.percentage = percentage;
    }

    @Override
    public String toString() {
        return "SteemVote{" +
                "accountName='" + accountName + '\'' +
                ", permlink='" + permlink + '\'' +
                ", percentage=" + percentage +
                '}';
    }
}
