package com.belonginterview.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
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

/*The main Activity which opens on App Launch*/
public class MainActivity extends AppCompatActivity implements NavigationDrawerCallbacks,
        OnDrawerCloseRequestListener {

    private NavigationDrawerFragment mNavigationDrawerFragment;
    private DrawerLayout drawerLayout;
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private String mTitle;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawers();

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mNavigationDrawerFragment.setUp(R.id.left_drawer, drawerLayout, toolbar);

    }


    /*Opens Fragment corresponding to user selection on Navigation Drawer*/
    @Override
    public void onNavigationDrawerItemSelected(final int position) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
                Fragment currentFragment = getCurrentFragment(position);
                mTitle = MenuOptionsEnum.getMenuOption(position).getName();
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportActionBar().setTitle(mTitle);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu) | true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.search:
                startActivity(new Intent(MainActivity.this, SearchActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(Gravity.LEFT)){
            drawerLayout.closeDrawers();
        }else {
            super.onBackPressed();
        }
    }
}
