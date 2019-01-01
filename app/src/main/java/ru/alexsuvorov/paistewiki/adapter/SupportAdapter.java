package ru.alexsuvorov.paistewiki.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import ru.alexsuvorov.paistewiki.R;
import ru.alexsuvorov.paistewiki.db.AppDatabase;
import ru.alexsuvorov.paistewiki.db.dao.SupportDao;
import ru.alexsuvorov.paistewiki.fragments.support.SupportAnatomyFragment;
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
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final AppDatabase db = AppDatabase.getDatabase(context);
        final SupportDao supportDao = db.supportDao();
        int imageId = context.getResources().getIdentifier(supportDao.getById(position + 1).getSupportImage(), "drawable", context.getPackageName());
        holder.supportImage.setImageResource(imageId);
        holder.supportTitle.setText(supportDao.getById(position + 1).getTitle());
        holder.supportText.setText(supportDao.getById(position + 1).getText());
        holder.supportLayout.setOnClickListener(v -> {
            if (position == 0) {
                Fragment support = new SupportAnatomyFragment();
                FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.container, support)
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return supportModelList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView supportImage;
        private TextView supportTitle;
        private TextView supportText;
        private LinearLayout supportLayout;

        ViewHolder(View itemView) {
            super(itemView);
            supportLayout = itemView.findViewById(R.id.ll_support_item);
            supportImage = itemView.findViewById(R.id.item_image);
            supportTitle = itemView.findViewById(R.id.item_title);
            supportText = itemView.findViewById(R.id.item_text);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position % 2 == 0) {
            return 1;
        } else {
            return 2;
        }
    }
}
