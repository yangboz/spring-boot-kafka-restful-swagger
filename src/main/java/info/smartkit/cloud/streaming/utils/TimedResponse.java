package info.smartkit.cloud.streaming.utils;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class TimedResponse<T> {
    private T data;
    private String startingThread;
    private String completingThread;
    private long timeMs;
    private boolean error = false;

    public TimedResponse() {
        this.startingThread = Thread.currentThread().getName();
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getStartingThread() {
        return startingThread;
    }

    public String getCompletingThread() {
        return completingThread;
    }

    public void setCompletingThread(String completingThread) {
        this.completingThread = completingThread;
    }

    public long getTimeMs() {
        return timeMs;
    }

    public void setTimeMs(long timeMs) {
        this.timeMs = timeMs;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
