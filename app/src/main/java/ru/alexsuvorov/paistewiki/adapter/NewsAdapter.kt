package ru.alexsuvorov.paistewiki.adapter

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import ru.alexsuvorov.paistewiki.R
import ru.alexsuvorov.paistewiki.adapter.NewsAdapter.NewsCardViewHolder
import ru.alexsuvorov.paistewiki.db.AppDatabase
import ru.alexsuvorov.paistewiki.model.Month

class NewsAdapter(
    private val months: MutableList<Month?>,
    private val context: Context
) : RecyclerView.Adapter<NewsCardViewHolder?>() {

    private var itemClickListner: onItemClickListner? = null

    class NewsCardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var cv: CardView? = itemView.findViewById<CardView?>(R.id.cv)
        var monthName: TextView = itemView.findViewById<TextView>(R.id.month_name)
        var tableLayout: TableLayout = itemView.findViewById<TableLayout>(R.id.postListLayout)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): NewsCardViewHolder {
        val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_news, viewGroup, false)
        return NewsCardViewHolder(v)
    }

    override fun onBindViewHolder(ViewHolder: NewsCardViewHolder, position: Int) {
        val db: AppDatabase = AppDatabase.Companion.getDatabase(context)
        val newsDao = db.newsDao()!!
        val monthDao = db.monthDao()!!

        val vposition = monthDao.count - position
        //Log.d(getClass().getSimpleName(),"Last position ID: "+monthDao.getLastMonthId());
        //Typeface myTypeface = Typeface.createFromAsset(context.getAssets(), "fonts/roboto_regular.ttf");
        //Log.d(getClass().getSimpleName(),"Position: "+vposition);
        val item = monthDao.getMonthById(vposition)!!

        ViewHolder.monthName.text = item.monthName
        ViewHolder.monthName.setTextColor(context.resources.getColor(R.color.black))
        val posts = newsDao.getNewsByMonthIndex(item.monthIndex.toLong())?: emptyList()
        if (posts.isNotEmpty()) {
            for (j in posts.indices) {
                val postLabel = TextView(context)
                postLabel.gravity = Gravity.START
                val layoutParams = TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT)
                layoutParams.setMargins(0, 0, 16, 0)
                postLabel.layoutParams = layoutParams
                postLabel.textSize = 16f
                postLabel.setPadding(10, 8, 0, 8)
                postLabel.setTextColor(context.resources.getColor(R.color.black))
                //postLabel.setTypeface(myTypeface, Typeface.BOLD);
                //postLabel.setBackgroundResource(R.drawable.divider);
                postLabel.text = newsDao.getNewsByMonthIndex(item.monthIndex.toLong())!!.get(j)!!.title
                postLabel.isClickable = true

                //LEFT PICTURES
                if (posts[j]!!.category!! == "Artist News") {
                    postLabel.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_artist, 0, 0, 0)
                } else {
                    postLabel.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_cymbal_icon, 0, 0, 0)
                }

                //POSTS ON CLICK EVENS
                val data = newsDao.getNewsByMonthIndex(item.monthIndex.toLong())!!.get(j)!!.url
                postLabel.setOnClickListener(object : View.OnClickListener {
                    override fun onClick(view: View?) {
                        itemClickListner?.onClick(data)
                    }
                })

                val row = TableRow(context)
                row.addView(postLabel) // добавляем в строку столбец с именем пользователя
                row.setPadding(2, 2, 2, 2)
                ViewHolder.tableLayout.addView(row) // добавляем в таблицу новую строку
            }
        } else {
            val postLabel = TextView(context)
            postLabel.gravity = Gravity.START
            val layoutParams = TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT)
            layoutParams.setMargins(16, 0, 16, 0)
            postLabel.layoutParams = layoutParams
            postLabel.textSize = 16f
            // postLabel.setTypeface(myTypeface, Typeface.BOLD);
            postLabel.setPadding(10, 8, 0, 8)
            postLabel.setTextColor(context.resources.getColor(R.color.black))
            //postLabel.setTypeface(myTypeface, Typeface.BOLD);
            //postLabel.setBackgroundResource(R.drawable.divider);
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

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
    }

    fun setOnItemClickListner(onItemClickListner: onItemClickListner) {
        this.itemClickListner = onItemClickListner
    }

    interface onItemClickListner {
        fun onClick(str: String?)
    }
}