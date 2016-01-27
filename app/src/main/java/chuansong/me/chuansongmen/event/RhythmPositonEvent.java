package chuansong.me.chuansongmen.event;

/**
 * Created by HongDanyang on 16/1/23.
 */
public class RhythmPositonEvent extends Event{

    private int mPosition;

    public RhythmPositonEvent(int position) {
        this.mPosition=position;
    }

    public int getPosition() {
        return mPosition;
    }
}
