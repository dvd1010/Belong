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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

public class MainActivity extends AppCompatActivity implements NavigationDrawerCallbacks,
        OnDrawerCloseRequestListener {

    private NavigationDrawerFragment mNavigationDrawerFragment;
    private DrawerLayout drawerLayout;
    private RelativeLayout leftDrawer;
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
        leftDrawer = (RelativeLayout) findViewById(R.id.left_drawer);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawers();
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mNavigationDrawerFragment.setUp(R.id.left_drawer, drawerLayout, toolbar);

        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
        super.onNewIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //use the query to search your data somehow
        }
    }

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
        getMenuInflater().inflate(R.menu.menu_search, menu);
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String text = newText;
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.search) {
            Intent intent = new Intent(this, SearchActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }*/
}
