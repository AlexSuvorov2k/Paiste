package ru.alexsuvorov.paistewiki.Adapter;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ru.alexsuvorov.paistewiki.R;
import ru.alexsuvorov.paistewiki.model.NewsMonth;

import static android.view.LayoutInflater.from;


//Endless RecyclerView!!!!!!!!!!!!!!!
//https://github.com/kprathap23/Android/tree/master/EndlessRecyclerView/app/src/main/java/com/pratap/endlessrecyclerview
public final class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.BaseViewHolder> {

    private final List<NewsMonth> data = new ArrayList<>();
    private static final String TAG = "RecyclerAdapter";


    public void setData(List<NewsMonth> items) {
        data.clear();
        data.addAll(items);
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
        NewsMonth item = data.get(position);
        //holder.titleTextView.setText(item.getTitle());
        if (position != 0 && position % 12 == 0) {
            holder.itemView.setPadding(0, 100, 0, 100);
        } else {
            holder.itemView.setPadding(0, 0, 0, 0);
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
            titleTextView = itemView.findViewById(R.id.tv_title);
            //messageTextView = (TextView) itemView.findViewById(R.id.tv_message);
        }
    }

}