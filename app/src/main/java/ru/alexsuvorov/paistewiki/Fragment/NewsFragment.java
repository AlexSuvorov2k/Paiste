package ru.alexsuvorov.paistewiki.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;

import ru.alexsuvorov.paistewiki.Adapter.ImageSliderAdapter;
import ru.alexsuvorov.paistewiki.Adapter.NewsAdapter;
import ru.alexsuvorov.paistewiki.R;
import ru.alexsuvorov.paistewiki.Utils.NewsLoader;
import ru.alexsuvorov.paistewiki.model.NewsMonth;
import ru.alexsuvorov.paistewiki.model.NewsPost;

public class NewsFragment extends Fragment /*implements NewsAdapter.OnItemClickListener */ {

    private List<NewsMonth> monthArray;
    private List<NewsPost> postArray;
    private TableLayout tableLayout;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.news_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ViewPager viewPager = view.findViewById(R.id.view_pager);
        ImageSliderAdapter sliderAdapter = new ImageSliderAdapter(getContext());
        viewPager.setAdapter(sliderAdapter);
        initializeData();
        RecyclerView recyclerView = view.findViewById(R.id.newsList);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity())); //getActivity(), LinearLayoutManager.VERTICAL, false
        NewsAdapter adapter = new NewsAdapter(monthArray);
        recyclerView.setAdapter(adapter);
        //adapter.setOnItemClickListener(this);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    /*@Override public void onItemClick(View view, ViewModel viewModel) {
        // Lire le son ici
        Integer sound = getResources().getIdentifier(viewModel.getSound(), "raw", getActivity().getPackageName());
        playSound(sound);
    }*/

    private void initializeData() {
        monthArray = NewsLoader.getMonthList();
        postArray = NewsLoader.getPostsList();
        setPosts();
        /*monthArray.add(new NewsMonth("Июль", "WHO IS...JODY GIACHELLO", null));
        //monthArray.get(1).setMonthPosts(monthArray).add(new NewsMonth("Июль", "опачки!", null));
        monthArray.add(new NewsMonth("Июнь", "THE NEW PST X - Craig Blundell", null));
        monthArray.add(new NewsMonth("Май", "Барабанщик забил гол", null));
        monthArray.add(new NewsMonth("Апрель", "йцуйцуйц забил гол", null));
        monthArray.add(new NewsMonth("Март", "Барабанщик забил гол", null));
        monthArray.add(new NewsMonth("Февраль", "йцуйуц забил гол", null));*/
    }

    private void setPosts() {
        int position = NewsAdapter.getMonth();
        for (int i = 0; i < postArray.size(); i++) {
            TextView postLabel = new TextView(getContext());
            postLabel.setGravity(Gravity.START);
            postLabel.setText(monthArray.get(position).getPosts().get(i).getTitle());
            TableRow row = new TableRow(getContext());
            row.addView(postLabel); // добавляем в строку столбец с именем пользователя
            tableLayout.addView(row); // добавляем в таблицу новую строку
            //ViewHolder.newsLabel.setText(months.get(i).getMonthURL());
        }
    }
}