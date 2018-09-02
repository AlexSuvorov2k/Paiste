package ru.alexsuvorov.paistewiki.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import ru.alexsuvorov.paistewiki.R;
import ru.alexsuvorov.paistewiki.adapter.BannerAdapter;
import ru.alexsuvorov.paistewiki.adapter.NewsAdapter;
import ru.alexsuvorov.paistewiki.db.AppDatabase;
import ru.alexsuvorov.paistewiki.db.dao.MonthDao;
import ru.alexsuvorov.paistewiki.model.Month;

public class NewsFragment extends Fragment {

    private int viewPagerCurrentPage = 0;
    private ViewPager viewPager;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.news_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Context context = this.getContext();
        viewPager = view.findViewById(R.id.view_pager);
        BannerAdapter sliderAdapter = new BannerAdapter(getContext());
        viewPager.setAdapter(sliderAdapter);
        pageSwitcher(5);
        AppDatabase db = AppDatabase.getDatabase(context);

        MonthDao monthDao = db.monthDao();
        RecyclerView recyclerView = view.findViewById(R.id.newsList);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        List<Month> monthArray = monthDao.getAllMonth();
        NewsAdapter newsAdapter = new NewsAdapter(monthArray, this.getActivity());
        recyclerView.setAdapter(newsAdapter);
        newsAdapter.notifyDataSetChanged();


        newsAdapter.setOnItemClickListner(new NewsAdapter.onItemClickListner() {
            @Override
            public void onClick(String str) {
                //CUSTOM TABS
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                builder.setToolbarColor(getResources().getColor(R.color.colorPrimary));
                builder.setShowTitle(true);
                //builder.setCloseButtonIcon(BitmapFactory.decodeResource(
                //        getResources(), R.drawable.ic_arrow_back));
                CustomTabsIntent customTabsIntent = builder.build();
                customTabsIntent.launchUrl(getContext(), Uri.parse(str));
            }
        });
        recyclerView.setItemAnimator(new DefaultItemAnimator());


    }

    public void pageSwitcher(int seconds) {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new RemindTask(), 0, seconds * 1000);
    }

    class RemindTask extends TimerTask {
        @Override
        public void run() {
            if (getActivity() != null)
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        if (viewPagerCurrentPage > 6) {
                            viewPagerCurrentPage = 0;
                            //timer.cancel();
                        } else {
                            viewPager.setCurrentItem(viewPagerCurrentPage++);
                        }
                    }
                });

        }
    }
}