package ru.alexsuvorov.paistewiki.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.alexsuvorov.paistewiki.App;
import ru.alexsuvorov.paistewiki.R;
import ru.alexsuvorov.paistewiki.adapter.SupportAdapter;
import ru.alexsuvorov.paistewiki.db.AppDatabase;
import ru.alexsuvorov.paistewiki.db.dao.SupportDao;
import ru.alexsuvorov.paistewiki.model.SupportModel;
import ru.alexsuvorov.paistewiki.tools.AppPreferences;
import ru.alexsuvorov.paistewiki.tools.Utils;

public class SupportFragment extends Fragment {

    RecyclerView supportView;
    AppPreferences appPreferences;
    List<SupportModel> supportList = new ArrayList<>();
    SupportAdapter supportAdapter;
    SupportDao supportDao;
    AppDatabase db;
    Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        appPreferences = new AppPreferences(context);
        this.context = context;
        ((App) getActivity().getApplication()).setLocale();
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_support, container, false);

        supportView = view.findViewById(R.id.supportList);
        supportView.setNestedScrollingEnabled(false);
        supportView.setHasFixedSize(false);
        supportView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        db = AppDatabase.getDatabase(context);
        supportDao = db.supportDao();
        supportList = supportDao.getSupportList();
        if (Utils.checkIsTablet(context) && Utils.checkIsLandscape(context)) {
            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(context, 2);
            supportView.setLayoutManager(layoutManager);
        }
        supportAdapter = new SupportAdapter(supportList, context, new SupportAdapter.SupportCallback() {
            @Override
            public void onClick(int position) {

            }
        });
        supportView.setAdapter(supportAdapter);
        supportAdapter.notifyDataSetChanged();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(R.string.nav_header_supportbutton);
    }
}
