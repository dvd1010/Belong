package com.belonginterview.activity;

import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.RelativeLayout;

import com.belonginterview.R;
import com.belonginterview.fragment.NavigationDrawerFragment;
import com.belonginterview.interfaces.NavigationDrawerCallbacks;
import com.belonginterview.interfaces.OnDrawerCloseRequestListener;

public class MainActivity extends FragmentActivity implements NavigationDrawerCallbacks,
        OnDrawerCloseRequestListener {

    private NavigationDrawerFragment mNavigationDrawerFragment;
    private DrawerLayout drawerLayout;
    public static RelativeLayout leftDrawer;

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
        // Set up the drawer.
        mNavigationDrawerFragment.setUp(R.id.left_drawer, drawerLayout, toolbar);
    }



    @Override
    public void onNavigationDrawerItemSelected(int position) {

    }

    @Override
    public void onDrawerCloseRequest() {

    }
}
