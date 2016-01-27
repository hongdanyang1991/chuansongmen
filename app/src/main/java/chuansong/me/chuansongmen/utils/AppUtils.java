package chuansong.me.chuansongmen.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;

import chuansong.me.chuansongmen.R;
import chuansong.me.chuansongmen.app.App;

/**
 * Created by HongDanyang on 16/1/24.
 */
public class AppUtils {

    public static String getVersionName(Context context) {
        String versionName = null;
        try {
            versionName = context.getApplicationContext().getPackageManager()
                    .getPackageInfo(context.getApplicationContext().getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }


    public static void showSnackBar(View view,int id) {
        Resources resources  = App.getContext().getResources();
        Snackbar sb = Snackbar.make(view, resources.getString(id), Snackbar.LENGTH_SHORT);
        setSnackbarMessageTextColor(sb);
        sb.getView().setBackgroundColor(resources.getColor( R.color.main_bg));
        sb.show();
    }

    public static void setSnackbarMessageTextColor(Snackbar snackbar) {
        View view = snackbar.getView();
        ((TextView) view.findViewById(R.id.snackbar_text)).setTextColor(Color.parseColor("#448AFF"));
    }

}

