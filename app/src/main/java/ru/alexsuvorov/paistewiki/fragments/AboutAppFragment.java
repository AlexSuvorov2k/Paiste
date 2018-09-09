package ru.alexsuvorov.paistewiki.fragments;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import ru.alexsuvorov.paistewiki.BuildConfig;
import ru.alexsuvorov.paistewiki.R;

public class AboutAppFragment extends Fragment {

    String BuildConfigStr = "09.09.2018";

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.about, container, false);

        MobileAds.initialize(getContext(), "ca-app-pub-3940256099942544/6300978111");
        AdView mAdView = view.findViewById(R.id.AdMob_aboutApp);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        TextView version = view.findViewById(R.id.versionNumber);
        Resources res = getResources();
        String text = String.format(res.getString(R.string.version_string), BuildConfig.VERSION_NAME, BuildConfigStr);
        version.setText(text);
        mAdView.setAdListener(new AdListener() {

            @Override
            public void onAdClicked() {
                super.onAdClicked();
                //getActivity().finish();
                Log.d("About", "finished");
            }

            // Listen for when user closes ad
            public void onAdClosed() {
                // When user closes ad end this activity (go back to first activity)
                /*Intent intent = new Intent(getContext(), Splash.class);
                startActivity(intent);
                getActivity().finish();*/
            }
        });
        return view;
    }

}