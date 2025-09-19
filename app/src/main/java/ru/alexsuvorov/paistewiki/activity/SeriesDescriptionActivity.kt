package ru.alexsuvorov.paistewiki.activity

import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import ru.alexsuvorov.paistewiki.App
import ru.alexsuvorov.paistewiki.R
import ru.alexsuvorov.paistewiki.db.AppDatabase

class SeriesDescriptionActivity : AppCompatActivity() {
    var toolbar: Toolbar? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (getApplication() as App).setLocale()
        setContentView(R.layout.fragment_cymbal_description)
        toolbar = findViewById<Toolbar?>(R.id.toolbar)
        setSupportActionBar(toolbar)
        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)

        val bundle = getIntent().getExtras()
        if (bundle != null) {
            val position = bundle.getInt("cymbalseries_id")
            val db: AppDatabase = AppDatabase.Companion.getDatabase(this)
            val cymbalDao = db.cymbalDao()!!
            val seriesImage = findViewById<ImageView>(R.id.seriesLogo)

            val item = cymbalDao.getById(position)!!

            val imageId = getResources().getIdentifier(item.seriesImage, "drawable", getPackageName())
            seriesImage.setImageResource(imageId)

            //Series Name
            this.setTitle(item.getCymbalName())

            //Описание серии
            val seriesDescription = findViewById<TextView>(R.id.seriesDescriptionText)
            seriesDescription.setText(item.seriesDescription)
            val seriesApplication = findViewById<TextView>(R.id.seriesDescriptionApplicationText)
            seriesApplication.setText(item.seriesDescriptionApplication)
            val seriesDescriptionSince = findViewById<TextView>(R.id.seriesDescriptionSince)
            seriesDescriptionSince.setText(item.seriesDescriptionSince)
            val seriesDescriptionSound = findViewById<TextView>(R.id.seriesDescriptionSound)
            seriesDescriptionSound.setText(item.seriesDescriptionSound)
            val seriesDescriptionAlloy = findViewById<TextView>(R.id.seriesDescriptionAlloy)
            seriesDescriptionAlloy.setText(item.seriesDescriptionAlloy)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}