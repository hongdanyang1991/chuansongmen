package chuansong.me.chuansongmen.event;

import chuansong.me.chuansongmen.data.Constant;
import chuansong.me.chuansongmen.model.News;

/**
 * Created by HongDanyang on 16/1/22.
 */
public class NewsEvent extends Event{

    private News news;

    private Constant.GETNEWSWAY getNewsWay;

    private String newsType;

    public String getNewsType() {
        return newsType;
    }

    public NewsEvent(News news,Constant.GETNEWSWAY getNewsWay,String newsType) {
        this.news=news;
        this.newsType=newsType;
        this.getNewsWay=getNewsWay;

    }

    public News getNews() {
        return news;
    }

    public Constant.GETNEWSWAY getNewsWay() {
        return getNewsWay;
    }
}
