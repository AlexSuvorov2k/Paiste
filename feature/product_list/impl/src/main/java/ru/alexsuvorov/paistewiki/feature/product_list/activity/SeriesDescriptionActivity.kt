package ru.alexsuvorov.paistewiki.feature.product_list.activity

import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import ru.alexsuvorov.paistewiki.core.database.AppDatabase
import ru.alexsuvorov.paistewiki.feature.product_list.R

class SeriesDescriptionActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_cymbal_description)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val bundle = intent.extras?: return
        val position = bundle.getInt("cymbalseries_id")
        val db = AppDatabase.getDatabase(this)
        val cymbalDao = db.cymbalDao()
        val cymbal = cymbalDao.getById(position) ?: return

        val seriesImage = findViewById<ImageView>(R.id.seriesLogo)
        val imageId = resources.getIdentifier(cymbal.seriesImage, "drawable", packageName)
        seriesImage.setImageResource(imageId)

        title = cymbal.cymbalName

        findViewById<TextView>(R.id.seriesDescriptionText).text = cymbal.seriesDescription
        findViewById<TextView>(R.id.seriesDescriptionApplicationText).text = cymbal.seriesDescriptionApplication
        findViewById<TextView>(R.id.seriesDescriptionSince).text = cymbal.seriesDescriptionSince
        findViewById<TextView>(R.id.seriesDescriptionSound).text = cymbal.seriesDescriptionSound
        findViewById<TextView>(R.id.seriesDescriptionAlloy).text = cymbal.seriesDescriptionAlloy
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressedDispatcher.onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
