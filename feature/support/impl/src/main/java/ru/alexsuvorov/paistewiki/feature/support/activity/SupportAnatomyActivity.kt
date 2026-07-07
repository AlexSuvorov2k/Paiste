package ru.alexsuvorov.paistewiki.feature.support.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.alexsuvorov.paistewiki.core.database.AppDatabase
import ru.alexsuvorov.paistewiki.feature.support.R
import ru.alexsuvorov.paistewiki.feature.support.adapter.AnatomyAdapter

class SupportAnatomyActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_support_anatomy)

        val rvAnatomy = findViewById<RecyclerView>(R.id.rv_basic_anatomy)
        rvAnatomy.setHasFixedSize(true)
        rvAnatomy.layoutManager = LinearLayoutManager(this)
        rvAnatomy.itemAnimator = DefaultItemAnimator()
        rvAnatomy.adapter = AnatomyAdapter(getDataList(), this)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun getDataList(): ArrayList<Any?> {
        val db = AppDatabase.getDatabase(this)
        val supportDao = db.supportAnatomyDao()

        val items = ArrayList<Any?>()
        items.add(getString(R.string.support_anatomy_basic_title))
        items.addAll(supportDao.getBasicAnatomy())
        items.add(ContextCompat.getDrawable(this, R.drawable.cymbal_anatomy_content_image))
        items.add(getString(R.string.support_anatomy_types_title))
        items.addAll(supportDao.getCymbalTypes())
        items.add(getString(R.string.support_anatomy_characteristics_title))
        items.addAll(supportDao.getCharacteristics())
        items.add(ContextCompat.getDrawable(this, R.drawable.cymbal_characteristics_content_image))
        items.add(getString(R.string.support_anatomy_drumbasics_title))
        items.addAll(supportDao.getDrumstickBasics())
        return items
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.getItemId() == android.R.id.home) {
            onBackPressedDispatcher.onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        setTitle(R.string.support_anatomy_cymbal_title)
    }
}
