package ru.alexsuvorov.paistewiki.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import ru.alexsuvorov.paistewiki.R;
import ru.alexsuvorov.paistewiki.db.AppDatabase;
import ru.alexsuvorov.paistewiki.db.dao.CymbalDao;

public class SeriesDescriptionFragment extends Fragment {

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