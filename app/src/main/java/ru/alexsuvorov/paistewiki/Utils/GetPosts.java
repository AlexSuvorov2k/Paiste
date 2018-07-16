package ru.alexsuvorov.paistewiki.Utils;

import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import ru.alexsuvorov.paistewiki.model.NewsPost;

public class GetPosts extends AsyncTask<String, Void, ArrayList<NewsPost>> {

    //private ProgressDialog progressdialog;
    //private ArrayList<NewsModel> newsArray = new ArrayList<NewsModel>();
    private String urlMain = "http://paiste.com/e/news.php?menuid=39&actn=monthlist&type=all&year=2017&month=11";
    //String urlMain = "http://paiste.com/e/news.php?menuid=39";

    private ArrayList<NewsPost> newsList = new ArrayList<NewsPost>();

    private final String TAG = "GetMonth";

    protected ArrayList<NewsPost> doInBackground(String... params) {
        try {
            Document doc = Jsoup.connect(urlMain).get();
            if (doc != null) {
                Elements tableRows = doc.getElementsByClass("contrighta").select("tr");
                //Название месяца
                String monthTitle = doc.getElementsByClass("contrighta").select("h1").text();

                //Log.d(TAG, "Title: " + title);
                    /*FOR IMAGES
                    http://stackoverflow.com/questions/10457415/extract-image-src-using-jsoup
                    */

                //Если есть новости
                if (tableRows.size() > 1) {
                    //String monthLabel = Html.fromHtml(title.toString()).toString();
                    //Log.d(TAG, "!!!!!!!"+(Html.fromHtml(title.toString()).toString())+"__"+monthLabel);
                    //начинать с первой новости, с 0 + пустая строка
                    for (int i = 1; i < tableRows.size(); i++) {
                        Element row = tableRows.get(i);
                        Elements rowItems = row.select("td");
                        //for (int j = 0; j < rowItems.size(); j++) { //Заголовок, дата, тип, язык
                        Elements links = rowItems.select("a[href]");
                        for (Element link : links) {
                            String linkUrl = "http://paiste.com/e/news.php" + link.attr("href");
                            String linkTitle = link.text();
                            String linkCategory = rowItems.get(3).text();
                            Log.d(TAG, "Text: " + linkTitle);
                            Log.d(TAG, "Link: " + linkUrl);
                            Log.d(TAG, "Category: " + linkCategory);
                            newsList.add(new NewsPost(linkTitle, linkCategory, linkUrl));
                        }
                    }
                } else {
                    newsList.add(new NewsPost("There is no news yet", null, null));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return newsList;
    }
}