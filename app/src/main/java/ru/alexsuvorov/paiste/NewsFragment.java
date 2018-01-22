package ru.alexsuvorov.paiste;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NewsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private String urlMain = "http://paiste.com/e/news.php?menuid=39&actn=monthlist&type=all&year=2017&month=10";
    private String linksUrl;
    private String linksText;
    private String linkNews;
    private Elements title;
    private TableLayout tableRow;
    private TextView Title;
    private ViewPager viewPager;
    private CustomAdapter adapter;

    private Hashmap<Integer, ArrayList<String>> hashMap = new Hashmap<Integer, ArrayList<String>>();
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private List<String[]> newsArray = new ArrayList<String[]>();

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_fragment, container, false);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        onRefresh();
        tableRow = (TableLayout) view.findViewById(R.id.tableNews);
        Title = (TextView) view.findViewById(R.id.labelNews);
        viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        adapter = new CustomAdapter(getContext());
        viewPager.setAdapter(adapter);

        mSwipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_purple,
                android.R.color.holo_red_dark,
                android.R.color.black,
                android.R.color.holo_red_light);


        return view;
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);

                try {
                    MyTask mt = new MyTask();
                    mt.execute();
                }catch (Exception e) {
                    e.toString();
                }
            }
        }, 3000);
    }

    class MyTask extends AsyncTask<String, Void, Void>{

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            tableRow.removeAllViews();
            newsArray.removeAll(newsArray);
        }

        protected Void doInBackground(String... params) {
            int count = 0;
            try {
                Document doc = Jsoup.connect(urlMain).get();
                if (doc != null) {

                    Elements tableRows = doc.getElementsByClass("contrighta").select("tr");

                    title = doc.getElementsByClass("contrighta").select("h1");

                    /*
                    FOR IMAGES
                    http://stackoverflow.com/questions/10457415/extract-image-src-using-jsoup
                    */

                    if (tableRows.size() > 1) {
                        for (int i = 1; i < tableRows.size(); i++) { //начинать с первой новости, с 0 + пустая строка

                            Element row = tableRows.get(i);
                            ArrayList<String> arrayList = new ArrayList<String>();
                            Elements rowItems = row.select("td");

                            //Удобный поссылочный парсинг
                            Elements links = rowItems.select("a[href]");

                            for (Element link : links) {

                                linksUrl = "http://paiste.com/e/news.php" + link.attr("href");
                                linksText = link.text();

                                newsArray.add(new String[]{linksUrl, linksText});
                            }

                            arrayList.add(rowItems.get(0).text());
                            //for (int j = 0; j < rowItems.size(); j++) { //Заголовок, дата, тип, язык

                            hashMap.put(count, arrayList);  //Помещаем строку с ключём count
                            count++;
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            Title.setText(title.text());

            if (hashMap.size() > 1) {

                TextView textNews;

                for (String[] rows : newsArray) {

                    textNews = new TextView(getContext());
                    textNews.setGravity(Gravity.LEFT);

                    //linkNews= "<a href="+linksUrl+">"+linksText+"</a>";
                    linkNews = "<a href=" + rows[0] + ">" + rows[1] + "</a>";
                    textNews.setText(Html.fromHtml(linkNews));
                    Log.d("onPostExecute", "NEWS: " + linkNews);
                    textNews.setMovementMethod(LinkMovementMethod.getInstance());
                    Log.d("onPostExecute", "Text: " + linksText);
                    Log.d("onPostExecute", "Link: " + linksUrl);
                    textNews.setTextSize(16);
                    textNews.setTextColor(getResources().getColor(android.R.color.black));

                    TableRow row = new TableRow(getContext());
                    row.addView(textNews); // добавляем в строку столбец с именем пользователя
                    tableRow.addView(row); // добавляем в таблицу новую строку
                }
            } else {
                Log.d("onPostExecute", "End");
                TextView textNews = new TextView(getContext(), null, R.style.BodyText);
                textNews.setGravity(Gravity.LEFT);
                textNews.setTextSize(18);
                textNews.setTextColor(0xff000000);
                textNews.setText(getResources().getString(R.string.emptyNews));  //Если нет новостей
                TableRow row = new TableRow(getContext());
                row.addView(textNews); // добавляем в строку столбец с именем пользователя
                tableRow.addView(row); // добавляем в таблицу новую строку
            }

            mSwipeRefreshLayout.setRefreshing(false);// Отменяем анимацию обновления
        }

    }
}