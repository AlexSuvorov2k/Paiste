package ru.alexsuvorov.paistewiki.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.alexsuvorov.paistewiki.R
import ru.alexsuvorov.paistewiki.db.AppDatabase
import ru.alexsuvorov.paistewiki.model.CymbalSeries

class CymbalsAdapter(
    private val cymbalSeries: MutableList<CymbalSeries?>,
    private val context: Context,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<CymbalsAdapter.ViewHolder?>() {

    interface OnItemClickListener {
        fun onItemClick(cymbalSeries: CymbalSeries?)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_cymbal, viewGroup, false)
        return CymbalsAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val db: AppDatabase = AppDatabase.Companion.getDatabase(context)
        val cymbalDao = db.cymbalDao()!!
        val item = cymbalDao.getById(position)!!
        holder.cymbalSeriesName.setText(item.getCymbalName())
        holder.cymbalSeriesSlogan.setText(item.cymbalSubName)
        val imageId = context.getResources().getIdentifier(item.cymbalImage, "drawable", context.getPackageName())

        holder.cymbalSeriesImage.setImageResource(imageId)
        holder.bind(cymbalSeries.get(position), listener)
    }

    override fun getItemCount(): Int {
        return cymbalSeries.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var cymbalSeriesImage: ImageView
        var cymbalSeriesName: TextView
        var cymbalSeriesSlogan: TextView

        init {
            cymbalSeriesName = itemView.findViewById<TextView>(R.id.cymbalSeriesName)
            cymbalSeriesSlogan = itemView.findViewById<TextView>(R.id.cymbalSeriesSlogan)
            cymbalSeriesImage = itemView.findViewById<ImageView>(R.id.cymbalSeriesImage)
        }

        fun bind(item: CymbalSeries?, listener: OnItemClickListener) {
            itemView.setOnClickListener(View.OnClickListener { v: View? -> listener.onItemClick(item) })
        }
    }
}