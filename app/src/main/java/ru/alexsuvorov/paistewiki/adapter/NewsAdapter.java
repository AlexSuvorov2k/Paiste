package ru.alexsuvorov.paistewiki.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;

import ru.alexsuvorov.paistewiki.R;
import ru.alexsuvorov.paistewiki.db.AppDatabase;
import ru.alexsuvorov.paistewiki.db.dao.MonthDao;
import ru.alexsuvorov.paistewiki.db.dao.NewsDao;
import ru.alexsuvorov.paistewiki.model.Month;
import ru.alexsuvorov.paistewiki.model.News;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsCardViewHolder> {

    private List<Month> months;
    private Context context;
    private onItemClickListner onItemClickListner;

    public NewsAdapter(List<Month> months, Context context) {
        this.months = months;
        this.context = context;
    }

    class NewsCardViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView monthName;
        TableLayout tableLayout;

        NewsCardViewHolder(View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.cv);
            monthName = itemView.findViewById(R.id.month_name);
            tableLayout = itemView.findViewById(R.id.postListLayout);
        }
    }

    @NonNull
    @Override
    public NewsCardViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_news, viewGroup, false);
        return new NewsCardViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final NewsCardViewHolder ViewHolder, final int position) {
        AppDatabase db = AppDatabase.getDatabase(context);
        NewsDao newsDao = db.newsDao();
        MonthDao monthDao = db.monthDao();

        int vposition = monthDao.getCount()-position;
        //Log.d(getClass().getSimpleName(),"Last position ID: "+monthDao.getLastMonthId());
        //Typeface myTypeface = Typeface.createFromAsset(context.getAssets(), "fonts/roboto_regular.ttf");
        //Log.d(getClass().getSimpleName(),"Position: "+vposition);
        ViewHolder.monthName.setText(monthDao.getMonthById(vposition).getMonthName());
        ViewHolder.monthName.setTextColor(context.getResources().getColor(R.color.black));
        List<News> posts = newsDao.getNewsByMonthIndex(monthDao.getMonthById(vposition).getMonthIndex());
        if(posts.size()>0) {
            for (int j = 0; j < posts.size(); j++) {
                TextView postLabel = new TextView(context);
                postLabel.setGravity(Gravity.START);
                TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
                layoutParams.setMargins(0, 0, 16, 0);
                postLabel.setLayoutParams(layoutParams);
                postLabel.setTextSize(16);
                postLabel.setPadding(10, 8, 0, 8);
                postLabel.setTextColor(context.getResources().getColor(R.color.black));
                //postLabel.setTypeface(myTypeface, Typeface.BOLD);
                //postLabel.setBackgroundResource(R.drawable.divider);
                postLabel.setText(newsDao.getNewsByMonthIndex(monthDao.getMonthById(vposition).getMonthIndex()).get(j).getTitle());
                postLabel.setClickable(true);

                //LEFT PICTURES
                if (posts.get(j).getCategory().equals("Artist News")) {
                    postLabel.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_artist, 0, 0, 0);
                } else {
                    postLabel.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_cymbal_icon, 0, 0, 0);
                }

                //POSTS ON CLICK EVENS
                final String data = newsDao.getNewsByMonthIndex(monthDao.getMonthById(vposition).getMonthIndex()).get(j).getUrl();
                postLabel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onItemClickListner.onClick(data);
                    }
                });

                TableRow row = new TableRow(context);
                row.addView(postLabel); // добавляем в строку столбец с именем пользователя
                row.setPadding(2,2,2,2);
                ViewHolder.tableLayout.addView(row); // добавляем в таблицу новую строку
            }
        }else {
            TextView postLabel = new TextView(context);
            postLabel.setGravity(Gravity.START);
            TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.setMargins(16, 0, 16, 0);
            postLabel.setLayoutParams(layoutParams);
            postLabel.setTextSize(16);
           // postLabel.setTypeface(myTypeface, Typeface.BOLD);
            postLabel.setPadding(10, 8, 0, 8);
            postLabel.setTextColor(context.getResources().getColor(R.color.black));
            //postLabel.setTypeface(myTypeface, Typeface.BOLD);
            //postLabel.setBackgroundResource(R.drawable.divider);
            postLabel.setText(R.string.no_news_yet);
            postLabel.setClickable(true);
            TableRow row = new TableRow(context);
            row.addView(postLabel);
            row.setPadding(2,2,2,2);
            ViewHolder.tableLayout.addView(row);
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

    public void setOnItemClickListner(NewsAdapter.onItemClickListner onItemClickListner) {
        this.onItemClickListner = onItemClickListner;
    }

    public interface onItemClickListner {
        void onClick(String str);
    }
}