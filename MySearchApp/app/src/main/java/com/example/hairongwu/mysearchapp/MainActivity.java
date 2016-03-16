package com.example.hairongwu.mysearchapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private TextView Data;
    private ListView Movies;
    public List<DataModel> movieModelList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)

                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())

                .defaultDisplayImageOptions(defaultOptions)

                .build();
        ImageLoader.getInstance().init(config);


        Movies = (ListView)findViewById(R.id.listView);

    }





    public class JSONTask extends AsyncTask<String, String, List<DataModel>> {
        @Override
        protected List<DataModel> doInBackground(String... params){
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            try {
                //URL url = new URL("http://www.android.com/");
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();

                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                String finalJson = buffer.toString();
                JSONObject parentobject = new JSONObject(finalJson);
                JSONArray parentArray = parentobject.getJSONArray("movies");



                //List<DataModel> movieModelList = new ArrayList<>();

                for(int i =0; i< parentArray.length();++i) {
                    JSONObject finalObject = parentArray.getJSONObject(i);
                    DataModel movieModel= new DataModel();
                    movieModel.setMovie(finalObject.getString("movie"));
                    movieModel.setYear(finalObject.getInt("year"));
                    movieModel.setRating((float) finalObject.getDouble("rating"));
                    movieModel.setDirector(finalObject.getString("director"));
                    movieModel.setTagline(finalObject.getString("tagline"));
                    movieModel.setImage(finalObject.getString("image"));
                    movieModel.setStory(finalObject.getString("story"));
                    List<DataModel.Cast> castlist = new ArrayList<>();
                    for (int j=0; j<finalObject.getJSONArray("cast").length(); ++j){
                        JSONObject castObject = finalObject.getJSONArray("cast").getJSONObject(j);
                        DataModel.Cast cast= new DataModel.Cast();
                        cast.setName(castObject.getString("name"));
                        castlist.add(cast);

                    }

                    movieModel.setCastList(castlist);
                    movieModelList.add(movieModel);




                }
                return movieModelList;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch(JSONException e){
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
        protected void onPostExecute(List<DataModel> result){
            super.onPostExecute(result);

            MyAdapter adapter = new MyAdapter(getApplicationContext(), R.layout.item, result);
            Movies.setAdapter(adapter);

            Log.i("myTag", " result size"+ result.size());
            for(int i=0; i<result.size(); ++i){
                Log.i("myTag", " CastList size"+ result.get(i).getCastList().size());
                for(int j=0; j<result.get(i).getCastList().size(); ++j) {
                    Log.i("myTag", " CastList size" + result.get(i).getCastList().get(j).getName());
                }
            }


        }





        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == R.id.action_refresh){
            new JSONTask().execute("http://jsonparsing.parseapp.com/jsonData/moviesData.txt");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }





}
