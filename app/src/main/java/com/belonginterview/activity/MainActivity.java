package com.belonginterview.activity;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.widget.RelativeLayout;

import com.belonginterview.R;
import com.belonginterview.enums.MenuEnumTagMap;
import com.belonginterview.enums.MenuOptionsEnum;
import com.belonginterview.fragment.NavigationDrawerFragment;
import com.belonginterview.fragment.ProductListFragment;
import com.belonginterview.interfaces.NavigationDrawerCallbacks;
import com.belonginterview.interfaces.OnDrawerCloseRequestListener;
import com.belonginterview.model.Constants;
import com.belonginterview.model.Product;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends FragmentActivity implements NavigationDrawerCallbacks,
        OnDrawerCloseRequestListener {

    private NavigationDrawerFragment mNavigationDrawerFragment;
    private DrawerLayout drawerLayout;
    private RelativeLayout leftDrawer;
    private FragmentManager fragmentManager = getSupportFragmentManager();

    private static final String GET_PRODUCTS_URL = "http://api.buyingiq.com/v1/search/?page={0}&{1}&facet=1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        leftDrawer = (RelativeLayout) findViewById(R.id.left_drawer);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawers();
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mNavigationDrawerFragment.setUp(R.id.left_drawer, drawerLayout, toolbar);
    }



    @Override
    public void onNavigationDrawerItemSelected(final int position) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
                Fragment currentFragment = getCurrentFragment(position);
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, currentFragment)
                        .setTransition(FragmentTransaction.TRANSIT_NONE)
                        .commit();
            }
        });
        executorService.shutdown();
    }

    private Fragment getCurrentFragment(int position){
        ProductListFragment fragment = new ProductListFragment();
        Bundle bundle = new Bundle();
        String apiTag = MenuEnumTagMap.getTagString(MenuOptionsEnum.getMenuOption(position));
        bundle.putString(Constants.API_TAG, apiTag);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onDrawerCloseRequest() {
        if (drawerLayout.isDrawerOpen(Gravity.LEFT)){
            drawerLayout.closeDrawers();
        }
    }

}
