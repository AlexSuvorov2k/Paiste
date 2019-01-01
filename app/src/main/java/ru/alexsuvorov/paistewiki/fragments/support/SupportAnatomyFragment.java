package ru.alexsuvorov.paistewiki.fragments.support;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Locale;

import ru.alexsuvorov.paistewiki.R;
import ru.alexsuvorov.paistewiki.adapter.AnatomyAdapter;
import ru.alexsuvorov.paistewiki.db.AppDatabase;
import ru.alexsuvorov.paistewiki.db.dao.SupportAnatomyDao;
import ru.alexsuvorov.paistewiki.tools.AppPreferences;

public class SupportAnatomyFragment extends Fragment {

    private AppPreferences appPreferences;
    private Context context;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_support_anatomy, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        appPreferences = new AppPreferences(context);
        Locale locale = new Locale(appPreferences.getText("choosed_lang"));
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        context.getResources().updateConfiguration(config,
                context.getResources().getDisplayMetrics());
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = this.getContext();

        RecyclerView rvAnatomy = view.findViewById(R.id.rv_basic_anatomy);
        rvAnatomy.setHasFixedSize(true);
        rvAnatomy.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        rvAnatomy.setItemAnimator(new DefaultItemAnimator());
        rvAnatomy.setAdapter(new AnatomyAdapter(getDataList(), context));
        //((ContentActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private ArrayList<Object> getDataList() {
        final AppDatabase db = AppDatabase.getDatabase(context);
        final SupportAnatomyDao supportDao = db.supportAnatomyDao();

        ArrayList<Object> items = new ArrayList<>();
        items.add(getString(R.string.support_anatomy_basic_title));
        items.addAll(supportDao.getBasicAnatomy());
        items.add(context.getResources().getDrawable(R.drawable.cymbal_anatomy_content_image));
        items.add(getString(R.string.support_anatomy_types_title));
        items.addAll(supportDao.getCymbalTypes());
        items.add(getString(R.string.support_anatomy_characteristics_title));
        items.addAll(supportDao.getCharacteristics());
        items.add(context.getResources().getDrawable(R.drawable.cymbal_characteristics_content_image));
        items.add(getString(R.string.support_anatomy_drumbasics_title));
        items.addAll(supportDao.getDrumstickBasics());
        return items;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(R.string.support_anatomy_cymbal_title);
    }
}