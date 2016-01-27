package chuansong.me.chuansongmen.network;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * Created by HongDanyang on 16/1/22.
 */
public class ChuansongmenCilent {

    final ChuansongmenAPI chuansongmenAPI ;

    final NewsDetileAPI newsDetileAPI;

    ChuansongmenCilent( ) {
        Retrofit retrofit0 = new Retrofit.Builder()
                .baseUrl("http://192.168.23.10:7171/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        chuansongmenAPI=retrofit0.create(ChuansongmenAPI.class);

        Retrofit retrofit1 = new Retrofit.Builder()
                .baseUrl("http://reader.res.meizu.com/reader/articlecontent/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        newsDetileAPI=retrofit1.create(NewsDetileAPI.class);
    }

    public ChuansongmenAPI getCilent() {
        return chuansongmenAPI;
    }

    public NewsDetileAPI getNewsDetileClient() {
        return newsDetileAPI;
    }
}
