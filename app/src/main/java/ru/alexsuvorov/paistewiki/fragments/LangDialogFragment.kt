package ru.alexsuvorov.paistewiki.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.fragment.app.DialogFragment
import ru.alexsuvorov.paistewiki.App
import ru.alexsuvorov.paistewiki.AppParams
import ru.alexsuvorov.paistewiki.AppParams.getLangLabel
import ru.alexsuvorov.paistewiki.R
import ru.alexsuvorov.paistewiki.SplashActivity
import ru.alexsuvorov.paistewiki.db.AppDatabase
import ru.alexsuvorov.paistewiki.tools.AppPreferences
import java.util.Locale

class LangDialogFragment : DialogFragment() {

    var appPreferences: AppPreferences? = null
    var radioGroup: RadioGroup? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        appPreferences = AppPreferences(getActivity()!!.getApplicationContext())
        radioGroup = view?.findViewById<RadioGroup>(R.id.radiogroup_theme)

        var count = 0
        for (lang in AppParams.LANG) {
            val radioButton = RadioButton(getActivity()!!.getApplicationContext())
            radioButton.setTextSize(16f)
            radioButton.setText(getLangLabel(getActivity()!!.getApplicationContext(), count))
            radioButton.setTextColor(getActivity()!!.getResources().getColor(R.color.black))
            radioButton.setTag(count)
            radioGroup!!.addView(radioButton)
            if (appPreferences!!.getText("choosed_lang") == lang) {
                (radioGroup!!.getChildAt(count) as RadioButton).setChecked(true)
            }
            count++
        }
        return AlertDialog.Builder(getActivity())
            .setTitle(getString(R.string.choose_lang))
            .setPositiveButton(
                getString(R.string.button_ok),
                object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, whichButton: Int) {
                        val res = getActivity()!!.getResources()
                        val dm = res.getDisplayMetrics()
                        val conf = res.getConfiguration()
                        //For language regions like uk_rUA
                        if (AppParams.LANG[radioGroup!!.findViewById<View?>(radioGroup!!.getCheckedRadioButtonId()).getTag() as Int]!!.length > 2) {
                            try {
                                val arrLang: Array<String?> =
                                    AppParams.LANG[radioGroup!!.findViewById<View?>(radioGroup!!.getCheckedRadioButtonId()).getTag() as Int]!!.split("_".toRegex()).dropLastWhile { it.isEmpty() }
                                        .toTypedArray()
                                conf.locale = Locale(arrLang[0], arrLang[1])
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        } else {
                            conf.locale = Locale(AppParams.LANG[radioGroup!!.findViewById<View?>(radioGroup!!.getCheckedRadioButtonId()).getTag() as Int])
                            //Log.d(getClass().getSimpleName(), "LANGUAGE" + AppParams.LANG[(int) radioGroup.findViewById(radioGroup.getCheckedRadioButtonId()).getTag()]);
                        }
                        //Log.d(getClass().getSimpleName(), "LANGUAGE " + AppParams.LANG[(int) radioGroup.findViewById(radioGroup.getCheckedRadioButtonId()).getTag()]);
                        res.updateConfiguration(conf, dm)
                        Locale.setDefault(Locale(AppParams.LANG[radioGroup!!.findViewById<View?>(radioGroup!!.getCheckedRadioButtonId()).getTag() as Int]))
                        appPreferences!!.saveText("choosed_lang", AppParams.LANG[radioGroup!!.findViewById<View?>(radioGroup!!.getCheckedRadioButtonId()).getTag() as Int])
                        (getActivity()!!.getApplication() as App).setLocale()
                        AppDatabase.Companion.closeDatabase(getContext())

                        val refresh = Intent(getContext(), SplashActivity::class.java)
                        startActivity(refresh)
                        getActivity()!!.finish()
                    }
                }
            )
            .setNegativeButton(
                getString(R.string.button_cancel),
                DialogInterface.OnClickListener { dialog: DialogInterface?, whichButton: Int -> dialog!!.dismiss() }
            )
            .setView(view).create()
    }

    override fun onConfigurationChanged(conf: Configuration) {
        super.onConfigurationChanged(conf)
        Locale.setDefault(Locale(AppParams.LANG[radioGroup!!.findViewById<View?>(radioGroup!!.getCheckedRadioButtonId()).getTag() as Int]))
    }
}