package chuansong.me.chuansongmen.data;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by HongDanyang on 16/1/22.
 */
public class Constant {

    public static final String LOGTAG="CSM";//日志TAG

    public static final String APP_FIR_IM_URL="http://fir.im/chuansongmen";
    public static final String API_TOKEN_FIR="xxxxxxxxxxxxxxxxxxxxx";

    public static final String LOADIMAGE = "LOADIMAGE";
    public static final String ACTILEFONTSIZE = "ACTILEFONTSIZE";

    public static final String CSS_STYLE ="<style>* {font-size:%spx;line-height:28px;}p {color:%s;}</style>";

    private static final Map<String,Integer> sTeamIconMap=new HashMap<>();

    public static Map<String,Integer>  getTeamIcons()  {
        return sTeamIconMap;
    }

    public enum NEWSTYPE {
        NEWS("news"),BLOG("blog"),RECENT("recent"),SELECT("select"),NEWSMEDIA("newsmedia"),
        FUN("fun"),LIFEJOURNEY("lifejourney"),UTILITY("utility"),HISBOOK("hisbook"),FINANCE("finance"),
        FOOD("food"),MOVIEMUSIC("moviemusic"),AUTO("auto");
        private String newsType;
        NEWSTYPE(String newsType) {
            this.newsType=newsType;
        }
        public String getNewsType() {
            return newsType;
        }
    }

    public enum GETNEWSWAY {
        INIT,UPDATE,LOADMORE
    }

    public enum Result {
        SUCCESS,FAIL,NORMAL
    }

}
