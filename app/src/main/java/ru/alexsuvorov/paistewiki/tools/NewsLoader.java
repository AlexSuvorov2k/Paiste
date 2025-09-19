package ru.alexsuvorov.paistewiki.tools;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ru.alexsuvorov.paistewiki.App;
import ru.alexsuvorov.paistewiki.db.AppDatabase;
import ru.alexsuvorov.paistewiki.db.dao.MonthDao;
import ru.alexsuvorov.paistewiki.db.dao.NewsDao;
import ru.alexsuvorov.paistewiki.model.Month;
import ru.alexsuvorov.paistewiki.model.News;

public class NewsLoader extends AsyncTask<Object, Void, Boolean> {

    private String monthIndex;
    private String yearIndex;
    private Context context;

    @Override
    protected Boolean doInBackground(Object[] params) {
        String URL = (String) params[0];
        context = (Context) params[1];
        AppDatabase db = AppDatabase.getDatabase(context);
        NewsDao newsDao = db.newsDao();
        MonthDao monthDao = db.monthDao();
        try {
            Document month = Jsoup.connect(URL).get();
            if (month != null) {
                //Список месяцев
                Elements monthRows = month.getElementsByClass("contlefta").select("tr");
                if (monthRows.size() > 1) {
                    for (int i = monthRows.size(); i > 0; i--) {
                        Element monthRowElement = monthRows.get(i - 1);  //check all tr tags                               i-1
                        Elements monthRowItems = monthRowElement.select("td");  //All(1), Prod, Artist
                        Element monthTitleElement = monthRowItems.first(); //Select All
                        Elements monthLinks = monthRowItems.select("a[href]");
                        Element monthLink = monthLinks.first();
                        String monthUrl = "http://paiste.com/e/news.php" + monthLink.attr("href");
                        String monthTitle = monthTitleElement.text();
                        Pattern pYear = Pattern.compile("year=[0-9]{4}");
                        Matcher mYear = pYear.matcher(monthUrl);
                        if (mYear.find()) {
                            String findY_Index1 = mYear.group(0);
                            String[] parts1 = findY_Index1.split("=");
                            yearIndex = parts1[1];
                        }
                        Pattern pMonth = Pattern.compile("month=\\d{1,12}");
                        Matcher mMonth = pMonth.matcher(monthUrl);
                        if (mMonth.find()) {
                            String findM_Index1 = mMonth.group(0);
                            if (clearValues(findM_Index1).length() == 1) {
                                String str = "0";
                                monthIndex = str.concat(clearValues(findM_Index1)); //08
                            } else {
                                monthIndex = clearValues(findM_Index1);  //11
                            }
                        }
                        int mIndex = Integer.valueOf(yearIndex.concat(monthIndex));
                        Log.d(getClass().getSimpleName(), "INDEX: " + mIndex);
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
                                    Element postsRowElement = postsRows.get(j);
                                    Elements postsRowItems = postsRowElement.select("td");
                                    Elements postsLinks = postsRowItems.select("a[href]");
                                    for (Element postLink : postsLinks) {
                                        String linkUrl = "http://paiste.com/e/news.php" + postLink.attr("href");
                                        String linkTitle = postLink.text();
                                        String linkCategory = postsRowItems.get(2).text();
                                        if (newsDao.insert(new News(0, linkTitle, linkCategory, linkUrl, mIndex)) > 0) {
                                            App.newsUpdated = true;
                                        }
                                    }
                                }
                            }
                            monthDao.insert(new Month(monthDao.getLastMonthId() + 1, monthTitle, monthUrl, mIndex));
                        }
                    }
                }
            }
        } catch (SocketTimeoutException exception) {
            exception.printStackTrace();
            App.errorCode = 1;
            return true;
        } catch (UnknownHostException exception) {
            exception.printStackTrace();
            App.errorCode = 2;
            Log.d(getClass().getSimpleName(), "Server error");
            return true;
        } catch (IOException exception) {
            exception.printStackTrace();
            App.errorCode = 3;
            return true;
        }
        App.errorCode = 0;
        return true;
    }

    private String clearValues(String input) {
        String[] parts = input.split("=");
        return parts[1];
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        Log.d(getClass().getSimpleName(), "Result is: " + result);
    }
}