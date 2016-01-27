package chuansong.me.chuansongmen.ui.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.orhanobut.logger.Logger;

import butterknife.Bind;
import chuansong.me.chuansongmen.R;
import chuansong.me.chuansongmen.app.AppService;
import chuansong.me.chuansongmen.data.Constant;
import chuansong.me.chuansongmen.event.NewsEvent;
import chuansong.me.chuansongmen.ui.adapter.MainAdapter;
import chuansong.me.chuansongmen.utils.NumericalUtil;

/**
 * Created by HongDanyang on 16/1/24.
 */
public class MainFragment extends NewsFragment{

    private static final int ANIM_DURATION_TOOLBAR = 300;
    private static boolean mFirstAnimate=true;
    @Bind(R.id.mian_title)
    View mainTitle;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static MainFragment newInstance() {

        MainFragment mainFragment = new MainFragment();
        return mainFragment;
    }

    @Override
    void setAdapter() {
        mSwipeRefreshLayout.setBackgroundResource(R.color.grey50);
        mainTitle.setVisibility(View.VISIBLE);
        mLoadAdapter=new MainAdapter(getActivity(),mNewsListEntity);
        mNewsListView.setAdapter(mLoadAdapter);
        mLoadAdapter.setAnimate(mFirstAnimate);
        if(mFirstAnimate) {
            startIntoAnimation();
            mFirstAnimate=false;
        }else {
            initCaChe();
        }
    }
    private void initCaChe() {
        Log.e("getNewsType", Constant.NEWSTYPE.RECENT.getNewsType());
        AppService.getInstance().initNews(getTaskId(), Constant.NEWSTYPE.RECENT.getNewsType());
    }

    @Override
    public void onRefresh() {
        Log.e("getNewsType!!!", Constant.NEWSTYPE.RECENT.getNewsType());
        Log.e("getTaskId!!!", getTaskId()+"");
        AppService.getInstance().updateNews(getTaskId(), Constant.NEWSTYPE.RECENT.getNewsType());
    }


    @Override
    public void onLoadMore() {
        if (mLoadAdapter.canLoadMore()) {
            mLoadAdapter.setLoading(true);
            mLoadAdapter.notifyItemChanged(mLoadAdapter.getItemCount() - 1);
            AppService.getInstance().loadMoreNews(getTaskId(),Constant.NEWSTYPE.RECENT.getNewsType(), mNewsId);
        }
    }


    public void onEventMainThread(NewsEvent newsEvent) {
        if(newsEvent!=null&&Constant.NEWSTYPE.RECENT.getNewsType().equals(newsEvent.getNewsType())) {

            updateView(newsEvent);
        }
    }


    @Override
    protected int getTitle() {
        return R.string.main;
    }

    private void startIntoAnimation() {

        int actionbarSize = NumericalUtil.dp2px(56);
        mToolBar.setTranslationY(-actionbarSize);
        mainTitle.setTranslationY(-actionbarSize);

        mToolBar.animate()
                .translationY(0)
                .setDuration(ANIM_DURATION_TOOLBAR)
                .setStartDelay(300);
        mainTitle.animate()
                .translationY(0)
                .setDuration(ANIM_DURATION_TOOLBAR)
                .setStartDelay(400)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        // startContentAnimation();
                        initCaChe();
                    }
                }).start();
    }

}
