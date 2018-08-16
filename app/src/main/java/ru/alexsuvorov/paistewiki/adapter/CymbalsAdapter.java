package ru.alexsuvorov.paistewiki.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ru.alexsuvorov.paistewiki.R;
import ru.alexsuvorov.paistewiki.db.AppDatabase;
import ru.alexsuvorov.paistewiki.db.dao.CymbalDao;
import ru.alexsuvorov.paistewiki.fragments.SeriesDescriptionFragment;
import ru.alexsuvorov.paistewiki.model.CymbalSeries;

public class CymbalsAdapter extends RecyclerView.Adapter<CymbalsAdapter.ViewHolder> {

    private List<CymbalSeries> cymbalSeries;
    private Context context;
    private Fragment fragment = null;
    private Class fragmentClass = null;

    public CymbalsAdapter(List<CymbalSeries> cymbalSeries, Context context) {
        this.cymbalSeries = cymbalSeries;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cymbal_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final AppDatabase db = AppDatabase.getDatabase(context);
        final CymbalDao cymbalDao = db.cymbalDao();
        Log.d("CYMBALS ADAPTER", "TEXT rESOURCE IS: " + cymbalDao.getById(position).getCymbalName());
        Log.d("TO STRING", "is: " + cymbalDao.getById(position).toString());
        holder.cymbalSeriesName.setText(cymbalDao.getById(position).getCymbalName());
        Log.d("CYMBALS ADAPTER", "iMAGE rESOURCE IS: " + cymbalDao.getById(position).getSeriesImage());
        int imageId = context.getResources().getIdentifier(cymbalDao.getById(position).getCymbalImage(), "drawable", context.getPackageName());
        holder.cymbalSeriesImage.setImageResource(imageId);
        holder.cymbalsSeriesChoiseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                fragmentClass = SeriesDescriptionFragment.class;
                try {
                    fragment = (Fragment) fragmentClass.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                SeriesDescriptionFragment seriesDescriptionFragment = new SeriesDescriptionFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("cymbalseries_id", holder.getAdapterPosition());
                seriesDescriptionFragment.setArguments(bundle);
                FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.container, seriesDescriptionFragment)
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return cymbalSeries.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView cymbalSeriesImage;
        TextView cymbalSeriesName;
        Button cymbalsSeriesChoiseButton;

        ViewHolder(View itemView) {
            super(itemView);
            cymbalSeriesName = itemView.findViewById(R.id.cymbalSeriesName);
            cymbalSeriesImage = itemView.findViewById(R.id.cymbalSeriesImage);
            cymbalsSeriesChoiseButton = itemView.findViewById(R.id.chooseButton);
        }
    }
}