package ru.alexsuvorov.paistewiki.feature.news.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.alexsuvorov.paistewiki.core.database.AppDatabase
import ru.alexsuvorov.paistewiki.feature.news.R
import ru.alexsuvorov.paistewiki.feature.news.adapter.NewsAdapter

class NewsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db = AppDatabase.getDatabase(requireContext())

        val monthDao = db.monthDao()
        val recyclerView = view.findViewById<RecyclerView>(R.id.newsList)
        recyclerView.isNestedScrollingEnabled = false
        recyclerView.setHasFixedSize(false)
        recyclerView.setLayoutManager(LinearLayoutManager(this.activity))

        val monthArray = monthDao.getAllMonth()
        val newsAdapter = NewsAdapter(monthArray, requireContext())
        recyclerView.setAdapter(newsAdapter)
        newsAdapter.notifyDataSetChanged()

        newsAdapter.setOnItemClickListener(object : NewsAdapter.OnItemClickListener {
            override fun onClick(str: String?) {
                val builder = CustomTabsIntent.Builder()
                builder.setToolbarColor(resources.getColor(ru.alexsuvorov.paistewiki.core.support.R.color.colorPrimary))
                builder.setShowTitle(true)
                val customTabsIntent = builder.build()
                customTabsIntent.launchUrl(requireContext(), Uri.parse(str))
            }
        })
        recyclerView.setItemAnimator(DefaultItemAnimator())
    }

    override fun onResume() {
        super.onResume()
        requireActivity().setTitle(ru.alexsuvorov.paistewiki.core.support.R.string.nav_header_newsbutton)
    }
}