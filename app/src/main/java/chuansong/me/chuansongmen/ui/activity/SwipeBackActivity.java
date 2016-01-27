package chuansong.me.chuansongmen.ui.activity;

import android.support.v7.widget.Toolbar;
import android.view.View;

import butterknife.Bind;
import chuansong.me.chuansongmen.R;
import chuansong.me.chuansongmen.ui.wigdets.SwipeBackLayout;

/**
 * Created by HongDanyang on 16/1/24.
 */
public abstract class SwipeBackActivity extends BaseActivity {
    @Bind(R.id.swipBackLayout)
    SwipeBackLayout mSwipeBackLayout;
    @Bind(R.id.toolbar)
    Toolbar mToolBar;


    abstract void setTitle();

    @Override
    protected void initViews() {

        initToolBar();
        mSwipeBackLayout.setCallBack(new SwipeBackLayout.CallBack() {
            @Override
            public void onFinish() {
                finish();
            }
        });

    }

    protected void initToolBar() {
        setTitle();
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }



}

