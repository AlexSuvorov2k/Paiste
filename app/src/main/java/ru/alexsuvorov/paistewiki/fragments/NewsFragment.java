package ru.alexsuvorov.paistewiki.fragments;

import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.alexsuvorov.paistewiki.App;
import ru.alexsuvorov.paistewiki.R;
import ru.alexsuvorov.paistewiki.adapter.NewsAdapter;
import ru.alexsuvorov.paistewiki.db.AppDatabase;
import ru.alexsuvorov.paistewiki.db.dao.MonthDao;
import ru.alexsuvorov.paistewiki.model.Month;
import ru.alexsuvorov.paistewiki.tools.AppPreferences;

public class NewsFragment extends Fragment {

    NewsAdapter newsAdapter;
    AppPreferences appPreferences;
    //SliderLayout sliderLayout;
    private final int[] images = {R.drawable.banner1, R.drawable.banner2, R.drawable.banner3,
            R.drawable.banner4, R.drawable.banner5, R.drawable.banner6, R.drawable.banner7};

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
        ((App) getActivity().getApplication()).setLocale();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Context context = this.getContext();

        /*viewPager = view.findViewById(R.id.view_pager);
        BannerAdapter sliderAdapter = new BannerAdapter(getContext());
        viewPager.setAdapter(sliderAdapter);
        pageSwitcher(5);*/
        /*sliderLayout = view.findViewById(R.id.imageSlider);
        sliderLayout.setIndicatorAnimation(SliderLayout.Animations.THIN_WORM);
        sliderLayout.setScrollTimeInSec(2); //set scroll delay in seconds :*/
        setSliderViews();

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

    private void setSliderViews() {

        for (int i = 0; i <= 6; i++) {

            /*SliderView sliderView = new SliderView(getActivity());
            sliderView.setImageDrawable(images[i]);*/
            /*switch (i) {
                case 0:
                    sliderView.setImageUrl("https://images.pexels.com/photos/547114/pexels-photo-547114.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260");
                    break;
                case 1:
                    sliderView.setImageUrl("https://images.pexels.com/photos/218983/pexels-photo-218983.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260");
                    break;
                case 2:
                    sliderView.setImageUrl("https://images.pexels.com/photos/747964/pexels-photo-747964.jpeg?auto=compress&cs=tinysrgb&h=750&w=1260");
                    break;
                case 3:
                    sliderView.setImageUrl("https://images.pexels.com/photos/929778/pexels-photo-929778.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260");
                    break;
            }*/

            /*sliderView.setImageScaleType(ImageView.ScaleType.FIT_CENTER);
            //sliderView.setDescription("setDescription " + (i + 1));
            final int finalI = i;
            sliderView.setOnSliderClickListener(new SliderView.OnSliderClickListener() {
                @Override
                public void onSliderClick(SliderView sliderView) {
                    //Toast.makeText(getActivity(), "This is slider " + (finalI + 1), Toast.LENGTH_SHORT).show();
                }
            });

            //at last add this view in your layout :
            sliderLayout.addSliderView(sliderView);*/
        }
    }

    /*public void pageSwitcher(int seconds) {
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
    }*/

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //newConfig.locale = new Locale(appPreferences.getText("choosed_lang"));
        ((App) getContext()).setLocale();
        // your code here, you can use newConfig.locale if you need to check the language
        // or just re-set all the labels to desired string resource
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(R.string.nav_header_newsbutton);
    }
}