package ru.alexsuvorov.paistewiki.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import ru.alexsuvorov.paistewiki.Adapter.CustomAdapter;
import ru.alexsuvorov.paistewiki.Adapter.NewsAdapter;
import ru.alexsuvorov.paistewiki.R;
import ru.alexsuvorov.paistewiki.model.NewsMonth;

public class NewsFragment extends Fragment {

    private static final String TAG = "NewsFragment";
    private CustomAdapter VPadapter;

    public static ArrayList<NewsMonth> newsArray = new ArrayList<NewsMonth>();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_fragment, container, false);

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

        // = view.findViewById(R.id.recyclerView);

        ViewPager viewPager = view.findViewById(R.id.view_pager);
        VPadapter = new CustomAdapter(getContext());
        viewPager.setAdapter(VPadapter);

        /*NewsAdapter newsAdapter = new NewsAdapter(this.getContext(),
                R.layout.news_item, newsArray);
        newsList.setAdapter(newsAdapter);*/
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.newsList);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(llm);

        NewsAdapter adapter = new NewsAdapter(month);
        recyclerView.setAdapter(adapter);
        //adapter.setData(newsList);

        //recyclerView.setAdapter(adapter);

        return view;
    }
}