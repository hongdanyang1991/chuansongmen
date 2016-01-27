package chuansong.me.chuansongmen.ui.fragment.base;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


import butterknife.Bind;
import chuansong.me.chuansongmen.R;

/**
 * Created by HongDanyang on 16/1/24.
 */
public abstract class ToorbarBaseFragment extends BaseFragment {
    @Bind(R.id.toolbar)
    protected
    Toolbar mToolBar;


    protected abstract int getTitle();

    @Override
    protected void initViews() {
        initToolbar();
        setHasOptionsMenu(true);

    }

    protected void initToolbar() {
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolBar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getTitle());
        mToolBar.setNavigationIcon(R.mipmap.ic_menu_white);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action fragment_bar item clicks here. The action fragment_bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.about :
                //AboutActivity.navigateFrom(getActivity());
                break;
            case R.id.setting:
                //SettingsActivity.navigateFrom(getActivity());
                break;
            default:break;

        }
        return super.onOptionsItemSelected(item);
    }


}
