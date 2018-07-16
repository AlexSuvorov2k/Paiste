package ru.alexsuvorov.paistewiki.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import ru.alexsuvorov.paistewiki.R;
import ru.alexsuvorov.paistewiki.model.NewsMonth;

public class NewsAdapter extends ArrayAdapter<NewsMonth> {

    private Activity activity;
    private List<NewsMonth> news;
    private int row;


    public NewsAdapter(@NonNull Context context, int resource, List<NewsMonth> news) {
        super(context, resource, news);
        this.activity = activity;
        this.news = news;
        this.row = row;
    }


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
        });*/

        return view;
    }

    private class ViewHolder {
        private TextView titleNews, catNews, tvMonth;
    }
}
