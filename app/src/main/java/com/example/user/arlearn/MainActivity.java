package com.example.user.arlearn;

import android.app.ActionBar;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private Fragment currentFragment = null;
    private FragmentTransaction ft;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    currentFragment = new HomeFragment();
                    ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content, currentFragment);
                    ft.commit();
                    findViewById(R.id.message).setVisibility(View.VISIBLE);
                    findViewById(R.id.message2).setVisibility(View.GONE);
                    return true;
                case R.id.navigation_discover:
                    currentFragment = new DiscoverFragment();
                    ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content, currentFragment);
                    ft.commit();
                    findViewById(R.id.message).setVisibility(View.GONE);
                    findViewById(R.id.message2).setVisibility(View.GONE);
                    return true;
                case R.id.navigation_favoutite:
                    currentFragment = new FavouriteFragment();
                    ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content, currentFragment);
                    ft.commit();
                    findViewById(R.id.message).setVisibility(View.GONE);
                    findViewById(R.id.message2).setVisibility(View.GONE);
                    return true;
                case R.id.navigation_setting:
                    currentFragment = new SettingFragment();
                    ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content, currentFragment);
                    ft.commit();
                    findViewById(R.id.message).setVisibility(View.GONE);
                    findViewById(R.id.message2).setVisibility(View.GONE);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        ft = getSupportFragmentManager().beginTransaction();
        currentFragment = new HomeFragment();
        ft.replace(R.id.content, currentFragment);
        ft.commit();

        if (!isConnected()) {
            Toast.makeText(getApplicationContext(), "No network", Toast.LENGTH_LONG).show();
        }

    }

    private boolean isConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if(id == R.id.app_bar_search)
        {
            Intent intent = new Intent(MainActivity.this,SearchResultActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

}


