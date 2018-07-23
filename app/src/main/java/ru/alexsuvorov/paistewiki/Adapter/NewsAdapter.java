package ru.alexsuvorov.paistewiki.Adapter;

import android.app.Activity;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;

import ru.alexsuvorov.paistewiki.R;
import ru.alexsuvorov.paistewiki.model.NewsMonth;
import ru.alexsuvorov.paistewiki.model.NewsPost;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsCardViewHolder> {

    private List<NewsMonth> months;
    private List<NewsPost> posts;
    private AdapterView.OnItemClickListener onItemClickListener;
    private String TAG = "NewsAdapter";
    private Activity context;
    TableLayout tableLayout;

    public NewsAdapter(List<NewsMonth> months, Activity context) {
        this.months = months;
        this.context = context;
    }

    class NewsCardViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView monthName;
        TextView postLabel;
        TextView postType;
        TableLayout tableLayout;

        NewsCardViewHolder(View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.cv);
            monthName = itemView.findViewById(R.id.month_name);
            postLabel = itemView.findViewById(R.id.news_label);
            postType = itemView.findViewById(R.id.news_category);
            tableLayout = itemView.findViewById(R.id.tableNews);
        }
    }

    @NonNull
    @Override
    public NewsCardViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.news_item, viewGroup, false);
        return new NewsCardViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsCardViewHolder ViewHolder, final int position) {
        ViewHolder.monthName.setText(months.get(position).getMonthName());
        posts = months.get(position).getPosts();
        for (int j = 0; j < posts.size(); j++) {
            //ViewHolder.itemView.addView(months.get(position).getPosts().get(j).getTitle());
            TextView postLabel = new TextView(context.getApplicationContext());
            postLabel.setGravity(Gravity.START);
            postLabel.setText(months.get(position).getPosts().get(j).getTitle());
            postLabel.setLayoutParams(new TableRow.LayoutParams
                    (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            postLabel.setTextSize(10);
            postLabel.setTextColor(Color.parseColor("#FF9900"));
            TableRow row = new TableRow(context.getApplicationContext());
            row.addView(postLabel); // добавляем в строку столбец с именем пользователя
            tableLayout.addView(row); // добавляем в таблицу новую строку
        }
    }

    @Override
    public int getItemCount() {
        return months.size();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}