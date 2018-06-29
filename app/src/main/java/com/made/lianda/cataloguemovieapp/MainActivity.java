package com.made.lianda.cataloguemovieapp;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private Toolbar toolbar;
    private ViewPager vpMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.app_name);

        vpMain = findViewById(R.id.vp_main);
        setupViewPager(vpMain);

        TabLayout tabMain = findViewById(R.id.tabs_main);
        tabMain.setupWithViewPager(vpMain);

        NavigationView navigationView = findViewById(R.id.nav_main);
        drawerLayout = findViewById(R.id.drawer_layout);

        navigationView.setNavigationItemSelectedListener(this);


    }

    private void setupViewPager(ViewPager viewPager){
        SliderAdapter sliderAdapter = new SliderAdapter(getSupportFragmentManager() );

        sliderAdapter.addFragment(new NowPlayingFragment(), getResources().getString(R.string.now_playing));
        sliderAdapter.addFragment(new UpcomingFragment(), getResources().getString(R.string.upcoming));

        viewPager.setAdapter(sliderAdapter);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_search:
                Intent search = new Intent(getApplicationContext(), SearchMovieActivity.class);
                startActivity(search);
                break;
            case R.id.nav_Account:
                Toast.makeText(getApplicationContext(), R.string.account_page, Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_Language:
                Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(intent);
                break;
            case R.id.nav_Settings:
                Toast.makeText(getApplicationContext(), R.string.setting_page, Toast.LENGTH_SHORT).show();
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    static class SliderAdapter extends FragmentPagerAdapter{

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitle = new ArrayList<>();

        public SliderAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title){

            mFragmentList.add(fragment);
            mFragmentTitle.add(title);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitle.get(position);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.Open_Drawer, R.string.Close_Drawer);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    protected void onPause() {
        super.onPause();

        drawerLayout.removeDrawerListener(toggle);
    }

}
