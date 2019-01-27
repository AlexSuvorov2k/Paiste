package ru.alexsuvorov.paistewiki.activity.support;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import ru.alexsuvorov.paistewiki.App;
import ru.alexsuvorov.paistewiki.R;

public class SupportCymbalClassificationActivity extends AppCompatActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support_classification);
        ((App) getApplication()).setLocale();

        /*RecyclerView rvAnatomy = findViewById(R.id.rv_basic_anatomy);
        rvAnatomy.setHasFixedSize(true);
        rvAnatomy.setLayoutManager(new LinearLayoutManager(this));
        rvAnatomy.setItemAnimator(new DefaultItemAnimator());
        rvAnatomy.setAdapter(new AnatomyAdapter(getDataList(), this));*/

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /*private ArrayList<Object> getDataList() {
        final AppDatabase db = AppDatabase.getDatabase(this);
        final SupportAnatomyDao supportDao = db.supportAnatomyDao();

        ArrayList<Object> items = new ArrayList<>();
        items.add(getString(R.string.support_anatomy_basic_title));
        items.addAll(supportDao.getBasicAnatomy());
        items.add(getResources().getDrawable(R.drawable.cymbal_anatomy_content_image));
        items.add(getString(R.string.support_anatomy_types_title));
        items.addAll(supportDao.getCymbalTypes());
        items.add(getString(R.string.support_anatomy_characteristics_title));
        items.addAll(supportDao.getCharacteristics());
        items.add(getResources().getDrawable(R.drawable.cymbal_characteristics_content_image));
        items.add(getString(R.string.support_anatomy_drumbasics_title));
        items.addAll(supportDao.getDrumstickBasics());
        return items;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        this.setTitle(R.string.support_classification_title);
    }
}
