package ru.alexsuvorov.paistewiki.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.alexsuvorov.paistewiki.R;
import ru.alexsuvorov.paistewiki.model.SupportAnatomy;

public class AnatomyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<Object> items;
    private final Context context;
    private final int IMAGE = 1, TITLE = 2, CONTENT = 0;

    public AnatomyAdapter(List<Object> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;

        switch (viewType) {
            case IMAGE: {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_anatomy_image, parent, false);
                viewHolder = new ImageHolder(view);
                break;
            }
            case TITLE: {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_anatomy_title, parent, false);
                viewHolder = new TitleHolder(view);
                break;
            }
            case CONTENT: {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cymbal_anatomy, parent, false);
                viewHolder = new ViewHolder(view);
                break;
            }
            default:
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cymbal_anatomy, parent, false);
                viewHolder = new ViewHolder(view);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        try {
            switch (holder.getItemViewType()) {
                case TITLE:
                    TitleHolder titleHolder = (TitleHolder) holder;
                    configureTitleHolder(titleHolder, position);
                    break;
                case IMAGE:
                    ImageHolder imageHolder = (ImageHolder) holder;
                    configureImageHolder(imageHolder, position);
                    break;
                default:
                    ViewHolder viewHolder = (ViewHolder) holder;
                    configureContentHolder(viewHolder, position);
                    break;
            }
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    public class ImageHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;

        public ImageHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.anatomy_image);
        }

        public ImageView getImageView() {
            return imageView;
        }

        public void setImageView(ImageView imageView) {
            this.imageView = imageView;
        }
    }

    public class TitleHolder extends RecyclerView.ViewHolder {

        private TextView title;

        public TitleHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
        }

        public TextView getTitle() {
            return title;
        }

        public void setTitle(TextView title) {
            this.title = title;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView anatomyTitle;
        private final TextView anatomySubTitle;
        private final TextView anatomyContent;

        public ViewHolder(View itemView) {
            super(itemView);
            anatomyTitle = itemView.findViewById(R.id.title);
            anatomySubTitle = itemView.findViewById(R.id.subtitle);
            anatomyContent = itemView.findViewById(R.id.content);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position) instanceof SupportAnatomy) {
            return CONTENT;
        } else if (items.get(position) instanceof String) {
            return TITLE;
        } else if (items.get(position) instanceof Drawable) {
            return IMAGE;
        }
        return -1;
        // https://guides.codepath.com/android/Heterogenous-Layouts-inside-RecyclerView
        // https://guides.codepath.com/android/using-the-recyclerview
        // https://github.com/codepath/android_guides/wiki/Implementing-a-Rate-Me-Feature
    }

    private void configureTitleHolder(TitleHolder vh, int position) {
        vh.getTitle().setText((CharSequence) items.get(position));
    }

    private void configureContentHolder(ViewHolder vh1, int position) {
        SupportAnatomy supportAnatomy = (SupportAnatomy) items.get(position);
        if (supportAnatomy != null) {
            vh1.anatomyTitle.setText(supportAnatomy.anatomyTitle);
            vh1.anatomySubTitle.setText(supportAnatomy.anatomySubtitle);
            vh1.anatomyContent.setText(supportAnatomy.anatomyText);
        }
    }

    private void configureImageHolder(ImageHolder holder, int position) {
        if (position == 6)
            holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.cymbal_anatomy_content_image));
        else
            holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.cymbal_characteristics_content_image));
    }
}
