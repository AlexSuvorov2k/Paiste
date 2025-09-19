package ru.alexsuvorov.paistewiki.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.alexsuvorov.paistewiki.App
import ru.alexsuvorov.paistewiki.R
import ru.alexsuvorov.paistewiki.adapter.SupportAdapter
import ru.alexsuvorov.paistewiki.db.AppDatabase
import ru.alexsuvorov.paistewiki.db.dao.SupportDao
import ru.alexsuvorov.paistewiki.model.SupportModel
import ru.alexsuvorov.paistewiki.tools.AppPreferences
import ru.alexsuvorov.paistewiki.tools.Utils

class SupportFragment : Fragment() {

    var supportView: RecyclerView? = null

    val appPreferences : AppPreferences by lazy{
        AppPreferences(requireActivity())
    }

    var supportList: MutableList<SupportModel?> = ArrayList<SupportModel?>()
    var supportAdapter: SupportAdapter? = null
    var supportDao: SupportDao? = null
    var db: AppDatabase? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity!!.application as App).setLocale()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_support, container, false)

        supportView = view.findViewById<RecyclerView>(R.id.supportList)
        supportView!!.isNestedScrollingEnabled = false
        supportView!!.setHasFixedSize(false)
        supportView!!.setLayoutManager(LinearLayoutManager(this.activity))
        db = AppDatabase.Companion.getDatabase(requireActivity())
        supportDao = db!!.supportDao()
        supportList = supportDao!!.supportList!!
        if (Utils.checkIsTablet(requireActivity()) && Utils.checkIsLandscape(requireActivity())) {
            val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(context, 2)
            supportView!!.setLayoutManager(layoutManager)
        }
        supportAdapter = SupportAdapter(supportList, requireActivity(), object : SupportAdapter.SupportCallback {
            override fun onClick(position: Int) {
            }
        })
        supportView!!.setAdapter(supportAdapter)
        supportAdapter!!.notifyDataSetChanged()
        return view
    }

    override fun onResume() {
        super.onResume()
        activity!!.setTitle(R.string.nav_header_supportbutton)
    }
}
