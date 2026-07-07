package ru.alexsuvorov.paistewiki.feature.support

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.alexsuvorov.paistewiki.core.database.AppDatabase
import ru.alexsuvorov.paistewiki.core.database.dao.SupportDao
import ru.alexsuvorov.paistewiki.core.database.model.SupportModel
import ru.alexsuvorov.paistewiki.core.support.Utils
import ru.alexsuvorov.paistewiki.feature.support.adapter.SupportAdapter

class SupportFragment : Fragment() {

    private var supportView: RecyclerView? = null
    private var supportList: List<SupportModel?> = ArrayList<SupportModel?>()
    private var supportAdapter: SupportAdapter? = null
    private var supportDao: SupportDao? = null
    private var db: AppDatabase? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_support, container, false)

        supportView = view.findViewById<RecyclerView>(R.id.supportList)
        supportView!!.isNestedScrollingEnabled = false
        supportView!!.setHasFixedSize(false)
        supportView!!.setLayoutManager(LinearLayoutManager(this.activity))
        db = AppDatabase.getDatabase(requireContext())
        supportDao = db!!.supportDao()
        supportList = supportDao!!.getSupportList()
        if (Utils.checkIsTablet(requireContext()) && Utils.checkIsLandscape(requireContext())) {
            val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(requireContext(), 2)
            supportView!!.setLayoutManager(layoutManager)
        }
        supportAdapter = SupportAdapter(supportList, requireContext(), object : SupportAdapter.SupportCallback {
            override fun onClick(position: Int) {
            }
        })
        supportView!!.setAdapter(supportAdapter)
        return view
    }
}