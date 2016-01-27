package chuansong.me.chuansongmen.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import chuansong.me.chuansongmen.app.AppService;

/**
 * Created by HongDanyang on 16/1/23.
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected abstract void initViews();
    protected abstract int getContentViewId();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        AppService.getInstance().addCompositeSub(getTaskId());
        AppService.getBus().register(this);
        ButterKnife.bind(this);
        initViews();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppService.getInstance().removeCompositeSub(getTaskId());
        AppService.getBus().unregister(this);
    }
}

