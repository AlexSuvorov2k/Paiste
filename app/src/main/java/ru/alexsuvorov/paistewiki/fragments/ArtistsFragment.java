package ru.alexsuvorov.paistewiki.fragments;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Locale;

import ru.alexsuvorov.paistewiki.R;
import ru.alexsuvorov.paistewiki.tools.AppPreferences;

public class ArtistsFragment extends Fragment {

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

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.artists_fragment, container, false);
    }

}