package ru.alexsuvorov.paistewiki.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ru.alexsuvorov.paistewiki.R;
import ru.alexsuvorov.paistewiki.model.NewsMonth;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsCardViewHolder> {

    private List<NewsMonth> months;

    public NewsAdapter(List<NewsMonth> months) {
        this.months = months;
    }

    class NewsCardViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView monthName;
        TextView newsLabel;
        TextView newsCategory;
        //TextView newsLink;

        NewsCardViewHolder(View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.cv);
            monthName = itemView.findViewById(R.id.month_name);
            newsLabel = itemView.findViewById(R.id.news_label);
            newsCategory = itemView.findViewById(R.id.news_category);
        }
    }

    @NonNull
    @Override
    public NewsCardViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.news_item, viewGroup, false);
        return new NewsCardViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsCardViewHolder monthViewHolder, int i) {
        monthViewHolder.monthName.setText(months.get(i).getMonthName());
        monthViewHolder.newsLabel.setText(months.get(i).getMonthURL());
        //monthViewHolder.newsCategory.setText(months.get(i).age);
    }

    @Override
    public int getItemCount() {
        return months.size();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    /*
    //http://android-delight.blogspot.ru/2015/12/tablelayout-like-listview-multi-column.html
    @NonNull
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        final ViewHolder holder;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert inflater != null;
            view = inflater.inflate(row, null);
            holder = new ViewHolder();
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        if ((news == null) || ((position + 1) > news.size()))
            return view;

        NewsMonth newsModel = news.get(position);

        holder.titleNews = view.findViewById(R.id.titleNews);
        //holder.tvMonth = view.findViewById(R.id.tvMonth);
        //holder.catNews = view.findViewById(R.id.catNews);

        /*if (holder.titleNews != null && null != newsModel.getTitle()
                && newsModel.getTitle().trim().length() > 0) {
            holder.titleNews.setText(newsModel.getTitle());
            holder.titleNews.setMovementMethod(LinkMovementMethod.getInstance());
        }*/


        /*if (holder.imgView != null) {
            if (null != objBean.getLink()
                    && objBean.getLink().trim().length() > 0) {
                Picasso.with(this.getContext().getApplicationContext())
                        .load(String.valueOf(Html.fromHtml(objBean.getLink())))
                        .resize(250, 190)
                        .placeholder(R.mipmap.ic_empty)
                        .centerCrop()
                        .into(holder.imgView);
            } else {
                holder.imgView.setImageResource(R.mipmap.ic_launcher);
            }
        }*/

        /*
        holder.btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Item item = getItem(position);
                Bundle bundle = new Bundle();
                assert item != null;
                bundle.putString("ID", item.getId());
                bundle.putString("Title", item.getTitle());
                bundle.putString("ImgLink", item.getLink());
                bundle.putString("Text", item.getText());
                bundle.putString("DopText", item.getDopText());

                Fragment fragment = null;
                try {
                    fragment = ArticleFragment.class.newInstance();
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
                if (fragment != null) {
                    fragment.setArguments(bundle);
                }

                ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.flContent, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        return view;

    private class ViewHolder {
        private TextView titleNews, catNews, tvMonth;
    }*/
}
