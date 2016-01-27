package chuansong.me.chuansongmen.model;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by HongDanyang on 16/1/24.
 */
@Setter
@Getter
public class NewsDetile {

    private String author;

    private Map<String,ContentImage> articleMediaMap;

    private String content;

    public String getAuthor() {
        return author;
    }

    @Setter
    @Getter
    public static class ContentImage {
        String url;
        int id;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
