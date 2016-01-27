package chuansong.me.chuansongmen.ui.fragment.base;

import android.support.v4.widget.SwipeRefreshLayout;

import butterknife.Bind;
import chuansong.me.chuansongmen.R;

/**
 * Created by HongDanyang on 16/1/24.
 */
public abstract class SwipeRefreshBaseFragment extends ToorbarBaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    @Bind(R.id.swipeRefreshLayout)
    protected SwipeRefreshLayout mSwipeRefreshLayout;
    protected void setRefreshing() {
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                onRefresh();
            }
        });
    }

    protected boolean isRefreshing() {
        return mSwipeRefreshLayout.isRefreshing();
    }

    protected void stopRefreshing() {
        mSwipeRefreshLayout.setRefreshing(false);
    }
    @Override
    protected void initViews() {
        super.initViews();
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryLight);
    }


}
