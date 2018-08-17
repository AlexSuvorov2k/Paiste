package ru.alexsuvorov.paistewiki.fragments;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import ru.alexsuvorov.paistewiki.R;

public class SeriesDescriptionFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cymbal_description, container, false);
        Bundle bundle = getArguments();
        if (bundle != null) {
            int position = bundle.getInt("cymbalseries_id");

            TypedArray cymbalSeriesPicsArr = getResources().obtainTypedArray(R.array.cymbalSeriesPics);
            ImageView img = view.findViewById(R.id.seriesLogo);
            img.setImageDrawable(cymbalSeriesPicsArr.getDrawable(position));

            cymbalSeriesPicsArr.recycle();

            //Series Name
            String[] cymbalSeriesNamesArr = getResources().getStringArray(R.array.cymbalSeriesNames);
            getActivity().setTitle(cymbalSeriesNamesArr[position]);

            //Описание серии
            String[] cymbalSeriesDescriptionArr = getResources().getStringArray(R.array.cymbalSeriesDescription);
            TextView seriesDescription = view.findViewById(R.id.seriesDescriptionText);
            seriesDescription.setText(Html.fromHtml(cymbalSeriesDescriptionArr[position]));
        }
        return view;
    }

}