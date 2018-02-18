package ru.alexsuvorov.paistewiki.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.brandongogetap.stickyheaders.StickyLayoutManager;
import com.brandongogetap.stickyheaders.exposed.StickyHeaderListener;

import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import ru.alexsuvorov.paistewiki.Adapter.CustomAdapter;
import ru.alexsuvorov.paistewiki.Adapter.RecyclerAdapter;
import ru.alexsuvorov.paistewiki.Adapter.TopSnappedStickyLayoutManager;
import ru.alexsuvorov.paistewiki.ItemModel.NewsModel;
import ru.alexsuvorov.paistewiki.R;
import ru.alexsuvorov.paistewiki.Utils.JsoupParser;

//HEADERS
//https://github.com/bgogetap/StickyHeaders


public class NewsFragment extends Fragment {

    private String urlMain = "http://paiste.com/e/news.php?menuid=39";
    private static final String TAG = "NewsFragment";
    //private String urlMain = "http://paiste.com/e/news.php?menuid=39&actn=monthlist&type=all&year=2017&month=10";
    private String linksText;
    private String linkNews;
    private Elements title;
    private TableLayout tableRow;
    private TextView Title;
    private CustomAdapter VPadapter;
    private JsoupParser parser;

    public static ArrayList<NewsModel> newsArray = new ArrayList<NewsModel>();
    //ArrayList<NewsModel> newsArray = (ArrayList<NewsModel>)getArguments().getSerializable("newsArray");
    private ListView newsList;
    RecyclerView recyclerView;

    private RecyclerAdapter adapter;

    //List<NewsModel> listArray = parser.getNews();
    //private ProgressDialog progressdialog;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_fragment, container, false);
        tableRow = view.findViewById(R.id.tableNews);
        Title = view.findViewById(R.id.labelNews);

        //newsList = view.findViewById(R.id.newsList);
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

        recyclerView = view.findViewById(R.id.recyclerView);

        ViewPager viewPager = view.findViewById(R.id.view_pager);
        VPadapter = new CustomAdapter(getContext());
        viewPager.setAdapter(VPadapter);

        /*NewsAdapter newsAdapter = new NewsAdapter(this.getContext(),
                R.layout.news_item, newsArray);
        newsList.setAdapter(newsAdapter);*/
        adapter = new RecyclerAdapter();
        adapter.setData(JsoupParser.getList());
        StickyLayoutManager layoutManager = new TopSnappedStickyLayoutManager(this.getContext(), adapter);
        layoutManager.elevateHeaders(true); // Default elevation of 5dp
        // You can also specify a specific dp for elevation
//        layoutManager.elevateHeaders(10);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        layoutManager.setStickyHeaderListener(new StickyHeaderListener() {
            @Override
            public void headerAttached(View headerView, int adapterPosition) {
                Log.d("Listener", "Attached with position: " + adapterPosition);
            }

            @Override
            public void headerDetached(View headerView, int adapterPosition) {
                Log.d("Listener", "Detached with position: " + adapterPosition);
            }
        });
        /*
        view.findViewById(R.id.visibility_button).setOnClickListener(v ->
                recyclerView.setVisibility(recyclerView.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE));*/
        return view;
    }

    void setItems(List<NewsModel> items) {
        if (adapter != null) {
            adapter.setData(items);
        }
    }
}