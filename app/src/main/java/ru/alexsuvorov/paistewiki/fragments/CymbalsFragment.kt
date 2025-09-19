package ru.alexsuvorov.paistewiki.fragments

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.alexsuvorov.paistewiki.R
import ru.alexsuvorov.paistewiki.activity.SeriesDescriptionActivity
import ru.alexsuvorov.paistewiki.adapter.CymbalsAdapter
import ru.alexsuvorov.paistewiki.db.AppDatabase
import ru.alexsuvorov.paistewiki.db.dao.CymbalDao
import ru.alexsuvorov.paistewiki.model.CymbalSeries
import ru.alexsuvorov.paistewiki.tools.AppPreferences
import java.util.Locale

class CymbalsFragment : Fragment() {

    val appPreferences : AppPreferences by lazy{
        AppPreferences(requireActivity())
    }

    var cymbalSeries: MutableList<CymbalSeries?> = ArrayList<CymbalSeries?>()
    var cymbalsAdapter: CymbalsAdapter? = null
    var db: AppDatabase? = null
    var cymbalDao: CymbalDao? = null
    var cymbalsView: RecyclerView? = null

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
    ): View {
        val view = inflater.inflate(R.layout.fragment_cymbals, container, false)

        cymbalsView = view.findViewById<RecyclerView>(R.id.cymbalsList)
        cymbalsView!!.isNestedScrollingEnabled = false
        cymbalsView!!.setHasFixedSize(false)
        cymbalsView!!.setLayoutManager(LinearLayoutManager(this.activity))
        db = AppDatabase.Companion.getDatabase(requireActivity())
        cymbalDao = db!!.cymbalDao()
        cymbalSeries = cymbalDao!!.getAllProduced(1)

        val listener = object :CymbalsAdapter.OnItemClickListener {
            override fun onItemClick(cymbalSeries: CymbalSeries?) {
                val intent = Intent(activity, SeriesDescriptionActivity::class.java)
                intent.putExtra("cymbalseries_id", cymbalSeries!!.cymbalseries_id)
                startActivity(intent)
            }

        }
        cymbalsAdapter = CymbalsAdapter(cymbalSeries, requireActivity(), listener)
        cymbalsView?.setAdapter(cymbalsAdapter)
        cymbalsAdapter?.notifyDataSetChanged()
        return view
    }

    override fun onResume() {
        super.onResume()
        activity!!.setTitle(R.string.nav_header_cymbalsbutton)
    }
}