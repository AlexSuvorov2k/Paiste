package ru.alexsuvorov.paistewiki;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import java.util.ArrayList;

import ru.alexsuvorov.paistewiki.ItemModel.NewsModel;
import ru.alexsuvorov.paistewiki.Utils.JsoupParser;
import ru.alexsuvorov.paistewiki.Utils.Utils;

public class Splash extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        if (Utils.isNetworkAvailable(this)) {
            final JsoupParser asyncTask = new JsoupParser();
            new asyncTask() {

                @Override
                protected void onPostExecute(ArrayList<NewsModel> newsModels) {
                    asyncTask.onPostExecute();
                }
                /*@Override
                protected void onPostExecute(ArrayList<NewsModel> list) {
                    //use the list from parseURL to fill grid view
                    //ArrayAdapter<String> adapter = new ArrayAdapter<NewsModel>(this, android.R.layout.simple_list_item_1, list);

                    /*gridView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }*/
            }.execute();


            asyncTask.execute();
            //newsList = new JsoupParser().getNews();
        } else {
            Toast.makeText(this, "No Network Connection", Toast.LENGTH_LONG).show();
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(Splash.this, StartDrawer.class);
                startActivity(i);
                finish();
            }
        }, 2 * 1000);

    }
    /*
    class GetData extends AsyncTask<String, Void, Void> {

        //ProgressDialog pDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            /*pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Loading...");
            pDialog.show();
        }

        @Override
        protected Void doInBackground(String... params) {
            newsList = new JsoupParser().getNews();
            return null;
        }

        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (null == newsList || newsList.size() == 0) {
                Toast.makeText(getApplicationContext(), "No data found from web", Toast.LENGTH_LONG).show();
            } else {
                NewsAdapter objAdapter = new NewsAdapter(getApplicationContext(),
                        R.layout.news_item, newsList);
                lv.setAdapter(objAdapter);
            }
        }

    }*/
}
