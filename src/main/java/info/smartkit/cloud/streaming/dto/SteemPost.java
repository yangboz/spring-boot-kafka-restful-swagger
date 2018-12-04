package info.smartkit.cloud.streaming.dto;

import java.util.Arrays;

public class SteemPost {

    private String title;
    private String content;
    private String[] tags;

    public SteemPost(String title, String content, String[] tags) {
        this.title = title;
        this.content = content;
        this.tags = tags;
    }

    public SteemPost() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
        return "SteemPost{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", tags=" + Arrays.toString(tags) +
                '}';
    }
}
