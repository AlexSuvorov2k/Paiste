package ru.alexsuvorov.paistewiki.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.alexsuvorov.paistewiki.R;
import ru.alexsuvorov.paistewiki.db.AppDatabase;
import ru.alexsuvorov.paistewiki.db.dao.CymbalDao;
import ru.alexsuvorov.paistewiki.model.CymbalSeries;

public class CymbalsAdapter extends RecyclerView.Adapter<CymbalsAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(CymbalSeries cymbalSeries);
    }

    private final List<CymbalSeries> cymbalSeries;
    private final OnItemClickListener listener;
    private final Context context;

    public CymbalsAdapter(List<CymbalSeries> cymbalSeries, Context context, OnItemClickListener listener) {
        this.cymbalSeries = cymbalSeries;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_cymbal, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        final AppDatabase db = AppDatabase.getDatabase(context);
        final CymbalDao cymbalDao = db.cymbalDao();
        holder.cymbalSeriesName.setText(cymbalDao.getById(position).getCymbalName());
        holder.cymbalSeriesSlogan.setText(cymbalDao.getById(position).getCymbalSubName());
        int imageId = context.getResources().getIdentifier(cymbalDao.getById(position).getCymbalImage(), "drawable", context.getPackageName());

        holder.cymbalSeriesImage.setImageResource(imageId);
        holder.bind(cymbalSeries.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return cymbalSeries.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView cymbalSeriesImage;
        TextView cymbalSeriesName;
        TextView cymbalSeriesSlogan;

        ViewHolder(View itemView) {
            super(itemView);
            cymbalSeriesName = itemView.findViewById(R.id.cymbalSeriesName);
            cymbalSeriesSlogan = itemView.findViewById(R.id.cymbalSeriesSlogan);
            cymbalSeriesImage = itemView.findViewById(R.id.cymbalSeriesImage);
        }

        void bind(CymbalSeries item, OnItemClickListener listener) {
            itemView.setOnClickListener(v -> listener.onItemClick(item));
        }
    }
}