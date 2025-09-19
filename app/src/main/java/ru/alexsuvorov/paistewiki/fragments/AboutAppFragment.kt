package ru.alexsuvorov.paistewiki.fragments

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import ru.alexsuvorov.paistewiki.AppParams
import ru.alexsuvorov.paistewiki.AppParams.getLangLabel
import ru.alexsuvorov.paistewiki.R
import ru.alexsuvorov.paistewiki.tools.AppPreferences
import ru.alexsuvorov.paistewiki.tools.NewsService
import java.util.Locale

class AboutAppFragment : Fragment() {
    var BuildConfigStr: String = "27.02.2019"
    var appPreferences: AppPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        appPreferences = AppPreferences(getActivity()!!.getApplicationContext())
        val locale = Locale(appPreferences!!.getText("choosed_lang"))
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        context.getResources().updateConfiguration(
            config,
            context.getResources().getDisplayMetrics()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_about, container, false)
        appPreferences = AppPreferences(getActivity()!!.getApplicationContext())
        val tvLang2 = view.findViewById<TextView>(R.id.tvLang2)
        val cbNotify = view.findViewById<Switch>(R.id.tvNotifications2)
        cbNotify.setChecked(appPreferences!!.getText("enable_notifications") == "1")
        val llNotifications = view.findViewById<LinearLayout>(R.id.notifications_button)
        llNotifications.setOnClickListener(View.OnClickListener { v: View? ->
            if (appPreferences!!.getText("enable_notifications") == "1") {
                appPreferences!!.saveText("enable_notifications", "0")
                getActivity()!!.stopService(Intent(getActivity(), NewsService::class.java))
                cbNotify.setChecked(false)
            } else {
                appPreferences!!.saveText("enable_notifications", "1")
                val newsService = Intent(getActivity(), NewsService::class.java)
                getActivity()!!.startService(newsService)
                cbNotify.setChecked(true)
            }
        })


        val lang = appPreferences!!.getText("choosed_lang")
        var count = 0
        for (item in AppParams.LANG) {
            if (item == lang) {
                tvLang2.setText(getLangLabel(getActivity()!!.getApplicationContext(), count))
                break
            }
            count++
        }
        /*TextView version = view.findViewById(R.id.versionNumber);
        Resources res = getResources();
        String text = String.format(res.getString(R.string.version_string), BuildConfig.VERSION_NAME, BuildConfigStr);
        version.setText(text);*/
        (view.findViewById<View?>(R.id.language_button)).setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val dialogFragment: DialogFragment = LangDialogFragment()
                dialogFragment.show(getActivity()!!.getSupportFragmentManager(), "dialogFragmentLang")
            }
        })

        (view.findViewById<View?>(R.id.helpLangBtn)).setOnClickListener(View.OnClickListener { v: View? ->
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.setData(Uri.parse("mailto: alexsuvorov2k@gmail.com"))
            intent.putExtra(Intent.EXTRA_SUBJECT, "Paiste Wiki")
            if (intent.resolveActivity(getActivity()!!.getPackageManager()) != null) {
                startActivity(intent)
            }
        })
        return view
    }

    override fun onResume() {
        super.onResume()
        getActivity()!!.setTitle(R.string.nav_header_about)
    }
}