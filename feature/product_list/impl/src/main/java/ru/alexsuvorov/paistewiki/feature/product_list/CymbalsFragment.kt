package ru.alexsuvorov.paistewiki.feature.product_list

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.alexsuvorov.paistewiki.core.database.AppDatabase
import ru.alexsuvorov.paistewiki.core.database.dao.CymbalDao
import ru.alexsuvorov.paistewiki.core.database.model.CymbalSeries
import ru.alexsuvorov.paistewiki.feature.product_list.adapter.CymbalsAdapter
import ru.alexsuvorov.paistewiki.tools.AppPreferences

class CymbalsFragment : Fragment() {

    private var cymbalSeries: List<CymbalSeries> = ArrayList()
    private var cymbalsAdapter: CymbalsAdapter? = null
    private var db: AppDatabase? = null
    private var cymbalDao: CymbalDao? = null
    private var cymbalsView: RecyclerView? = null
    private var appPreferences: AppPreferences? = null

    interface CymbalsNavigation {
        fun onCymbalSeriesSelected(seriesId: Int?)
    }

    private var navigation: CymbalsNavigation? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is CymbalsNavigation) {
            navigation = context
        }
        appPreferences = AppPreferences(requireContext())
    }

    override fun onDetach() {
        super.onDetach()
        navigation = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_cymbals, container, false)
        cymbalsView = view.findViewById(R.id.cymbalsList)
        cymbalsView?.isNestedScrollingEnabled = false
        cymbalsView?.setHasFixedSize(false)
        cymbalsView?.layoutManager = LinearLayoutManager(activity)
        db = context?.let { AppDatabase.getDatabase(it) }
        cymbalDao = db?.cymbalDao()
        cymbalSeries = cymbalDao?.getAllProduced(1) ?: ArrayList()
        cymbalsAdapter = CymbalsAdapter(cymbalSeries, requireContext(), object : CymbalsAdapter.OnItemClickListener {
            override fun onItemClick(cymbalSeries: CymbalSeries?) {
                navigation?.onCymbalSeriesSelected(cymbalSeries?.cymbalseries_id)
            }
        })

        cymbalsView?.adapter = cymbalsAdapter
        return view
    }
}
