package com.example.hairongwu.googlesearchapp;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.AsynchronousCloseException;


public class MainActivity extends Activity {

    String searchUrl = "http://ajax.googleapis.com/ajax/services/search/web?v=2.0&q=";
    //String searchQuery= "http://ajax.googleapis.com/ajax/services/search/web?v=1.0&q=";
    String searchItem = "cat";
    String searchQuery = searchUrl+searchItem;

    TextView searchResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchResult = (TextView) findViewById(R.id.result);

        new JsonSearchTask().execute();

    }


    private class JsonSearchTask extends AsyncTask<Void, Void, Void>{
        String searchResultString = "";

        @Override
        protected Void doInBackground (Void... params ){
            try{
                searchResultString = ParseStringResult(sendQuery(searchQuery));
            }catch(JSONException e){
                e.printStackTrace();
            }catch(IOException e){
                e.printStackTrace();
            }
            return null;

        }

        @Override
        protected void onPostExecute(Void aVoid){
            searchResult.setText(searchResultString);
            super.onPostExecute(aVoid);
        }


    }

    private String sendQuery(String query)throws IOException{
        String result = "";
        URL sUrl = new URL(query);

        HttpURLConnection httpURLConnection = (HttpURLConnection) sUrl.openConnection();
        if(httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
            InputStreamReader inputStream = new InputStreamReader(httpURLConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStream, 8192);
            String line = null;
            while((line = bufferedReader.readLine()) != null){
                result += line;
            }

            bufferedReader.close();

        }

    return result;

    }

    private String ParseStringResult (String json) throws JSONException{
        String parsedResult = "";
        JSONObject jsonObject = new JSONObject(json);
        JSONObject jsonObject_responseData = jsonObject.getJSONObject("responseData");
        JSONArray jsonArray_result = jsonObject_responseData.getJSONArray("results");

        parsedResult += "Google Search for: "+ searchItem + "\n";
        parsedResult += "Number of results returned = " +jsonArray_result.length()+"\n\n";

        for(int i=0; i<jsonArray_result.length(); ++i){
            JSONObject jsonObject_i = jsonArray_result.getJSONObject(i);
            parsedResult += "title: " +jsonObject_i.getString("title")+"\n";
            parsedResult += "content: " +jsonObject_i.getString("content")+"\n";
            parsedResult += "url: " +jsonObject_i.getString("url")+"\n\n";
        }
        return parsedResult;
    }



}
