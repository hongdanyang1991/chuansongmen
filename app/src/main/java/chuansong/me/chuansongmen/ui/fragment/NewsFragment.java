package chuansong.me.chuansongmen.ui.fragment;

import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import chuansong.me.chuansongmen.R;
import chuansong.me.chuansongmen.data.Constant;
import chuansong.me.chuansongmen.event.NewsAnimatEndEvent;
import chuansong.me.chuansongmen.event.NewsEvent;
import chuansong.me.chuansongmen.model.News;
import chuansong.me.chuansongmen.ui.adapter.LoadAdapter;
import chuansong.me.chuansongmen.ui.fragment.base.SwipeRefreshBaseFragment;
import chuansong.me.chuansongmen.ui.listener.RecyclerViewLoadMoreListener;
import chuansong.me.chuansongmen.utils.AppUtils;

/**
 * Created by HongDanyang on 16/1/24.
 */
public abstract class NewsFragment extends SwipeRefreshBaseFragment implements RecyclerViewLoadMoreListener.OnLoadMoreListener {
    @Bind(R.id.rv_news)
    RecyclerView mNewsListView;
    @Bind(R.id.newsContainer)
    CoordinatorLayout newsContainer;


    protected List<News.NewslistEntity> mNewsListEntity = new ArrayList<News.NewslistEntity>();
    protected LoadAdapter mLoadAdapter;
    protected String mNewsId = "";

    abstract void setAdapter();

    @Override
    protected void initViews() {
        super.initViews();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mNewsListView.getContext());
        mNewsListView.setLayoutManager(linearLayoutManager);
        mNewsListView.addOnScrollListener(new RecyclerViewLoadMoreListener(linearLayoutManager, this, 0));
        mNewsListView.setOnTouchListener(
                new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (mSwipeRefreshLayout.isRefreshing()) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                }
        );
        setAdapter();
    }

    protected void stopAll() {
        stopRefreshing();
        stopLoading();
    }

    protected void stopLoading() {
        mLoadAdapter.notifyItemChanged(mLoadAdapter.getItemCount() - 1);
        mLoadAdapter.setLoading(false);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_main;
    }


    protected void updateView(NewsEvent newsEvent) {

        if (Constant.Result.FAIL.equals(newsEvent.getEventResult())) {
            if (newsEvent.getNewsWay().equals(Constant.GETNEWSWAY.INIT)) {
                setRefreshing();
            } else {
                stopAll();
                AppUtils.showSnackBar(newsContainer, R.string.load_fail);
            }
        } else {
            News news = newsEvent.getNews();
            mNewsId = news.getNextPage();
            switch (newsEvent.getNewsWay()) {
                case INIT:
                    mNewsListEntity.clear();
                    mNewsListEntity.addAll(news.getNewslist());
                    break;
                case UPDATE:
                    mNewsListEntity.clear();
                    mNewsListEntity.addAll(news.getNewslist());
                    stopRefreshing();
                    mLoadAdapter.setAnimate(false);
                    break;
                case LOADMORE:
                    mNewsListEntity.addAll(news.getNewslist());
                    stopLoading();
                    mLoadAdapter.setAnimate(false);
                    break;
                default:
                    break;
            }
            mLoadAdapter.updateItem();
            if (Constant.GETNEWSWAY.UPDATE.equals(newsEvent.getNewsWay())) {
                AppUtils.showSnackBar(newsContainer, R.string.load_success);
            }

        }


    }

    public void onEventMainThread(NewsAnimatEndEvent newsAnimatEndEvent) {
        setRefreshing();
    }
}