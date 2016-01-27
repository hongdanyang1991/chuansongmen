package chuansong.me.chuansongmen.event;

import chuansong.me.chuansongmen.data.Constant;

/**
 * Created by HongDanyang on 16/1/22.
 */
public class Event {

    protected Constant.Result mEventResult;

    public void setEventResult(Constant.Result eventResult) {
        this.mEventResult=eventResult;
    }

    public Constant.Result getEventResult() {
        return mEventResult;
    }
}
