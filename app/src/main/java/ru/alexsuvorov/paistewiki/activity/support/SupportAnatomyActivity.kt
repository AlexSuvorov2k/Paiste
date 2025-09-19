package ru.alexsuvorov.paistewiki.activity.support

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.alexsuvorov.paistewiki.App
import ru.alexsuvorov.paistewiki.R
import ru.alexsuvorov.paistewiki.adapter.AnatomyAdapter
import ru.alexsuvorov.paistewiki.db.AppDatabase

class SupportAnatomyActivity : AppCompatActivity() {
    var toolbar: Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_support_anatomy)
        (getApplication() as App).setLocale()
        val rvAnatomy = findViewById<RecyclerView>(R.id.rv_basic_anatomy)
        rvAnatomy.setHasFixedSize(true)
        rvAnatomy.setLayoutManager(LinearLayoutManager(this))
        rvAnatomy.setItemAnimator(DefaultItemAnimator())
        rvAnatomy.setAdapter(AnatomyAdapter(this.dataList, this))

        toolbar = findViewById<Toolbar?>(R.id.toolbar)
        setSupportActionBar(toolbar)
        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)
    }

    private val dataList: ArrayList<Any?>
        get() {
            val db: AppDatabase = AppDatabase.Companion.getDatabase(this)
            val supportDao = db.supportAnatomyDao()!!

            val items = java.util.ArrayList<Any?>()
            items.add(getString(R.string.support_anatomy_basic_title))
            items.addAll(supportDao.basicAnatomy!!)
            items.add(getResources().getDrawable(R.drawable.cymbal_anatomy_content_image))
            items.add(getString(R.string.support_anatomy_types_title))
            items.addAll(supportDao.cymbalTypes!!)
            items.add(getString(R.string.support_anatomy_characteristics_title))
            items.addAll(supportDao.characteristics!!)
            items.add(getResources().getDrawable(R.drawable.cymbal_characteristics_content_image))
            items.add(getString(R.string.support_anatomy_drumbasics_title))
            items.addAll(supportDao.drumstickBasics!!)
            return items
        }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    public override fun onResume() {
        super.onResume()
        this.setTitle(R.string.support_anatomy_cymbal_title)
    }
}