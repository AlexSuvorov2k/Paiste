package ru.alexsuvorov.paistewiki.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.alexsuvorov.paistewiki.R
import ru.alexsuvorov.paistewiki.model.SupportAnatomy

class AnatomyAdapter(private val items: MutableList<Any?>, private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder?>() {
    private val IMAGE = 1
    private val TITLE = 2
    private val CONTENT = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val viewHolder: RecyclerView.ViewHolder?

        when (viewType) {
            IMAGE -> {
                val view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_anatomy_image, parent, false)
                viewHolder = ImageHolder(view)
            }

            TITLE -> {
                val view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_anatomy_title, parent, false)
                viewHolder = TitleHolder(view)
            }

            CONTENT -> {
                val view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cymbal_anatomy, parent, false)
                viewHolder = AnatomyAdapter.ViewHolder(view)
            }

            else -> {
                val view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cymbal_anatomy, parent, false)
                viewHolder = AnatomyAdapter.ViewHolder(view)
            }
        }
        return viewHolder!!
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
            when (holder.getItemViewType()) {
                TITLE -> {
                    val titleHolder = holder as TitleHolder
                    configureTitleHolder(titleHolder, position)
                }

                IMAGE -> {
                    val imageHolder = holder as ImageHolder
                    configureImageHolder(imageHolder, position)
                }

                else -> {
                    val viewHolder = holder as ViewHolder
                    configureContentHolder(viewHolder, position)
                }
            }
        } catch (e: ClassCastException) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return this.items.size
    }

    inner class ImageHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView = itemView.findViewById<ImageView>(R.id.anatomy_image)
    }

    inner class TitleHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView?

        init {
            title = itemView.findViewById<TextView?>(R.id.title)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val anatomyTitle: TextView
        val anatomySubTitle: TextView
        val anatomyContent: TextView

        init {
            anatomyTitle = itemView.findViewById<TextView>(R.id.title)
            anatomySubTitle = itemView.findViewById<TextView>(R.id.subtitle)
            anatomyContent = itemView.findViewById<TextView>(R.id.content)
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (items.get(position) is SupportAnatomy) {
            return CONTENT
        } else if (items.get(position) is String) {
            return TITLE
        } else if (items.get(position) is Drawable) {
            return IMAGE
        }
        return -1
        // https://guides.codepath.com/android/Heterogenous-Layouts-inside-RecyclerView
        // https://guides.codepath.com/android/using-the-recyclerview
        // https://github.com/codepath/android_guides/wiki/Implementing-a-Rate-Me-Feature
    }

    private fun configureTitleHolder(vh: TitleHolder, position: Int) {
        vh.title!!.setText(items.get(position) as CharSequence?)
    }

    private fun configureContentHolder(vh1: ViewHolder, position: Int) {
        val supportAnatomy = items.get(position) as SupportAnatomy?
        if (supportAnatomy != null) {
            vh1.anatomyTitle.setText(supportAnatomy.anatomyTitle)
            vh1.anatomySubTitle.setText(supportAnatomy.anatomySubtitle)
            vh1.anatomyContent.setText(supportAnatomy.anatomyText)
        }
    }

    private fun configureImageHolder(holder: ImageHolder, position: Int) {
        if (position == 6) holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.cymbal_anatomy_content_image))
        else holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.cymbal_characteristics_content_image))
    }
}
