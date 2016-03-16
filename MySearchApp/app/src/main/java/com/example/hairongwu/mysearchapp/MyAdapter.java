package com.example.hairongwu.mysearchapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by hairongwu on 3/11/16.
 */
public class MyAdapter extends ArrayAdapter {

    private List<DataModel> movieModelList;
    private int resouce;
    private LayoutInflater inflater;

    public MyAdapter(Context context, int resource, List<DataModel> objects) {
        super(context, resource, objects);
        movieModelList = objects;
        this.resouce = resource;
        //inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        if(convertView ==null){
            convertView = inflater.inflate(resouce, null);
        }
        ImageView ivMovieIcon;
        TextView tvMovie;
        TextView  tvTagline;
        TextView  tydd;

        TextView  tvDirector;
        TextView  tvRating;
        TextView   tvActor;

        TextView  tvStory;


        ivMovieIcon =(ImageView)convertView.findViewById(R.id.imageView);
        tvMovie     =(TextView)convertView.findViewById(R.id.movieName);
        tvTagline  =(TextView)convertView.findViewById(R.id.tydd);
        tydd       = (TextView)convertView.findViewById(R.id.tydd);

        tvDirector     =(TextView)convertView.findViewById(R.id.director);
        tvRating       = (TextView)convertView.findViewById(R.id.rating);


        tvActor   =(TextView)convertView.findViewById(R.id.cast1);
        tvStory     =(TextView)convertView.findViewById(R.id.story);

        ImageLoader.getInstance().displayImage(movieModelList.get(position).getImage(), ivMovieIcon);

        tvMovie.setText(movieModelList.get(position).getMovie());
        tvTagline.setText(movieModelList.get(position).getTagline());

        tydd.setText("Year: "+movieModelList.get(position).getYear());
        tvRating.setText("Rating: "+ movieModelList.get(position).getRating());


        tvDirector.setText("Director: "+ movieModelList.get(position).getDirector());


        StringBuffer stringBuffer = new StringBuffer();
        for(DataModel.Cast cast : movieModelList.get(position).getCastList()){
            stringBuffer.append(cast.getName()+", ");
        }
        tvActor.setText("Actor: "+ stringBuffer);

        Log.i("myTag"," Cast "+ stringBuffer);
        tvStory.setText("Story: "+ movieModelList.get(position).getStory());
        Log.i("myTag", " story " + movieModelList.get(position).getStory());

        return convertView;
    }
}
