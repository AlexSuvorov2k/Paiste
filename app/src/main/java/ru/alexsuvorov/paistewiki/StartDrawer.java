package ru.alexsuvorov.paistewiki;

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
import android.view.MenuItem;

import com.google.android.gms.ads.MobileAds;

import java.util.Locale;

import ru.alexsuvorov.paistewiki.fragments.AboutAppFragment;
import ru.alexsuvorov.paistewiki.fragments.CymbalsFragment;
import ru.alexsuvorov.paistewiki.fragments.NewsFragment;
import ru.alexsuvorov.paistewiki.tools.AppPreferences;

public class StartDrawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Fragment fragment;
    AppPreferences appPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appPreferences = new AppPreferences(this);
        Locale locale;
        locale = new Locale(appPreferences.getText("choosed_lang"));
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        this.getBaseContext().getResources().updateConfiguration(config,
                this.getBaseContext().getResources().getDisplayMetrics());
        MobileAds.initialize(getApplicationContext(), "ca-app-pub-3940256099942544/6300978111");
        setContentView(R.layout.start_drawer);

        if (savedInstanceState == null) {
            fragment = new NewsFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
        } else {
            fragment = getSupportFragmentManager()
                    .findFragmentByTag("last_fragment");
        }
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);
        //setTitle(R.string.nav_header_newsbutton);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_news) {
            fragment = new NewsFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
        /*} else if (id == R.id.nav_artists) {
            fragmentClass = ArtistsFragment.class;*/
        } else if (id == R.id.nav_cymbals) {
            fragment = new CymbalsFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
        } else if (id == R.id.nav_about) {
            fragment = new AboutAppFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
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
        Locale.setDefault(new Locale(appPreferences.getText("choosed_lang")));
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        newConfig.locale = new Locale(appPreferences.getText("choosed_lang"));
        // your code here, you can use newConfig.locale if you need to check the language
        // or just re-set all the labels to desired string resource
    }

}