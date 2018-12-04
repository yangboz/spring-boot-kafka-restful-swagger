package info.smartkit.cloud.streaming.dto;

public class SteemTransfer {
    private String accountName;
    private String memo;
    private long value;

    public SteemTransfer(String accountName, String memo, long value) {
        this.accountName = accountName;
        this.memo = memo;
        this.value = value;
    }

    public SteemTransfer() {
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "SteemTransfer{" +
                "accountName='" + accountName + '\'' +
                ", memo='" + memo + '\'' +
                ", value=" + value +
                '}';
    }
}
