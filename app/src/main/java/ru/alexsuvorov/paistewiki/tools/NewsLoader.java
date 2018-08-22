package ru.alexsuvorov.paistewiki.tools;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import ru.alexsuvorov.paistewiki.model.NewsMonth;
import ru.alexsuvorov.paistewiki.model.NewsPost;

public class NewsLoader extends AsyncTask<String, Void, Boolean> {

    private static ArrayList<NewsMonth> monthList = new ArrayList<>();

    @Override
    protected Boolean doInBackground(String... urls) {
        String URL = urls[0];
        try {
            Document month = Jsoup.connect(URL).get();
            if (month != null) {
                //Список месяцев
                Elements monthRows = month.getElementsByClass("contlefta").select("tr");
                if (monthRows.size() > 1) {
                    for (int i = 0; i < monthRows.size(); i++) {
                        Element monthRowElement = monthRows.get(i);  //check all tr tags
                        Elements monthRowItems = monthRowElement.select("td");  //All(1), Prod, Artist
                        Element monthTitleElement = monthRowItems.first(); //Select All
                        Elements monthLinks = monthRowItems.select("a[href]");
                        Element monthLink = monthLinks.first();
                        String monthUrl = "http://paiste.com/e/news.php" + monthLink.attr("href");
                        String monthTitle = monthTitleElement.text();
                        //---------------------------------------------------
                        Document posts = Jsoup.connect(monthUrl).get();
                        if (posts != null) {
                            Elements postsRows = posts.getElementsByClass("contrighta").select("tr");
                            /*FOR IMAGES
                            http://stackoverflow.com/questions/10457415/extract-image-src-using-jsoup
                            */

                            ArrayList<NewsPost> postsList = new ArrayList<>();
                            //Если есть новости
                            if (postsRows.size() > 1) {
                                //начинать с первой новости, с 0 + пустая строка
                                for (int j = 0; j < postsRows.size(); j++) {
                                    Element postsRowElement = postsRows.get(j);
                                    Elements postsRowItems = postsRowElement.select("td");
                                    Elements postsLinks = postsRowItems.select("a[href]");
                                    for (Element postLink : postsLinks) {
                                        String linkUrl = "http://paiste.com/e/news.php" + postLink.attr("href");
                                        String linkTitle = postLink.text();
                                        String linkCategory = postsRowItems.get(2).text();
                                        postsList.add((new NewsPost(linkTitle, linkCategory, linkUrl, monthTitle)));

                                    }
                                }
                            } else {
                                postsList.add(new NewsPost("There is no news yet", "News", URL, null));
                            }
                            NewsMonth newsMonth = new NewsMonth();
                            newsMonth.setMonthName(monthTitle);
                            newsMonth.setMonthURL(monthUrl);
                            newsMonth.setMonthPosts(postsList);
                            monthList.add(newsMonth);
                            //Отправляем данные в базу через Dao?
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
    }

    public static ArrayList<NewsMonth> getMonthList() {
        //Сносим к чертям
        return monthList;
    }

}