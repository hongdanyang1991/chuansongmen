package chuansong.me.chuansongmen.rxmethod;

import com.orhanobut.logger.Logger;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import chuansong.me.chuansongmen.app.App;
import chuansong.me.chuansongmen.app.AppService;
import chuansong.me.chuansongmen.data.Constant;
import chuansong.me.chuansongmen.event.NewsDetileEvent;
import chuansong.me.chuansongmen.event.NewsEvent;
import chuansong.me.chuansongmen.model.News;
import chuansong.me.chuansongmen.model.NewsDetile;
import chuansong.me.chuansongmen.utils.PreferenceUtils;
import de.greenrobot.dao.query.DeleteQuery;
import de.greenrobot.dao.query.Query;

import me.chuansong.greendao.GreenNews;
import me.chuansong.greendao.GreenNewsDao;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


/**
 * Created by HongDanyang on 16/1/22.
 */
public class RxNews {

    public static Subscription initNews(final String newsType) {
        Subscription subscription = Observable.create(new Observable.OnSubscribe<News>() {
            @Override
            public void call(Subscriber<? super News> subscriber) {
                News news = getCacheNews(newsType);
                subscriber.onNext(news);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io())
                .subscribe(new Action1<News>() {
                    @Override
                    public void call(News news) {
                        NewsEvent newsEvent = new NewsEvent(news, Constant.GETNEWSWAY.INIT, newsType);
                        if (news == null) {
                            newsEvent.setEventResult(Constant.Result.FAIL);
                        }
                        AppService.getBus().post(newsEvent);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        NewsEvent newsEvent = new NewsEvent(new News(), Constant.GETNEWSWAY.INIT, newsType);
                        newsEvent.setEventResult(Constant.Result.FAIL);
                        AppService.getBus().post(newsEvent);
                    }
                });
        return subscription;
    }

    public static Subscription updateNews(final String newsType) {
        Logger.e("xxxxxxxxxx","xxxxxxxx");

        Subscription subscription = AppService.getNbaplus().updateNews(newsType)
                .subscribeOn(Schedulers.io())
                .doOnNext(new Action1<News>() {
                    @Override
                    public void call(News news) {
                        cacheNews(news, newsType);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<News>() {
                    @Override
                    public void call(News news) {
                        AppService.getBus().post(new NewsEvent(news, Constant.GETNEWSWAY.UPDATE,newsType));
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        NewsEvent newsEvent= new NewsEvent(new News(), Constant.GETNEWSWAY.UPDATE,newsType);
                        newsEvent.setEventResult(Constant.Result.FAIL);
                        AppService.getBus().post(newsEvent);
                    }
                });
        return subscription;
    }

    public static Subscription loadMoreNews(final String newsType,final String pageNo) {
        Subscription subscription = AppService.getNbaplus().loadMoreNews(newsType, pageNo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<News>() {
                    @Override
                    public void call(News news) {
                        AppService.getBus().post(new NewsEvent(news, Constant.GETNEWSWAY.LOADMORE,newsType));
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        NewsEvent newsEvent= new NewsEvent(new News(), Constant.GETNEWSWAY.LOADMORE,newsType);
                        newsEvent.setEventResult(Constant.Result.FAIL);
                        AppService.getBus().post(newsEvent);
                    }
                });
        return subscription;
    }

    public static Subscription getNewsDetile(final String date,final String detileId) {
        Subscription subscription = AppService.getNewsDetileApi().getNewsDetile(date, detileId + ".json")
                .subscribeOn(Schedulers.io())
                .map(new Func1<NewsDetile, String>() {
                    @Override
                    public String call(NewsDetile newsDetile) {
                        int imageCount = 0;
                        String assStyle = String.format(Constant.CSS_STYLE, PreferenceUtils.getPrefString(App.getContext()
                                , Constant.ACTILEFONTSIZE, "18"), "#333333");
                        String html = "<html><head>" + assStyle + "</head><body>"
                                + newsDetile.getContent() + "<p>（" + newsDetile.getAuthor() + "）<p></body></html>";
                        Pattern p = Pattern.compile("(<p class=\"reader_img_box\"><img id=\"img_\\d\" class=\"reader_img\" src=\"reader_img_src\" /></p>)+");
                        Matcher m = p.matcher(html);
                        while (m.find() && imageCount < newsDetile.getArticleMediaMap().size()) {
                            if (imageCount == 0) {
                                html = html.replace(html.substring(m.start(), (m.end())), "");
                            } else {
                                if (PreferenceUtils.getPrefBoolean(App.getContext(), Constant.LOADIMAGE, true)) {
                                    html = html.replace(html.substring(m.start(), (m.end())), "<img src="
                                            + newsDetile.getArticleMediaMap().get(String.format("img_%d", imageCount)).getUrl() + " "
                                            + "width=\"100%\" height=\"auto\">");
                                }
                            }
                            imageCount++;
                            m = p.matcher(html);
                        }
                        return html;
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String newsContent) {
                        AppService.getBus().post(new NewsDetileEvent(newsContent));
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        NewsDetileEvent newsDetileEvent = new NewsDetileEvent(throwable.toString());
                        newsDetileEvent.setEventResult(Constant.Result.FAIL);
                        AppService.getBus().post(newsDetileEvent);
                    }
                });
        return subscription;
    }


    public static void cacheNews(final News news,final String newsType) {

        GreenNewsDao greenNewsDao = AppService.getDBHelper().getDaoSession().getGreenNewsDao();
        DeleteQuery deleteQuery = greenNewsDao.queryBuilder()
                .where(GreenNewsDao.Properties.Type.eq(newsType))
                .buildDelete();
        deleteQuery.executeDeleteWithoutDetachingEntities();
        String newsList = AppService.getGson().toJson(news);
        GreenNews greenNews = new GreenNews(null, newsList, newsType);
        greenNewsDao.insert(greenNews);

    }

    private static News getCacheNews(String newsType) {
        News news=null;
        GreenNewsDao greenNewsDao = AppService.getDBHelper().getDaoSession().getGreenNewsDao();
        Query query = greenNewsDao.queryBuilder()
                .where(GreenNewsDao.Properties.Type.eq(newsType))
                .build();
        // 查询结果以 List 返回
        List<GreenNews> greenNewses = query.list();
        if(greenNewses!=null&&greenNewses.size()>0) {
            news = AppService.getGson().fromJson(greenNewses.get(0).getNewslist(), News.class);
        }
        return news;
    }
}
