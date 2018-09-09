package ru.alexsuvorov.paistewiki.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ru.alexsuvorov.paistewiki.R;
import ru.alexsuvorov.paistewiki.adapter.CymbalsAdapter;
import ru.alexsuvorov.paistewiki.db.AppDatabase;
import ru.alexsuvorov.paistewiki.db.dao.CymbalDao;
import ru.alexsuvorov.paistewiki.model.CymbalSeries;

public class CymbalsFragment extends Fragment {

    List<CymbalSeries> cymbalSeries = new ArrayList<>();
    CymbalsAdapter cymbalsAdapter;
    AppDatabase db;
    CymbalDao cymbalDao;
    RecyclerView cymbalsView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cymbals_fragment, container, false);
        Context context = getContext();
        cymbalsView = view.findViewById(R.id.cymbalsList);
        cymbalsView.setNestedScrollingEnabled(false);
        cymbalsView.setHasFixedSize(false);
        cymbalsView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        db = AppDatabase.getDatabase(context);
        cymbalDao = db.cymbalDao();
        cymbalSeries = cymbalDao.getAllProduced(1);
        cymbalsAdapter = new CymbalsAdapter(cymbalSeries,getContext(), new CymbalsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CymbalSeries cymbalSeries) {
                SeriesDescriptionFragment seriesFragment = new SeriesDescriptionFragment();
                FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
                Bundle bundle = new Bundle();
                bundle.putInt("cymbalseries_id", cymbalSeries.getCymbalseries_id());
                seriesFragment.setArguments(bundle);
                fragmentManager.beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.container, seriesFragment)
                        .commit();
            }
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