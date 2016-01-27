package chuansong.me.chuansongmen.app;

import android.app.Application;
import android.content.Context;

import chuansong.me.chuansongmen.data.Constant;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;

import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

/**
 * Created by HongDanyang on 16/1/22.
 */
public class App extends Application {

    private static Context sContext;


    @Override
    public void onCreate() {
        System.out.println("-------------------------------------------------");
        super.onCreate();
        sContext = getApplicationContext();
        AppService.getInstance().initService();
        Logger
                .init(Constant.LOGTAG)
                .setMethodCount(3)
                .hideThreadInfo()
                .setLogLevel(LogLevel.NONE);

    }

    public static Context getContext() {
        return sContext;
    }

}
