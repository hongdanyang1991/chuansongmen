package chuansong.me.chuansongmen.network;

import lombok.NoArgsConstructor;

/**
 * Created by HongDanyang on 16/1/22.
 */
@NoArgsConstructor
public class ChuansongmenFactory {

    private static ChuansongmenAPI sInstanceInstance=null;

    private static NewsDetileAPI sNewsSetileInStance=null;

    private static final Object WATCH_DOG=new Object();

    public static ChuansongmenAPI getChuansongmenInstance() {
        synchronized (WATCH_DOG) {
            if(sInstanceInstance==null){
                ChuansongmenCilent chuansongmenCilent = new ChuansongmenCilent();
                sInstanceInstance=chuansongmenCilent.getCilent();
            }
            return sInstanceInstance;
        }
    }

    public static NewsDetileAPI getNewsDetileInstance() {
        synchronized (WATCH_DOG) {
            if(sNewsSetileInStance==null){
                ChuansongmenCilent nbaplusCilent = new ChuansongmenCilent();
                sInstanceInstance=nbaplusCilent.getCilent();
                sNewsSetileInStance=nbaplusCilent.getNewsDetileClient();
            }
            return sNewsSetileInStance;
        }
    }
}
