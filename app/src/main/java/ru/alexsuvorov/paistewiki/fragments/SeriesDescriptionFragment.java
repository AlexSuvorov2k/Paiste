package ru.alexsuvorov.paistewiki.fragments;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

import ru.alexsuvorov.paistewiki.R;
import ru.alexsuvorov.paistewiki.db.AppDatabase;
import ru.alexsuvorov.paistewiki.db.dao.CymbalDao;
import ru.alexsuvorov.paistewiki.tools.AppPreferences;

public class SeriesDescriptionFragment extends Fragment {

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
        View view = inflater.inflate(R.layout.cymbal_description, container, false);
        Bundle bundle = getArguments();
        Context context = getContext();
        if (bundle != null) {
            int position = bundle.getInt("cymbalseries_id");
            final AppDatabase db = AppDatabase.getDatabase(context);
            final CymbalDao cymbalDao = db.cymbalDao();
            ImageView seriesImage = view.findViewById(R.id.seriesLogo);
            int imageId = context.getResources().getIdentifier(cymbalDao.getById(position).getSeriesImage(), "drawable", context.getPackageName());
            seriesImage.setImageResource(imageId);

            //Series Name
            getActivity().setTitle(cymbalDao.getById(position).getCymbalName());

            //Описание серии
            TextView seriesDescription = view.findViewById(R.id.seriesDescriptionText);
            seriesDescription.setText(cymbalDao.getById(position).getSeriesDescription());
            TextView seriesApplication = view.findViewById(R.id.seriesDescriptionApplicationText);
            seriesApplication.setText(cymbalDao.getById(position).getSeriesDescriptionApplication());
            TextView seriesDescriptionSince = view.findViewById(R.id.seriesDescriptionSince);
            seriesDescriptionSince.setText(cymbalDao.getById(position).getSeriesDescriptionSince());
            TextView seriesDescriptionSound = view.findViewById(R.id.seriesDescriptionSound);
            seriesDescriptionSound.setText(cymbalDao.getById(position).getSeriesDescriptionSound());
            TextView seriesDescriptionAlloy = view.findViewById(R.id.seriesDescriptionAlloy);
            seriesDescriptionAlloy.setText(cymbalDao.getById(position).getSeriesDescriptionAlloy());
        }
        return view;
    }

}