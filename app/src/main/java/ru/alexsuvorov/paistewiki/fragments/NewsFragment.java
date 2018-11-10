package ru.alexsuvorov.paistewiki.fragments;

import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import ru.alexsuvorov.paistewiki.R;
import ru.alexsuvorov.paistewiki.adapter.BannerAdapter;
import ru.alexsuvorov.paistewiki.adapter.NewsAdapter;
import ru.alexsuvorov.paistewiki.db.AppDatabase;
import ru.alexsuvorov.paistewiki.db.dao.MonthDao;
import ru.alexsuvorov.paistewiki.model.Month;
import ru.alexsuvorov.paistewiki.tools.AppPreferences;

public class NewsFragment extends Fragment {

    private int viewPagerCurrentPage = 0;
    private ViewPager viewPager;
    NewsAdapter newsAdapter;
    AppPreferences appPreferences;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setRetainInstance(true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        appPreferences = new AppPreferences(this.getContext());
        Locale locale = new Locale(appPreferences.getText("choosed_lang"));
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        context.getResources().updateConfiguration(config,
                context.getResources().getDisplayMetrics());
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
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
        newsAdapter = new NewsAdapter(monthArray, this.getActivity());
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
        timer.scheduleAtFixedRate(new RemindTask(), 0, seconds * 900);
    }

    class RemindTask extends TimerTask {
        @Override
        public void run() {
            if (getActivity() != null)
                getActivity().runOnUiThread(() -> {
                    if (viewPagerCurrentPage > 10) {
                        viewPagerCurrentPage = 0;
                        //timer.cancel();
                    } else {
                        viewPager.setCurrentItem(viewPagerCurrentPage++);
                    }
                });

        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        newConfig.locale = new Locale(appPreferences.getText("choosed_lang"));

        // your code here, you can use newConfig.locale if you need to check the language
        // or just re-set all the labels to desired string resource
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(R.string.nav_header_newsbutton);
    }
}