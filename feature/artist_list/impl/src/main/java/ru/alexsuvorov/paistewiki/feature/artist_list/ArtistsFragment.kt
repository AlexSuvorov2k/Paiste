package ru.alexsuvorov.paistewiki.feature.artist_list

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.alexsuvorov.paistewiki.tools.AppPreferences

class ArtistsFragment : Fragment() {

    private var appPreferences: AppPreferences? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        appPreferences = AppPreferences(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_artists, container, false)
    }
}