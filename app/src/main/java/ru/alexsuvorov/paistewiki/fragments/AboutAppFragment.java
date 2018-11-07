package ru.alexsuvorov.paistewiki.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.Locale;

import ru.alexsuvorov.paistewiki.AppParams;
import ru.alexsuvorov.paistewiki.BuildConfig;
import ru.alexsuvorov.paistewiki.R;
import ru.alexsuvorov.paistewiki.tools.AppPreferences;

public class AboutAppFragment extends Fragment {

    String BuildConfigStr = "07.10.2018";
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
        View view = inflater.inflate(R.layout.about, container, false);
        appPreferences = new AppPreferences(getActivity().getApplicationContext());
        AdView mAdView = view.findViewById(R.id.AdMob_aboutApp);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        TextView tvLang2 = view.findViewById(R.id.tvLang2);

        String lang = appPreferences.getText("choosed_lang");
        int count = 0;
        for (String item : AppParams.LANG) {
            if (item.equals(lang)) {
                tvLang2.setText(AppParams.getLangLabel(getActivity().getApplicationContext(), count));
                break;
            }
            count++;
        }
        TextView version = view.findViewById(R.id.versionNumber);
        Resources res = getResources();
        String text = String.format(res.getString(R.string.version_string), BuildConfig.VERSION_NAME, BuildConfigStr);
        version.setText(text);
        (view.findViewById(R.id.language_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFragment = new LangDialogFragment();
                dialogFragment.show(getActivity().getSupportFragmentManager(), "dialogFragmentLang");
            }
        });

        (view.findViewById(R.id.helpLangBtn)).setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto: alexsuvorov2k@gmail.com"));
            intent.putExtra(Intent.EXTRA_SUBJECT, "Paiste Wiki");
            if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                startActivity(intent);
            }
        });

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

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(R.string.nav_header_about);
    }

}