package chuansong.me.chuansongmen.network;

import chuansong.me.chuansongmen.model.News;

import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by SilenceDut on 2015/11/28.
 */
public interface ChuansongmenAPI{
    @GET("api/v1.0/{type}/update")
    Observable<News> updateNews(@Path("type") String type);
    @GET("api/v1.0/{type}/loadmore/{newsId}")
    Observable<News> loadMoreNews(@Path("type") String type,@Path("newsId") String newsId );
}