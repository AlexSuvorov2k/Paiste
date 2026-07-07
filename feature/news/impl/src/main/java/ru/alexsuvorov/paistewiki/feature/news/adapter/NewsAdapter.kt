package ru.alexsuvorov.paistewiki.feature.news.adapter

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.alexsuvorov.paistewiki.core.database.AppDatabase
import ru.alexsuvorov.paistewiki.core.database.model.Month
import ru.alexsuvorov.paistewiki.feature.news.R

internal class NewsAdapter(
    private val months: List<Month>,
    private val context: Context
) : RecyclerView.Adapter<NewsAdapter.NewsCardViewHolder?>() {

    private var onItemClickListener: OnItemClickListener? = null

    internal class NewsCardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val monthName: TextView = itemView.findViewById(R.id.month_name)
        val tableLayout: TableLayout = itemView.findViewById(R.id.postListLayout)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): NewsCardViewHolder {
        val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_news, viewGroup, false)
        return NewsCardViewHolder(v)
    }

    override fun onBindViewHolder(ViewHolder: NewsCardViewHolder, position: Int) {
        val db = AppDatabase.getDatabase(context)
        val newsDao = db.newsDao()
        val monthDao = db.monthDao()

        val vposition = monthDao.getCount() - position
        ViewHolder.monthName.text = monthDao.getMonthById(vposition)!!.monthName
        ViewHolder.monthName.setTextColor(context.resources.getColor(ru.alexsuvorov.paistewiki.core.support.R.color.black))
        val posts = newsDao.getNewsByMonthIndex(monthDao.getMonthById(vposition)!!.monthIndex.toLong())
        if (posts.isNotEmpty()) {
            for (j in posts.indices) {
                val postLabel = TextView(context)
                postLabel.gravity = Gravity.START
                val layoutParams = TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT)
                layoutParams.setMargins(0, 0, 16, 0)
                postLabel.layoutParams = layoutParams
                postLabel.textSize = 16f
                postLabel.setPadding(10, 8, 0, 8)
                postLabel.setTextColor(context.resources.getColor(ru.alexsuvorov.paistewiki.core.support.R.color.black))
                postLabel.text = newsDao.getNewsByMonthIndex(monthDao.getMonthById(vposition)!!.monthIndex.toLong()).get(j).title
                postLabel.isClickable = true

                //LEFT PICTURES
                if (posts.get(j).category == "Artist News") {
                    postLabel.setCompoundDrawablesWithIntrinsicBounds(ru.alexsuvorov.paistewiki.core.support.R.mipmap.ic_artist, 0, 0, 0)
                } else {
                    postLabel.setCompoundDrawablesWithIntrinsicBounds(ru.alexsuvorov.paistewiki.core.support.R.mipmap.ic_cymbal_icon, 0, 0, 0)
                }

                //POSTS ON CLICK EVENS
                val data = newsDao.getNewsByMonthIndex(monthDao.getMonthById(vposition)!!.monthIndex.toLong()).get(j).url
                postLabel.setOnClickListener {
                    onItemClickListener?.onClick(data)
                }

                val row = TableRow(context)
                row.addView(postLabel)
                row.setPadding(2, 2, 2, 2)
                ViewHolder.tableLayout.addView(row)
            }
        } else {
            val postLabel = TextView(context)
            postLabel.gravity = Gravity.START
            val layoutParams = TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT)
            layoutParams.setMargins(16, 0, 16, 0)
            postLabel.layoutParams = layoutParams
            postLabel.textSize = 16f
            postLabel.setPadding(10, 8, 0, 8)
            postLabel.setTextColor(context.resources.getColor(ru.alexsuvorov.paistewiki.core.support.R.color.black))
            postLabel.setText(R.string.no_news_yet)
            postLabel.isClickable = true
            val row = TableRow(context)
            row.addView(postLabel)
            row.setPadding(2, 2, 2, 2)
            ViewHolder.tableLayout.addView(row)
        }
    }

    override fun getItemCount(): Int {
        return months.size
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    internal interface OnItemClickListener {
        fun onClick(str: String?)
    }
}