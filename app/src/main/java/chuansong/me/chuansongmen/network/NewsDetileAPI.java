package chuansong.me.chuansongmen.network;

import chuansong.me.chuansongmen.model.NewsDetile;
import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by HongDanyang on 16/1/24.
 */
public interface NewsDetileAPI {
    @GET("{date}/{detileId}")
    Observable<NewsDetile> getNewsDetile(@Path("date") String type,@Path("detileId") String newsId);
}
