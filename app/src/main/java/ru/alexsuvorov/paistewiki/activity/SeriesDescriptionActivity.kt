package ru.alexsuvorov.paistewiki.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import ru.alexsuvorov.paistewiki.App;
import ru.alexsuvorov.paistewiki.R;
import ru.alexsuvorov.paistewiki.db.AppDatabase;
import ru.alexsuvorov.paistewiki.db.dao.CymbalDao;

public class SeriesDescriptionActivity extends AppCompatActivity {

    Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((App) getApplication()).setLocale();
        setContentView(R.layout.fragment_cymbal_description);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            int position = bundle.getInt("cymbalseries_id");
            final AppDatabase db = AppDatabase.getDatabase(this);
            final CymbalDao cymbalDao = db.cymbalDao();
            ImageView seriesImage = findViewById(R.id.seriesLogo);
            int imageId = getResources().getIdentifier(cymbalDao.getById(position).getSeriesImage(), "drawable", getPackageName());
            seriesImage.setImageResource(imageId);

            //Series Name
            this.setTitle(cymbalDao.getById(position).getCymbalName());

            //Описание серии
            TextView seriesDescription = findViewById(R.id.seriesDescriptionText);
            seriesDescription.setText(cymbalDao.getById(position).getSeriesDescription());
            TextView seriesApplication = findViewById(R.id.seriesDescriptionApplicationText);
            seriesApplication.setText(cymbalDao.getById(position).getSeriesDescriptionApplication());
            TextView seriesDescriptionSince = findViewById(R.id.seriesDescriptionSince);
            seriesDescriptionSince.setText(cymbalDao.getById(position).getSeriesDescriptionSince());
            TextView seriesDescriptionSound = findViewById(R.id.seriesDescriptionSound);
            seriesDescriptionSound.setText(cymbalDao.getById(position).getSeriesDescriptionSound());
            TextView seriesDescriptionAlloy = findViewById(R.id.seriesDescriptionAlloy);
            seriesDescriptionAlloy.setText(cymbalDao.getById(position).getSeriesDescriptionAlloy());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}