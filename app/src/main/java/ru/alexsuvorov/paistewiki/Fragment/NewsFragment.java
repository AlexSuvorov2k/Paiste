package ru.alexsuvorov.paistewiki.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;

import org.jsoup.select.Elements;

import java.util.ArrayList;

import ru.alexsuvorov.paistewiki.Adapter.CustomAdapter;
import ru.alexsuvorov.paistewiki.Adapter.NewsAdapter;
import ru.alexsuvorov.paistewiki.ItemModel.NewsModel;
import ru.alexsuvorov.paistewiki.R;
import ru.alexsuvorov.paistewiki.Utils.JsoupParser;

public class NewsFragment extends Fragment {

    private String urlMain = "http://paiste.com/e/news.php?menuid=39";
    private static final String TAG = "NewsFragment";
    //private String urlMain = "http://paiste.com/e/news.php?menuid=39&actn=monthlist&type=all&year=2017&month=10";
    private String linksText;
    private String linkNews;
    private Elements title;
    private TableLayout tableRow;
    private TextView Title;
    private CustomAdapter adapter;
    private JsoupParser parser;

    public static ArrayList<NewsModel> newsArray = new ArrayList<NewsModel>();
    //ArrayList<NewsModel> newsArray = (ArrayList<NewsModel>)getArguments().getSerializable("newsArray");
    private ListView newsList;

    //List<NewsModel> listArray = parser.getNews();
    //private ProgressDialog progressdialog;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_fragment, container, false);
        tableRow = view.findViewById(R.id.tableNews);
        Title = view.findViewById(R.id.labelNews);

        newsList = view.findViewById(R.id.newsList);
        /*savedInstanceState = this.getArguments();
        assert savedInstanceState != null;
        String title = savedInstanceState.getString("Title");*/

        /*Log.d(TAG, "Title is: " + title);
        Title.setText(Html.fromHtml(title));*/

        /*
        textNews.setMovementMethod(LinkMovementMethod.getInstance());
        textNews.setTextSize(18);
        textNews.setTextColor(getResources().getColor(android.R.color.black));
        textNews.setLinkTextColor(getResources().getColor(android.R.color.black));
        textNews.setTypeface(Typeface.DEFAULT_BOLD);
        */

        ViewPager viewPager = view.findViewById(R.id.view_pager);
        adapter = new CustomAdapter(getContext());
        viewPager.setAdapter(adapter);

        NewsAdapter newsAdapter = new NewsAdapter(this.getContext(),
                R.layout.news_item, newsArray);
        newsList.setAdapter(newsAdapter);

        return view;
    }
}