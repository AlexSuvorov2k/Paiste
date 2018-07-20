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
import java.util.List;

import ru.alexsuvorov.paistewiki.Adapter.ImageSliderAdapter;
import ru.alexsuvorov.paistewiki.Adapter.NewsAdapter;
import ru.alexsuvorov.paistewiki.R;
import ru.alexsuvorov.paistewiki.model.NewsMonth;

public class NewsFragment extends Fragment {

    private static final String TAG = "NewsFragment";
    private ImageSliderAdapter sliderAdapter;
    private List<NewsMonth> monthArray;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.news_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ViewPager viewPager = view.findViewById(R.id.view_pager);
        sliderAdapter = new ImageSliderAdapter(getContext());
        viewPager.setAdapter(sliderAdapter);

        initializeData();
        RecyclerView recyclerView = view.findViewById(R.id.newsList);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(llm);

        NewsAdapter adapter = new NewsAdapter(monthArray); /*this.getContext(), R.layout.news_item, monthArray*/
        recyclerView.setAdapter(adapter);
    }

    private void initializeData() {
        monthArray = new ArrayList<>();
        monthArray.add(new NewsMonth("www.ya.ru", "Январь", null));
        monthArray.add(new NewsMonth("www.on.ru", "Февраль", null));
        monthArray.add(new NewsMonth("www.ti.ru", "Март", null));
    }
}