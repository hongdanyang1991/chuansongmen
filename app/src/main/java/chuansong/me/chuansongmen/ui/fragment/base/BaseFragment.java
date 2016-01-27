package chuansong.me.chuansongmen.ui.fragment.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import chuansong.me.chuansongmen.app.AppService;

/**
 * Created by HongDanyang on 16/1/23.
 */
public abstract class BaseFragment extends Fragment {

    private int mTaskId;
    protected View rootView;
    protected abstract void initViews() ;

    protected abstract int getContentViewId();

    protected int getTaskId (){
        return mTaskId;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTaskId=getActivity().getTaskId();
        AppService.getBus().register(this);

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(getContentViewId(),container,false);
        ButterKnife.bind(this, rootView);
        initViews();
        return rootView;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        AppService.getBus().unregister(this);
    }
}
