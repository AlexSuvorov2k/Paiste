package ru.alexsuvorov.paistewiki.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    private Context context;
    private onItemClickListner onItemClickListner;

    public NewsAdapter(List<NewsMonth> months, Context context) {
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
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.news_item, viewGroup, false);
        return new NewsCardViewHolder(v);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final NewsCardViewHolder ViewHolder, final int position) {
        Typeface myTypeface = Typeface.createFromAsset(context.getAssets(), "fonts/roboto_regular.ttf");
        ViewHolder.monthName.setText(months.get(position).getMonthName());
        List<NewsPost> posts = months.get(position).getPosts();
        for (int j = 0; j < posts.size(); j++) {
            TextView postLabel = new TextView(context);
            postLabel.setGravity(Gravity.START);
            postLabel.setLayoutParams(new TableRow.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)); //80
            postLabel.setTextSize(16);
            postLabel.setPadding(10, 8, 0, 8);
            postLabel.setTextColor(R.color.black);
            postLabel.setTypeface(myTypeface, Typeface.BOLD);

            //LINK TEXTVIEW
            //postLabel.setText(Html.fromHtml("<a href=\"" + months.get(position).getPosts().get(j).getURL() + "\">" + months.get(position).getPosts().get(j).getTitle() + "</a>"));
            postLabel.setText(months.get(position).getPosts().get(j).getTitle());
            postLabel.setClickable(true);

            //HYPHENATION
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                postLabel.setHyphenationFrequency(Layout.HYPHENATION_FREQUENCY_FULL);
                postLabel.setBreakStrategy(Layout.BREAK_STRATEGY_BALANCED);
            }

            //LEFT PICTURES
            if (posts.get(j).getCategory().equals("Artist News")) {
                postLabel.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_artist, 0, 0, 0);
            } else {
                postLabel.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_cymbal_icon, 0, 0, 0);
            }

            //POSTS ON CLICK EVENS
            final String data = months.get(position).getPosts().get(j).getURL(); // if you pass object of class then create that class object.
            postLabel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListner.onClick(data);
                }
            });

            TableRow row = new TableRow(context);
            row.addView(postLabel); // добавляем в строку столбец с именем пользователя
            ViewHolder.tableLayout.addView(row); // добавляем в таблицу новую строку
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