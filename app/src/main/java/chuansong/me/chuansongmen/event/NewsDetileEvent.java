package chuansong.me.chuansongmen.event;

/**
 * Created by HongDanyang on 16/1/24.
 */
public class NewsDetileEvent extends Event {

    private String mNewsContent;

    public NewsDetileEvent(String newsContent) {
        this.mNewsContent=newsContent;
    }
    public String getContent() {
        return mNewsContent;
    }
}
