package ru.alexsuvorov.paistewiki.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import ru.alexsuvorov.paistewiki.R;
import ru.alexsuvorov.paistewiki.activity.SeriesDescriptionActivity;
import ru.alexsuvorov.paistewiki.adapter.CymbalsAdapter;
import ru.alexsuvorov.paistewiki.db.AppDatabase;
import ru.alexsuvorov.paistewiki.db.dao.CymbalDao;
import ru.alexsuvorov.paistewiki.model.CymbalSeries;
import ru.alexsuvorov.paistewiki.tools.AppPreferences;

public class CymbalsFragment extends Fragment {

    List<CymbalSeries> cymbalSeries = new ArrayList<>();
    CymbalsAdapter cymbalsAdapter;
    AppDatabase db;
    CymbalDao cymbalDao;
    RecyclerView cymbalsView;
    AppPreferences appPreferences;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setRetainInstance(true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        appPreferences = new AppPreferences(this.getContext());
        Locale locale = new Locale(appPreferences.getText("choosed_lang"));
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        context.getResources().updateConfiguration(config,
                context.getResources().getDisplayMetrics());
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cymbals, container, false);
        Context context = getContext();
        cymbalsView = view.findViewById(R.id.cymbalsList);
        cymbalsView.setNestedScrollingEnabled(false);
        cymbalsView.setHasFixedSize(false);
        cymbalsView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        db = AppDatabase.getDatabase(context);
        cymbalDao = db.cymbalDao();
        cymbalSeries = cymbalDao.getAllProduced(1);
        cymbalsAdapter = new CymbalsAdapter(cymbalSeries, getContext(), cymbalSeries -> {
            Intent intent = new Intent(getActivity(), SeriesDescriptionActivity.class);
            intent.putExtra("cymbalseries_id", cymbalSeries.getCymbalseries_id());
            startActivity(intent);
        });

        cymbalsView.setAdapter(cymbalsAdapter);
        cymbalsAdapter.notifyDataSetChanged();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(R.string.nav_header_cymbalsbutton);
    }
}