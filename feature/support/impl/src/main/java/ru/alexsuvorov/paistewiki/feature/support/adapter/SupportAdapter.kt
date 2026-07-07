package ru.alexsuvorov.paistewiki.feature.support.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.alexsuvorov.paistewiki.core.database.AppDatabase
import ru.alexsuvorov.paistewiki.core.database.model.SupportModel
import ru.alexsuvorov.paistewiki.core.support.Utils.checkIsLandscape
import ru.alexsuvorov.paistewiki.core.support.Utils.checkIsTablet
import ru.alexsuvorov.paistewiki.feature.support.R
import ru.alexsuvorov.paistewiki.feature.support.activity.SupportAnatomyActivity

internal class SupportAdapter(
    private val supportModelList: List<SupportModel?>,
    private val context: Context,
    private val listener: SupportCallback?
) : RecyclerView.Adapter<SupportAdapter.ViewHolder?>() {

    internal interface SupportCallback {
        fun onClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View

        when (viewType) {
            1 -> {
                view = LayoutInflater.from(parent.context).inflate(R.layout.item_support_left, parent, false)
            }

            2 -> {
                view = LayoutInflater.from(parent.context).inflate(R.layout.item_support_right, parent, false)
            }

            else -> {
                view = LayoutInflater.from(parent.context).inflate(R.layout.item_support_left, parent, false)
            }
        }
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val db = AppDatabase.getDatabase(context)
        val supportDao = db.supportDao()
        val supportItem = supportDao.getById(position + 1)
        if (supportItem != null) {
            val imageId = context.resources.getIdentifier(supportItem.supportImage, "drawable", context.packageName)
            holder.supportImage.setImageResource(imageId)
            holder.supportTitle.text = supportItem.title
            holder.supportText.text = supportItem.text
        }
        holder.supportLayout.setOnClickListener(View.OnClickListener { v: View? ->
            context.startActivity(Intent(context, SupportAnatomyActivity::class.java))
            /*when (position) {
                0 -> {
                    context.startActivity(Intent(context, SupportAnatomyActivity::class.java))
                }
                1 -> {
                    context.startActivity(Intent(context, SupportCymbalClassificationActivity::class.java))
                }
                else -> {
                    Log.d("TEST", "POSITION: " + position)
                    listener?.onClick(position)
                }
            }*/
        })
    }

    override fun getItemCount(): Int {
        return supportModelList.size
    }

    internal class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val supportImage: ImageView = itemView.findViewById(R.id.item_image)
        val supportTitle: TextView = itemView.findViewById(R.id.item_title)
        val supportText: TextView = itemView.findViewById(R.id.item_text)
        val supportLayout: LinearLayout = itemView.findViewById(R.id.ll_support_item)
    }

    override fun getItemViewType(position: Int): Int {
        if (!checkIsTablet(context) && !checkIsLandscape(context)) {
            if (position % 2 == 0) {
                return 1
            } else {
                return 2
            }
        } else return 1
    }
}