package com.example.hairongwu.mysearchapp_http;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.DocumentsContract;
import android.renderscript.Element;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {


    String url = "http://unofficialnetworks.com/2012/05/top-5-mountain-dogs";
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button titleButton = (Button) findViewById(R.id.titlebutton);


        titleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Title().execute();
            }
        });



    }


    private class Title extends AsyncTask<Void, Void, Void> {
        String title;
        String description1;
        String source1;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setTitle("Title");
            progressDialog.setMessage("Loading...");
            progressDialog.setIndeterminate(false);
            progressDialog.show();
        }


        @Override
        protected Void doInBackground(Void... params) {
            try {
                Document document = Jsoup.connect(url).get();

                title = document.title();
                Elements description = document.select("meta[name=keywords]");
                description1 = description.attr("content");
                org.jsoup.nodes.Element body = document.body();
                Elements keywords =document.body().getElementsByAttribute("meta[name=keywords]");

                Log.i("myTag ", "title: "+ title);
                Log.i("myTag ", "description: "+description1);
                Log.i("myTag ", "keywords: "+keywords.attr("content"));
                Log.i("myTag ", "body "+body);

                Log.i("myTag ", "document: "+ document);


            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            TextView txtTitle = (TextView) findViewById(R.id.titletxt);
            txtTitle.setText(title);
            progressDialog.dismiss();

        }

    }



}




