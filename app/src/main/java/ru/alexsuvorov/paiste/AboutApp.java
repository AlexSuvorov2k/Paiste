package ru.alexsuvorov.paiste;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class AboutApp extends Fragment {

    String versionName = BuildConfig.VERSION_NAME;
    int versionCode = BuildConfig.VERSION_CODE;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.about, container, false);


        TextView version = (TextView) view.findViewById(R.id.versionAbout);
        version.setText("Version: "+versionName);
        return view;
    }

    public void openBrowser(View view){

        String url = (String)view.getTag();

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(Uri.parse(url));

        startActivity(intent);
    }

}