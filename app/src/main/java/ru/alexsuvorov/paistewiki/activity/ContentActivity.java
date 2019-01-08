package ru.alexsuvorov.paistewiki.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.ads.MobileAds;

import ru.alexsuvorov.paistewiki.App;
import ru.alexsuvorov.paistewiki.R;
import ru.alexsuvorov.paistewiki.fragments.AboutAppFragment;
import ru.alexsuvorov.paistewiki.fragments.CymbalsFragment;
import ru.alexsuvorov.paistewiki.fragments.NewsFragment;
import ru.alexsuvorov.paistewiki.fragments.SupportFragment;
import ru.alexsuvorov.paistewiki.tools.AppPreferences;

public class ContentActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Fragment fragment;
    private FragmentManager fragmentManager;
    AppPreferences appPreferences;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawer;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appPreferences = new AppPreferences(this);
        fragmentManager = getSupportFragmentManager();
        ((App) getApplication()).setLocale();

        MobileAds.initialize(getApplicationContext(), "ca-app-pub-3940256099942544/6300978111");
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            fragment = new NewsFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
        } else {
            fragment = getSupportFragmentManager()
                    .findFragmentByTag("last_fragment");
        }
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        /*
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
    if (navigationView != null) {
        Menu menu = navigationView.getMenu();
        menu.findItem(R.id.nav_profile).setTitle("My Account");
        menu.findItem(R.id.nav_mng_task).setTitle("Control Task");
        //menu.findItem(R.id.nav_pkg_manage).setVisible(false);//In case you want to remove menu item
        navigationView.setNavigationItemSelectedListener(this);
    }
         */

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().clear();
        navigationView.inflateMenu(R.menu.menu_activity_main);
        navigationView.getMenu().getItem(0).setChecked(true);
    }

    @Override
    public void onBackPressed() {
        drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!drawer.isDrawerOpen(GravityCompat.START)) {
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }*/

    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_news) {
            fragment = new NewsFragment();
            fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
        } else if (id == R.id.nav_cymbals) {
            fragment = new CymbalsFragment();
            fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
        } else if (id == R.id.nav_support) {
            fragment = new SupportFragment();
            fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
        } else if (id == R.id.nav_about) {
            fragment = new AboutAppFragment();
            fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
        }
        setFragmentMisc(item);
        return true;
    }

    public void setFragmentMisc(MenuItem item) {
        item.setChecked(true);
        setTitle(item.getTitle());
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        ((App) getApplication()).setLocale();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        ((App) getApplication()).setLocale();
        this.recreate();
    }
}