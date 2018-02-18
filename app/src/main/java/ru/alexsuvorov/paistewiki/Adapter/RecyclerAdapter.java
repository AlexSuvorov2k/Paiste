package ru.alexsuvorov.paistewiki.Adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.brandongogetap.stickyheaders.exposed.StickyHeader;
import com.brandongogetap.stickyheaders.exposed.StickyHeaderHandler;

import java.util.ArrayList;
import java.util.List;

import ru.alexsuvorov.paistewiki.ItemModel.NewsModel;
import ru.alexsuvorov.paistewiki.R;

import static android.view.LayoutInflater.from;

public final class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.BaseViewHolder>
        implements StickyHeaderHandler {

    private final List<NewsModel> data = new ArrayList<>();

    public void setData(List<NewsModel> items) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new SimpleDiffCallback(data, items));
        data.clear();
        data.addAll(items);
        diffResult.dispatchUpdatesTo(this);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = from(parent.getContext()).inflate(R.layout.item_view, parent, false);
        final BaseViewHolder viewHolder;
        if (viewType == 0) {
            viewHolder = new MyViewHolder(view);
        } else {
            viewHolder = new MyOtherViewHolder(view);
        }
        /*view.setOnClickListener(v -> {
            int position = viewHolder.getAdapterPosition();
            if (position != NO_POSITION) {
                List<NewsModel> newData = new ArrayList<>(data);
                newData.remove(position);
                setData(newData);
            }
        });*/
        return viewHolder;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        NewsModel item = data.get(position);
        holder.titleTextView.setText(item.getTitle());
        //holder.messageTextView.setText(item.getURL());
        if (position != 0 && position % 12 == 0) {
            holder.itemView.setPadding(0, 100, 0, 100);
        } else {
            holder.itemView.setPadding(0, 0, 0, 0);
        }
        if (item instanceof StickyHeader) {
            holder.itemView.setBackgroundColor(R.color.colorPrimary);
        } else {
            holder.itemView.setBackgroundColor(Color.TRANSPARENT);

        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position != 0 && position % 16 == 0) {
            return 1;
        }
        return 0;
    }

    @Override
    public List<?> getAdapterData() {
        return data;
    }

    private static final class MyViewHolder extends BaseViewHolder {

        MyViewHolder(View itemView) {
            super(itemView);
        }
    }

    private static final class MyOtherViewHolder extends BaseViewHolder {

        MyOtherViewHolder(View itemView) {
            super(itemView);
        }
    }

    static class BaseViewHolder extends RecyclerView.ViewHolder {

        TextView titleTextView;
        //TextView messageTextView;

        BaseViewHolder(View itemView) {
            super(itemView);
            titleTextView = (TextView) itemView.findViewById(R.id.tv_title);
            //messageTextView = (TextView) itemView.findViewById(R.id.tv_message);
        }
    }
}