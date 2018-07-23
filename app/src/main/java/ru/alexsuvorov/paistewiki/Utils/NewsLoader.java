package ru.alexsuvorov.paistewiki.Utils;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import ru.alexsuvorov.paistewiki.model.NewsMonth;
import ru.alexsuvorov.paistewiki.model.NewsPost;

public class NewsLoader extends AsyncTask<String, Void, ArrayList<NewsMonth>> {

    public static ArrayList<NewsMonth> monthList = new ArrayList<>();
    public static ArrayList<NewsPost> postsList = new ArrayList<>();
    private String TAG = "NewsLoader";

    /*@Override
    protected void onProgressUpdate(Void values) {
        txt.setText("Running..."+ values[0]);
        progressBar.setProgress(values[0]);
    }*/

    @Override
    protected ArrayList<NewsMonth> doInBackground(String... urls) {
        String URL = urls[0];
        try {
            Document month = Jsoup.connect(URL).get();
            if (month != null) {
                //Список месяцев
                Elements monthRows = month.getElementsByClass("contlefta").select("tr");
                if (monthRows.size() > 1) {
                    for (int i = 0; i < monthRows.size(); i++) {
                        Element monthRow = monthRows.get(i);  //check all tr tags
                        Elements monthRowItems = monthRow.select("td");  //All(1), Prod, Artist
                        Element title = monthRowItems.first(); //Select All
                        Elements links = monthRowItems.select("a[href]");
                        Element link = links.first();
                        String monthUrl = "http://paiste.com/e/news.php" + link.attr("href");
                        String monthTitle = title.text();

                        //Log.d(TAG, "Link: " + monthUrl);
                        //Log.d(TAG, "Title: " + monthTitle);
                        //---------------------------------------------------
                        Document posts = Jsoup.connect(monthUrl).get();
                        if (posts != null) {
                            Elements postsRows = posts.getElementsByClass("contrighta").select("tr");
                            /*FOR IMAGES
                            http://stackoverflow.com/questions/10457415/extract-image-src-using-jsoup
                            */

                            //Если есть новости
                            if (postsRows.size() > 1) {
                                //начинать с первой новости, с 0 + пустая строка
                                for (int j = 0; j < postsRows.size(); j++) {
                                    Element row = postsRows.get(j);
                                    Elements rowItems1 = row.select("td");
                                    Elements links1 = rowItems1.select("a[href]");
                                    for (Element link1 : links1) {
                                        String linkUrl = "http://paiste.com/e/news.php" + link1.attr("href");
                                        String linkTitle = link1.text();
                                        String linkCategory = rowItems1.get(2).text();
                                        //Log.d(TAG, "Text: " + linkTitle);
                                        //Log.d(TAG, "Link: " + linkUrl);
                                        //Log.d(TAG, "Category: " + linkCategory);
                                        postsList.add(new NewsPost(linkTitle, linkCategory, linkUrl));
                                    }
                                }
                            } else {
                                //Log.d(TAG, "There is no news yet");
                                postsList.add(new NewsPost("There is no news yet", null, null));
                            }
                        }
                        monthList.add(new NewsMonth(monthTitle, monthUrl, postsList));
                        publishProgress();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            /*Toast toast = Toast.makeText(getContext(),
                    "Ошибка соединения!", Toast.LENGTH_SHORT);
            toast.show();*/
        }
        return monthList;

    }

    public static ArrayList<NewsMonth> getMonthList() {
        return monthList;
    }

    public static ArrayList<NewsPost> getPostsList() {
        return postsList;
    }
}