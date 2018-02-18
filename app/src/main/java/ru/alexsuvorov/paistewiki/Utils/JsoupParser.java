package ru.alexsuvorov.paistewiki.Utils;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import ru.alexsuvorov.paistewiki.ItemModel.NewsHeaderItem;
import ru.alexsuvorov.paistewiki.ItemModel.NewsModel;

public class JsoupParser extends AsyncTask<Void, Void, ArrayList<NewsModel>> {

    private ProgressDialog progressdialog;
    //private ArrayList<NewsModel> newsArray = new ArrayList<NewsModel>();
    private String title;
    private static final String TAG = "JsoupParser";

    //private String urlMain = "http://paiste.com/e/news.php?menuid=39";
    private String urlMain = "http://paiste.com/e/news.php?menuid=39&actn=monthlist&type=all&year=2017&month=11";
    private String linksUrl;
    private String linksText;

    static ArrayList<NewsModel> newsList = new ArrayList<NewsModel>();

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


    protected ArrayList<NewsModel> doInBackground(Void... params) {
        try {
            Document doc = Jsoup.connect(urlMain).get();
            if (doc != null) {
                Elements tableRows = doc.getElementsByClass("contrighta").select("tr");
                title = doc.getElementsByClass("contrighta").select("h1").text();
                //Log.d(TAG, "Title: " + title);
                    /*FOR IMAGES
                    http://stackoverflow.com/questions/10457415/extract-image-src-using-jsoup
                    */
                //Если есть новости
                if (tableRows.size() > 1) {
                    //String monthLabel = Html.fromHtml(title.toString()).toString();
                    newsList.add(new NewsHeaderItem(title.toString(), null));
                    //Log.d(TAG, "!!!!!!!"+(Html.fromHtml(title.toString()).toString())+"__"+monthLabel);
                    //(Html.fromHtml(title.toString()).toString())
                    //начинать с первой новости, с 0 + пустая строка
                    for (int i = 1; i < tableRows.size(); i++) {
                        Element row = tableRows.get(i);
                        //ArrayList<String> arrayList = new ArrayList<String>();
                        Elements rowItems = row.select("td");
                        //arrayList.add(rowItems.get(0).text());
                        //for (int j = 0; j < rowItems.size(); j++) { //Заголовок, дата, тип, язык
                        Elements links = rowItems.select("a[href]");
                        for (Element link : links) {
                            linksUrl = "http://paiste.com/e/news.php" + link.attr("href");
                            linksText = link.text();
                            //Log.d(TAG, "Text: " + linksText);
                            //Log.d(TAG, "Link: " + linksUrl);
                            newsList.add(new NewsModel(linksText, linksUrl));
                        }
                    }
                }
            }
            return newsList;

        } catch (IOException e) {
            return new ArrayList<NewsModel>();
            //e.printStackTrace();
        }/*
        for (int i = 0; i < listArray.size(); i++) {
            Log.d(TAG, listArray.get(i).getTitle());
        }*/
    }

    public static ArrayList<NewsModel> getList() {
        return newsList;
    }
/*
    @Override
    public void onPostExecute(List<NewsModel> list) {
        //super.onPostExecute(ArrayList<NewsModel> listArray);
        Log.e("Data list size: ", String.valueOf(list.size()));
        return listArray;
    }*/
}
            /*Bundle bundle = new Bundle();
            bundle.putString("Title", String.valueOf(title));*/
//bundle.putSerializable("newsArray",newsArray);
        /*FragmentManager fragmentManager = getSupportFragmentManager();
        fragment.setArguments(bundle);
        fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();*/
//progressdialog.dismiss();


//Title.setText(title.text());
//if (hashMap.size() > 1) {
               /* TextView textNews;
                for (String[] rows : newsArray) {
                    textNews = new TextView(getApplicationContext());
                    textNews.setGravity(Gravity.LEFT);
                    //linkNews= "<a href="+linksUrl+">"+linksText+"</a>";
                    linkNews = "<a href=" + rows[0] + ">" + rows[1] + "</a>";
                    textNews.setText(Html.fromHtml(linkNews));

                    TableRow row = new TableRow(getApplicationContext());
                    row.addView(textNews);*/ // добавляем в строку столбец с именем пользователя
//tableRow.addView(row); // добавляем в таблицу новую строку