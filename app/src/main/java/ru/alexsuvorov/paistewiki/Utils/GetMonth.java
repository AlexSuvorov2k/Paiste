package ru.alexsuvorov.paistewiki.Utils;

import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import ru.alexsuvorov.paistewiki.model.NewsMonth;

public class GetMonth extends AsyncTask<Void, Void, ArrayList<NewsMonth>> {

    public ArrayList<NewsMonth> monthList = new ArrayList<NewsMonth>();
    private final String TAG = "GetMonth";

    @Override
    protected ArrayList<NewsMonth> doInBackground(Void... voids) {
        String urlNews = "http://paiste.com/e/news.php?menuid=39";
        Document doc = null;
        try {
            doc = Jsoup.connect(urlNews).get();
            if (doc != null) {
                //Список месяцев
                Elements monthRows = doc.getElementsByClass("contlefta").select("tr");
                if (monthRows.size() > 1) {
                    for (int i = 1; i < monthRows.size(); i++) {
                        Element monthRow = monthRows.get(i);
                        Elements rowItems = monthRow.select("td");  //All, Prod, Artist
                        Elements links = rowItems.select("a[href]");
                        for (Element link : links) {
                            String monthUrl = "http://paiste.com/e/news.php" + link.attr("href");
                            //Название месяца
                            String monthTitle = doc.getElementsByClass("contlefta").select("td").text();
                            Log.d(TAG, "Link: " + monthUrl);
                            Log.d(TAG, "Title: " + monthTitle);
                            monthList.add(new NewsMonth(monthUrl, monthTitle));
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return monthList;
    }
}
