package ru.alexsuvorov.paistewiki.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ru.alexsuvorov.paistewiki.R;
import ru.alexsuvorov.paistewiki.model.SupportModel;

public class SupportAdapter extends RecyclerView.Adapter<SupportAdapter.ViewHolder> {

    private List<SupportModel> supportModelList;
    private Context context;

    public SupportAdapter(List<SupportModel> supportModelList, Context context) {
        this.supportModelList = supportModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public SupportAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        SupportAdapter.ViewHolder viewHolder;

        switch (viewType) {
            case 1: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_support_left, parent, false);
                break;
            }
            case 2: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_support_right, parent, false);
                break;
            }
            default: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_support_left, parent, false);
                break;
            }

        }
        return viewHolder = new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SupportAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ViewHolder(View itemView) {
            super(itemView);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(position%2==0){
            return 1;
        }else{
            return 2;
        }
    }
}
