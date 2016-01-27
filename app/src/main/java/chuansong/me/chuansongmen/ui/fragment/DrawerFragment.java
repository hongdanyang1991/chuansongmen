package chuansong.me.chuansongmen.ui.fragment;

import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import butterknife.Bind;
import chuansong.me.chuansongmen.R;
import chuansong.me.chuansongmen.app.AppService;
import chuansong.me.chuansongmen.data.Constant;
import chuansong.me.chuansongmen.event.DrawerClickEvent;
import chuansong.me.chuansongmen.ui.adapter.DrawerAdapter;
import chuansong.me.chuansongmen.ui.fragment.base.BaseFragment;
import chuansong.me.chuansongmen.utils.BitmapUtils;
import chuansong.me.chuansongmen.utils.Blur;

/**
 * Created by HongDanyang on 16/1/23.
 */
public class DrawerFragment extends BaseFragment {

    private static final int DOWNSCALE=8;

    private static final int BLUR_RADIUS=15;

    private ActionBarDrawerToggle mDrawerToggle;

    private DrawerLayout mDrawerLayout;

    private View mFragmentContainerView;

    private Bitmap mBitmap ;

    private Bitmap bitmap ;

    private DrawerClickEvent mDrawerClickEvent;

    @Bind(R.id.rv_drawer)
    RecyclerView mDrawerRv;

    @Bind(R.id.item_head)
    View head;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }



    @Override
    protected void initViews() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mDrawerRv.getContext());
        DrawerAdapter drawerAdapter = new DrawerAdapter(getActivity());
        mDrawerRv.setLayoutManager(linearLayoutManager);
        mDrawerRv.setAdapter(drawerAdapter);
        head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_drawer;
    }

    public boolean isDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mFragmentContainerView);
    }

    public void closeDrawer() {
        mDrawerLayout.closeDrawer(mFragmentContainerView);
    }

    public void setUp( final FrameLayout mContentLayout,int fragmentId,final DrawerLayout drawerLayout) {
        mFragmentContainerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;

        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        mDrawerToggle = new ActionBarDrawerToggle(
                getActivity(),
                mDrawerLayout,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        ) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if(mDrawerClickEvent!=null){
                    mDrawerClickEvent.setEventResult(Constant.Result.SUCCESS);
                    AppService.getBus().post(mDrawerClickEvent);
                }
                getActivity().invalidateOptionsMenu(); // calls onPrepareOptionsMenu()
                if(mBitmap!=null){
                    mBitmap.recycle();
                    mBitmap=null;
                }
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!isAdded()) {
                    return;
                }
                getActivity().invalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                if (Build.VERSION.SDK_INT > 16) {
                    if (mBitmap != null) {
                        bitmap = Bitmap.createBitmap(mBitmap, 0, 0, (int) (mBitmap.getWidth() * slideOffset) > 0 ? (int) (mBitmap.getWidth() * slideOffset) : 1, mBitmap.getHeight());
                        BitmapDrawable bd;
                        bd = new BitmapDrawable(bitmap);
                        drawerView.setBackground(bd);

                    } else {
                        mBitmap = BitmapUtils.drawViewToBitmap(mContentLayout,
                                rootView.getWidth(), rootView.getHeight(), DOWNSCALE, "#42283593");
                        mBitmap = Blur.fastblur(mContentLayout.getContext(), mBitmap, BLUR_RADIUS);
                    }
                }
            }

        };
        // Defer code dependent on restoration of previous instance state.
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

        mDrawerLayout.setDrawerListener(mDrawerToggle);

    }

    public void onEventMainThread(DrawerClickEvent drawerClickEvent) {
        if(Constant.Result.SUCCESS.equals(drawerClickEvent.getEventResult())) {
            return;
        }
        mDrawerClickEvent=drawerClickEvent;
        closeDrawer();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
