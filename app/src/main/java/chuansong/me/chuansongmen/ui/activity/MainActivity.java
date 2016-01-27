package chuansong.me.chuansongmen.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.widget.FrameLayout;

import java.util.HashMap;
import java.util.Map;

import chuansong.me.chuansongmen.R;
import chuansong.me.chuansongmen.data.Constant;
import chuansong.me.chuansongmen.event.DrawerClickEvent;
import chuansong.me.chuansongmen.ui.fragment.DrawerFragment;
import chuansong.me.chuansongmen.ui.fragment.MainFragment;
import chuansong.me.chuansongmen.ui.fragment.base.BaseFragment;

/**
 * Created by HongDanyang on 16/1/23.
 */
public class MainActivity extends BaseActivity {

    private DrawerFragment mNavigationFragment;

    private BaseFragment mCurrentFragment;

    private int mCurrentDrawId= R.string.news;

    private Map<String,BaseFragment> mBaseFragmentByName= new HashMap<>();

    private Map<Integer,String> mFragmentNameByDrawerId = new HashMap<>();


    @Override
    protected void initViews() {
        getWindow().setBackgroundDrawable(null);
        mNavigationFragment = (DrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mNavigationFragment.setUp((FrameLayout) findViewById(R.id.main_content),
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.main_drawer));
        initDrawerMap();
        mCurrentFragment= getFragment(mFragmentNameByDrawerId.get(R.string.news));
        transactionSupportFragment(mCurrentFragment);
    }

    public void onEventMainThread(DrawerClickEvent drawerClickEvent) {
        if(Constant.Result.FAIL.equals(drawerClickEvent.getEventResult())||drawerClickEvent.getDrawId()==mCurrentDrawId) {
            return;
        }
        mCurrentDrawId=drawerClickEvent.getDrawId();
        if(mCurrentDrawId==R.string.statistics) {
            //mCurrentFragment=StatisticsFragment.newInstance();
        }else {
            mCurrentFragment = getFragment(mFragmentNameByDrawerId.get(mCurrentDrawId));
        }
        transactionSupportFragment(mCurrentFragment);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    private void initDrawerMap() {
        mFragmentNameByDrawerId.put(R.string.news,MainFragment.class.getName());
    }

    private BaseFragment getFragment(String fragmentName) {
        BaseFragment baseFragment = mBaseFragmentByName.get(fragmentName);
        if(mBaseFragmentByName.get(fragmentName)==null) {
            try {
                baseFragment=(BaseFragment)Class.forName(fragmentName).newInstance();
            } catch (Exception e) {
                baseFragment= MainFragment.newInstance();
            }
            mBaseFragmentByName.put(fragmentName,baseFragment);
        }
        return baseFragment;
    }

    private void transactionSupportFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.main_content, fragment).commit();
    }

    @Override
    public void onBackPressed() {
        if (mNavigationFragment.isDrawerOpen()) {
            mNavigationFragment.closeDrawer();
        } else {
            finish();
        }
    }
}
