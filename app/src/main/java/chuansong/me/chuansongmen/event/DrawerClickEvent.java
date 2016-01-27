package chuansong.me.chuansongmen.event;

/**
 * Created by HongDanyang on 16/1/23.
 */
public class DrawerClickEvent extends Event {

    private int drawId;

    public DrawerClickEvent(int drawId) {
        this.drawId = drawId;
    }

    public int getDrawId() {
        return drawId;
    }

}

