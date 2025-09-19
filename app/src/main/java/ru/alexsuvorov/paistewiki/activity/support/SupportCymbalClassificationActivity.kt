package ru.alexsuvorov.paistewiki.activity.support

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import ru.alexsuvorov.paistewiki.App
import ru.alexsuvorov.paistewiki.R

class SupportCymbalClassificationActivity : AppCompatActivity() {
    var toolbar: Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_support_classification)
        (getApplication() as App).setLocale()

        /*RecyclerView rvAnatomy = findViewById(R.id.rv_basic_anatomy);
        rvAnatomy.setHasFixedSize(true);
        rvAnatomy.setLayoutManager(new LinearLayoutManager(this));
        rvAnatomy.setItemAnimator(new DefaultItemAnimator());
        rvAnatomy.setAdapter(new AnatomyAdapter(getDataList(), this));*/
        toolbar = findViewById<Toolbar?>(R.id.toolbar)
        setSupportActionBar(toolbar)
        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)
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
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    public override fun onResume() {
        super.onResume()
        this.setTitle(R.string.support_classification_title)
    }
}
