package ru.alexsuvorov.paistewiki.fragments

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.alexsuvorov.paistewiki.R
import ru.alexsuvorov.paistewiki.tools.AppPreferences
import java.util.Locale

class ArtistsFragment : Fragment() {

    val appPreferences : AppPreferences by lazy{
        AppPreferences(requireActivity())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setRetainInstance(true);
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        val locale = Locale(appPreferences.getText("choosed_lang"))
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        context.resources.updateConfiguration(
            config,
            context.resources.displayMetrics
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_artists, container, false)
    }
}