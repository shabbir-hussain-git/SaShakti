package com.example.shabbir.sashakti;

import android.animation.LayoutTransition;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

public class News extends AppCompatActivity {

    TextView txt;
    ProgressDialog pd;
    String returnNews;
    JSONObject jsonObject,jsonObject1;
    JSONArray news;
    ArrayList<NewsItem> arr;
    NewsItem newsItem[];
    int pos;
    Intent i;
    ArrayAdapter<NewsItem> adapter;

    Bitmap image;

    CardView cardView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        //This method is used to check internet connection before opening app
//        if(!checkConnection())
//        {
//            Toast.makeText(this,"No internet connection",Toast.LENGTH_LONG).show();
//            Intent i=new Intent(this,Home.class);
//            startActivity(i);
//            finish();
//        }


            String a[] = {
                    "https://newsapi.org/v1/articles?source=the-hindu&sortBy=latest&apiKey=899f63f5b7084937bccd7419bb8be942",
                    " https://newsapi.org/v1/articles?source=google-news&sortBy=top&apiKey=899f63f5b7084937bccd7419bb8be942",
                    " https://newsapi.org/v1/articles?source=the-times-of-india&sortBy=top&apiKey=899f63f5b7084937bccd7419bb8be942",
                    "https://newsapi.org/v1/articles?source=the-verge&sortBy=top&apiKey=899f63f5b7084937bccd7419bb8be942"
            };
            Random rand = new Random();
            int n = rand.nextInt(3) + 0;
         cardView=(CardView)findViewById(R.id.progress);

            new JsonTask().execute(a[n]);




    }

    public boolean checkConnection(){
        ConnectivityManager cm=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if(networkInfo == null){
            return false;
        }
        else{
            return true;
        }
    }


    private class JsonTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();


        }

        protected String doInBackground(String... params) {


            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();


                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                    Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)

                }

                return buffer.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            cardView.setVisibility(View.GONE);
            try {


                jsonObject=new JSONObject(result);
                news = jsonObject.getJSONArray("articles");
                newsItem =new NewsItem[news.length()];
                for (int i = 0; i < news.length(); i++) {
                    jsonObject1 = news.getJSONObject(i);
                    newsItem[i]=new NewsItem(jsonObject1.getString("title"),jsonObject1.getString("description"),jsonObject1.getString("urlToImage"),jsonObject1.getString("url"));
                }

                //txt.setText(returnNews);

            } catch (Exception e) {
                e.printStackTrace();
            }

            ListView ls=(ListView)findViewById(R.id.listViewNewsItem);
             //adapter=new ArrayAdapter<String>(News.this,R.layout.support_simple_spinner_dropdown_item,arr);
            adapter=new NewsAdapter(getApplicationContext(),newsItem);
            ls.setAdapter(adapter);
            i=new Intent(getApplicationContext(),NewsDetails.class);

            ls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //String pos=String.valueOf(position);
//                    Toast.makeText(MainActivity.this,pos,Toast.LENGTH_LONG).show();
                    pos=position;

                    i.putExtra("url",newsItem[position].getUrl());
                    startActivity(i);

                    //Animate
                    // overridePendingTransition( R.anim.slide_up_animation, R.anim.slide_down_animation );
                }
            });




        }

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.menuSearch));
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        MenuItem item=menu.findItem(R.id.menuSearch);
        searchView = (SearchView)item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        LinearLayout searchBar = (LinearLayout) searchView.findViewById(R.id.search_bar);
        searchBar.setLayoutTransition(new LayoutTransition());
        return true;
    }
}

