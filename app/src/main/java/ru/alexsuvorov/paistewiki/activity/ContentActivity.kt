package ru.alexsuvorov.paistewiki.activity

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.core.view.get
import androidx.core.view.isNotEmpty
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.navigation.NavigationView
import ru.alexsuvorov.paistewiki.R
import ru.alexsuvorov.paistewiki.feature.about.AboutAppFragment
import ru.alexsuvorov.paistewiki.feature.product_list.CymbalsFragment
import ru.alexsuvorov.paistewiki.feature.product_list.activity.SeriesDescriptionActivity
import ru.alexsuvorov.paistewiki.feature.support.SupportFragment
import ru.alexsuvorov.paistewiki.tools.AppPreferences

class ContentActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, CymbalsFragment.CymbalsNavigation {

    private var fragment: Fragment? = null
    private lateinit var fragmentManager: FragmentManager
    private lateinit var appPreferences: AppPreferences
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var drawer: DrawerLayout
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appPreferences = AppPreferences(this)
        fragmentManager = supportFragmentManager

        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        drawer = findViewById(R.id.drawer_layout)
        toggle = ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        if (savedInstanceState == null) {
            fragment = CymbalsFragment()
            fragmentManager.beginTransaction().replace(R.id.container, fragment!!, "last_fragment").commit()
            if (navigationView.menu.isNotEmpty()) {
                navigationView.menu[0].isChecked = true
                title = navigationView.menu[0].title
            }
        } else {
            fragment = supportFragmentManager.findFragmentByTag("last_fragment")
        }
    }

    override fun onBackPressed() {
        drawer = findViewById(R.id.drawer_layout)
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_cymbals -> {
                fragment = CymbalsFragment()
                fragmentManager.beginTransaction().replace(R.id.container, fragment!!).commit()
            }

            R.id.nav_support -> {
                fragment = SupportFragment()
                fragmentManager.beginTransaction().replace(R.id.container, fragment!!).commit()
            }

            R.id.nav_about -> {
                fragment = AboutAppFragment()
                fragmentManager.beginTransaction().replace(R.id.container, fragment!!).commit()
            }
        }
        setFragmentMisc(item)
        return true
    }

    private fun setFragmentMisc(item: MenuItem) {
        item.isChecked = true
        title = item.title
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        drawer.closeDrawer(GravityCompat.START)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }

    override fun onCymbalSeriesSelected(seriesId: Int?) {
        val intent = Intent(this, SeriesDescriptionActivity::class.java)
        intent.putExtra("cymbalseries_id", seriesId)
        startActivity(intent)
    }
}
