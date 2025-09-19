package ru.alexsuvorov.paistewiki.activity

import android.content.res.Configuration
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.navigation.NavigationView
import ru.alexsuvorov.paistewiki.App
import ru.alexsuvorov.paistewiki.R
import ru.alexsuvorov.paistewiki.fragments.AboutAppFragment
import ru.alexsuvorov.paistewiki.fragments.CymbalsFragment
import ru.alexsuvorov.paistewiki.fragments.InstagramViewerFragment
import ru.alexsuvorov.paistewiki.fragments.SupportFragment
import ru.alexsuvorov.paistewiki.tools.AppPreferences

class ContentActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private var fragment: Fragment? = null
    private var fragmentManager: FragmentManager? = null
    var appPreferences: AppPreferences? = null
    var toggle: ActionBarDrawerToggle? = null
    var drawer: DrawerLayout? = null
    var toolbar: Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appPreferences = AppPreferences(this)
        fragmentManager = getSupportFragmentManager()
        (getApplication() as App).setLocale()

        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            fragment = CymbalsFragment()
            val fragmentManager = getSupportFragmentManager()
            fragmentManager.beginTransaction().replace(R.id.container, fragment!!).commit()
        } else {
            fragment = getSupportFragmentManager()
                .findFragmentByTag("last_fragment")
        }
        toolbar = findViewById<Toolbar?>(R.id.toolbar)
        setSupportActionBar(toolbar)
        drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        toggle = ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer!!.addDrawerListener(toggle!!)
        toggle!!.syncState()

        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)
        navigationView.getMenu().clear()
        navigationView.inflateMenu(R.menu.menu_activity_main)
        navigationView.getMenu().getItem(0).setChecked(true)
    }

    override fun onBackPressed() {
        drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        if (drawer!!.isDrawerOpen(GravityCompat.START)) {
            drawer!!.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id = item.getItemId()
        /*if (id == R.id.nav_news) {
            fragment = new NewsFragment();
            fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
        } else */
        if (id == R.id.nav_cymbals) {
            fragment = CymbalsFragment()
            fragmentManager!!.beginTransaction().replace(R.id.container, fragment!!).commit()
        } else if (id == R.id.nav_support) {
            fragment = SupportFragment()
            fragmentManager!!.beginTransaction().replace(R.id.container, fragment!!).commit()
        } else if (id == R.id.nav_instagram) {
            fragment = InstagramViewerFragment()
            fragmentManager!!.beginTransaction().replace(R.id.container, fragment!!).commit()
        } else if (id == R.id.nav_about) {
            fragment = AboutAppFragment()
            fragmentManager!!.beginTransaction().replace(R.id.container, fragment!!).commit()
        }
        setFragmentMisc(item)
        return true
    }

    fun setFragmentMisc(item: MenuItem) {
        item.setChecked(true)
        setTitle(item.getTitle())
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        drawer.closeDrawer(GravityCompat.START)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        (getApplication() as App).setLocale()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        (getApplication() as App).setLocale()
        this.recreate()
    }
}