package ru.alexsuvorov.paistewiki.feature.product_list.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.alexsuvorov.paistewiki.core.database.AppDatabase
import ru.alexsuvorov.paistewiki.core.database.model.CymbalSeries
import ru.alexsuvorov.paistewiki.feature.product_list.R

class CymbalsAdapter(
    private val cymbalSeries: List<CymbalSeries?>,
    private val context: Context,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<CymbalsAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(cymbalSeries: CymbalSeries?)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_cymbal, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val db = AppDatabase.getDatabase(context)
        val cymbalDao = db.cymbalDao()
        val cymbal = cymbalDao.getById(position)
        holder.cymbalSeriesName.text = cymbal?.cymbalName ?: ""
        holder.cymbalSeriesSlogan.text = cymbal?.cymbalSubName ?: ""
        val imageId = context.resources.getIdentifier(cymbal?.cymbalImage, "drawable", context.packageName)

        holder.cymbalSeriesImage.setImageResource(imageId)
        holder.bind(cymbalSeries[position], listener)
    }

    override fun getItemCount(): Int {
        return cymbalSeries.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var cymbalSeriesImage: ImageView = itemView.findViewById(R.id.cymbalSeriesImage)
        var cymbalSeriesName: TextView = itemView.findViewById(R.id.cymbalSeriesName)
        var cymbalSeriesSlogan: TextView = itemView.findViewById(R.id.cymbalSeriesSlogan)

        fun bind(item: CymbalSeries?, listener: OnItemClickListener) {
            itemView.setOnClickListener { listener.onItemClick(item) }
        }
    }
}
