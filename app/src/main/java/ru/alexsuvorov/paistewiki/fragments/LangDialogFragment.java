package ru.alexsuvorov.paistewiki.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.Locale;

import ru.alexsuvorov.paistewiki.App;
import ru.alexsuvorov.paistewiki.AppParams;
import ru.alexsuvorov.paistewiki.R;
import ru.alexsuvorov.paistewiki.SplashActivity;
import ru.alexsuvorov.paistewiki.db.AppDatabase;
import ru.alexsuvorov.paistewiki.tools.AppPreferences;

public class LangDialogFragment extends DialogFragment {

    View view;
    AppPreferences appPreferences;
    RadioGroup radioGroup;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        view = getActivity().getLayoutInflater().inflate(R.layout.dialog_lang, null);
        appPreferences = new AppPreferences(getActivity().getApplicationContext());
        radioGroup = view.findViewById(R.id.radiogroup_theme);

        int count = 0;
        for (String lang : AppParams.LANG) {
            RadioButton radioButton = new RadioButton(getActivity().getApplicationContext());
            radioButton.setTextSize(16);
            radioButton.setText(AppParams.getLangLabel(getActivity().getApplicationContext(), count));
            radioButton.setTextColor(getActivity().getResources().getColor(R.color.black));
            radioButton.setTag(count);
            radioGroup.addView(radioButton);
            if (appPreferences.getText("choosed_lang").equals(lang)) {
                ((RadioButton) radioGroup.getChildAt(count)).setChecked(true);
            }
            count++;
        }
        return new AlertDialog.Builder(getActivity())
                .setTitle(getString(R.string.choose_lang))
                .setPositiveButton(getString(R.string.button_ok),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                Resources res = getActivity().getResources();
                                DisplayMetrics dm = res.getDisplayMetrics();
                                Configuration conf = res.getConfiguration();
                                //For language regions like uk_rUA
                                if (AppParams.LANG[(int) radioGroup.findViewById(radioGroup.getCheckedRadioButtonId()).getTag()].length() > 2) {
                                    try {
                                        String[] arrLang = AppParams.LANG[(int) radioGroup.findViewById(radioGroup.getCheckedRadioButtonId()).getTag()].split("_");
                                        conf.locale = new Locale(arrLang[0], arrLang[1]);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    conf.locale = new Locale(AppParams.LANG[(int) radioGroup.findViewById(radioGroup.getCheckedRadioButtonId()).getTag()]);
                                    //Log.d(getClass().getSimpleName(), "LANGUAGE" + AppParams.LANG[(int) radioGroup.findViewById(radioGroup.getCheckedRadioButtonId()).getTag()]);
                                }
                                //Log.d(getClass().getSimpleName(), "LANGUAGE " + AppParams.LANG[(int) radioGroup.findViewById(radioGroup.getCheckedRadioButtonId()).getTag()]);
                                res.updateConfiguration(conf, dm);
                                Locale.setDefault(new Locale(AppParams.LANG[(int) radioGroup.findViewById(radioGroup.getCheckedRadioButtonId()).getTag()]));
                                appPreferences.saveText("choosed_lang", AppParams.LANG[(int) radioGroup.findViewById(radioGroup.getCheckedRadioButtonId()).getTag()]);
                                ((App) getActivity().getApplication()).setLocale();
                                AppDatabase.closeDatabase(getContext());

                                Intent refresh = new Intent(getContext(), SplashActivity.class);
                                startActivity(refresh);
                                getActivity().finish();
                            }
                        }
                )
                .setNegativeButton(getString(R.string.button_cancel),
                        (dialog, whichButton) -> dialog.dismiss()
                )
                .setView(view).create();
    }

    @Override
    public void onConfigurationChanged(Configuration conf) {
        super.onConfigurationChanged(conf);
        Locale.setDefault(new Locale(AppParams.LANG[(int) radioGroup.findViewById(radioGroup.getCheckedRadioButtonId()).getTag()]));
    }
}