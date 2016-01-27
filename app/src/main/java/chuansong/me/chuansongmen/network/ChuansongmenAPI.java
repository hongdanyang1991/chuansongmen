package chuansong.me.chuansongmen.network;

import chuansong.me.chuansongmen.model.News;
import retrofit.http.GET;
import retrofit.http.Path;

import rx.Observable;
/**
 * Created by HongDanyang on 16/1/22.
 */
public interface ChuansongmenAPI {

    @GET("api/1/essays/{newsTpye}")
    Observable<News> updateNews(@Path("newsTpye") String newsTpye);

    @GET("api/1/essays/{newsTpye}?start={pageNo}")
    Observable<News> loadMoreNews(@Path("newsTpye") String newsTpye,@Path("pageNo") String pageNo);
}
