package ru.alexsuvorov.paistewiki.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.alexsuvorov.paistewiki.R
import ru.alexsuvorov.paistewiki.activity.support.SupportAnatomyActivity
import ru.alexsuvorov.paistewiki.db.AppDatabase
import ru.alexsuvorov.paistewiki.model.SupportModel
import ru.alexsuvorov.paistewiki.tools.Utils

class SupportAdapter(
    private val supportModelList: MutableList<SupportModel?>,
    private val context: Context,
    private val listener: SupportCallback?
) : RecyclerView.Adapter<SupportAdapter.ViewHolder?>() {

    interface SupportCallback {
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
        val db: AppDatabase = AppDatabase.Companion.getDatabase(context)
        val supportDao = db.supportDao()!!

        val support = supportDao.getById(position + 1)!!

        val imageId = context.resources.getIdentifier(support.supportImage, "drawable", context.packageName)
        holder.supportImage.setImageResource(imageId)
        holder.supportTitle.text = support.title
        holder.supportText.text = support.text
        holder.supportLayout.setOnClickListener(View.OnClickListener { v: View? ->
            if (position == 0) {
                context.startActivity(Intent(context, SupportAnatomyActivity::class.java))
            } else if (position == 1) {
                //context.startActivity(new Intent(context, SupportCymbalClassificationActivity.class));
            } /*else{
                Log.d("TEST","POSITION: "+position);
                listener.onClick(position);
            }*/
        })
    }

    override fun getItemCount(): Int {
        return supportModelList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val supportImage: ImageView
        val supportTitle: TextView
        val supportText: TextView
        val supportLayout: LinearLayout

        init {
            supportLayout = itemView.findViewById<LinearLayout>(R.id.ll_support_item)
            supportImage = itemView.findViewById<ImageView>(R.id.item_image)
            supportTitle = itemView.findViewById<TextView>(R.id.item_title)
            supportText = itemView.findViewById<TextView>(R.id.item_text)
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (!Utils.checkIsTablet(context) && !Utils.checkIsLandscape(context)) {
            if (position % 2 == 0) {
                return 1
            } else {
                return 2
            }
        } else return 1
    }
}
