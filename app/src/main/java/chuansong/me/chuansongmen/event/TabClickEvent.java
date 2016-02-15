package chuansong.me.chuansongmen.event;

/**
 * Created by HongDanyang on 16/2/15.
 */
public class TabClickEvent extends Event {

    private int tabId;

    public TabClickEvent(int tabId) {
        this.tabId = tabId;
    }

    public int getTabId() {
        return tabId;
    }
}
