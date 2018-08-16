package ru.alexsuvorov.paistewiki.fragments;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import ru.alexsuvorov.paistewiki.BuildConfig;
import ru.alexsuvorov.paistewiki.R;

public class AboutAppFragment extends Fragment {

    String BuildConfigStr = "12.08.2018";

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.about, container, false);

        AdView mAdView = view.findViewById(R.id.AdMob_aboutApp);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        MobileAds.initialize(getContext(), "ca-app-pub-3940256099942544/6300978111");

        TextView version = view.findViewById(R.id.versionNumber);
        Resources res = getResources();
        String text = String.format(res.getString(R.string.version_string), BuildConfig.VERSION_NAME, BuildConfigStr);
        version.setText(text);
        return view;
    }
}