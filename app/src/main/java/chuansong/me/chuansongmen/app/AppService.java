package chuansong.me.chuansongmen.app;

import android.os.Handler;
import android.os.HandlerThread;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import chuansong.me.chuansongmen.network.ChuansongmenAPI;
import chuansong.me.chuansongmen.network.ChuansongmenFactory;
import chuansong.me.chuansongmen.network.NewsDetileAPI;
import chuansong.me.chuansongmen.rxmethod.RxNews;
import de.greenrobot.event.EventBus;
import me.chuansong.greendao.DBHelper;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by HongDanyang on 16/1/22.
 */
public class AppService {

    private static final AppService CHUANSONGMEN_SERVICE=new AppService();

    private static Gson sGson;

    private static EventBus sBus ;

    private static DBHelper sDBHelper;

    private static ChuansongmenAPI sNbaplusApi;

    private static NewsDetileAPI sNewsDetileApi;

    private static ExecutorService sSingleThreadExecutor;

    private Map<Integer,CompositeSubscription> mCompositeSubByTaskId;

    private Handler mIoHandler;

    private AppService(){}

    public void initService() {
        sBus = EventBus.getDefault();
        sGson=new Gson();
        mCompositeSubByTaskId=new HashMap<Integer,CompositeSubscription>();
        backGroundInit();
    }

    private void backGroundInit() {
        HandlerThread ioThread = new HandlerThread("IoThread");
        ioThread.start();
        mIoHandler= new Handler(ioThread.getLooper());
        mIoHandler.post(new Runnable() {
            @Override
            public void run() {
                sNbaplusApi = ChuansongmenFactory.getChuansongmenInstance();
                sNewsDetileApi = ChuansongmenFactory.getNewsDetileInstance();
                sDBHelper = DBHelper.getInstance(App.getContext());
            }
        });
    }

    public void addCompositeSub(int taskId) {
        CompositeSubscription compositeSubscription;
        if(mCompositeSubByTaskId.get(taskId)==null) {
            compositeSubscription = new CompositeSubscription();
            mCompositeSubByTaskId.put(taskId, compositeSubscription);
        }
    }

    public void removeCompositeSub(int taskId) {
        CompositeSubscription compositeSubscription;
        if(mCompositeSubByTaskId!=null&& mCompositeSubByTaskId.get(taskId)!=null){
            compositeSubscription= mCompositeSubByTaskId.get(taskId);
            compositeSubscription.unsubscribe();
            mCompositeSubByTaskId.remove(taskId);
        }
    }

    private CompositeSubscription getCompositeSubscription(int taskId) {
        CompositeSubscription compositeSubscription ;
        if(mCompositeSubByTaskId.get(taskId)==null) {
            compositeSubscription = new CompositeSubscription();
            mCompositeSubByTaskId.put(taskId, compositeSubscription);
        }else {
            compositeSubscription= mCompositeSubByTaskId.get(taskId);
        }
        return compositeSubscription;
    }


    public void initNews(int taskId,String type) {
        getCompositeSubscription(taskId).add(RxNews.initNews(type));
    }

    public void updateNews(int taskId,String type) {
        getCompositeSubscription(taskId).add(RxNews.updateNews(type));
    }

    public void loadMoreNews(int taskId,String type,String pageNo) {
        getCompositeSubscription(taskId).add(RxNews.loadMoreNews(type, pageNo));
    }

    public void getNewsDetile(int taskId,String date,String detielId) {
        getCompositeSubscription(taskId).add(RxNews.getNewsDetile(date, detielId));
    }

    public static AppService getInstance() {
        return CHUANSONGMEN_SERVICE;
    }

    public static EventBus getBus() {
        return sBus;
    }

    public static ChuansongmenAPI getNbaplus() {
        return sNbaplusApi;
    }

    public static NewsDetileAPI getNewsDetileApi() {
        return sNewsDetileApi;
    }

    public static DBHelper getDBHelper() {
        return sDBHelper;
    }

    public static Gson getGson() {
        return sGson;
    }

    public static ExecutorService getSingleThreadExecutor(){
        return sSingleThreadExecutor;
    }
}
