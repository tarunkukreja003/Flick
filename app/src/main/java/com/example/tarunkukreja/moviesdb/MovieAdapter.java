package com.example.tarunkukreja.moviesdb;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * Created by tarunkukreja on 16/03/17.
 */

public class MovieAdapter extends ArrayAdapter<MoviePage> {

    private static final String LOG_TAG = MovieAdapter.class.getSimpleName() ;

    int resource ;
    List<MoviePage> moviePageList ;
    LayoutInflater inflater ;
    CardView cardView ;

    public MovieAdapter(Context context, int resource, List<MoviePage> objects) {
        super(context, resource, objects);
        this.resource = resource ;
        moviePageList = objects ;

        inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE) ;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null ;

        if(convertView == null) {

            Log.d(LOG_TAG, "ConvertView is null") ;

            holder = new ViewHolder();
            convertView = inflater.inflate(resource, null);

            holder.title = (TextView) convertView.findViewById(R.id.movie_title);
         //   holder.language = (TextView) convertView.findViewById(R.id.lang);
           // holder.overview = (TextView) convertView.findViewById(R.id.overview);
            holder.imageView = (ImageView) convertView.findViewById(R.id.image_view);
            holder.adult = (TextView)convertView.findViewById(R.id.adult) ;
            holder.ratingBar = (RatingBar)convertView.findViewById(R.id.rating);
            cardView = (CardView)convertView.findViewById(R.id.card_view) ;
           // holder.releaseDate = (TextView)convertView.findViewById(R.id.release) ;

            convertView.setTag(holder);
        }
        else {
            Log.d(LOG_TAG, "ConvertView is not NULL") ;
            holder = (ViewHolder)convertView.getTag() ;
        }

        holder.title.setText(moviePageList.get(position).getTitle());
//        holder.language.setText("Language: " + moviePageList.get(position).getLanguage());
//        holder.overview.setText("Overview: " + moviePageList.get(position).getOverview());
//        holder.releaseDate.setText("Release Date" + moviePageList.get(position).getRelease());
        if(moviePageList.get(position).isAdult()){
            holder.adult.setText("A");
        }
        else{
            holder.adult.setText("U/A");
        }

        // holder.imageView.setText("Image url: " + moviePageList.get(position).getImage());
        String url = moviePageList.get(position).getImage();

        Picasso.with(getContext()).load(url)
                .into(holder.imageView);

        float rating = moviePageList.get(position).getRating() ;
        holder.ratingBar.setRating(rating/2);


        final MoviePage pos1 = moviePageList.get(position) ;

       convertView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String storyline = pos1.getOverview();
               String language = pos1.getLanguage() ;
               float pos1Rating = pos1.getRating() ;
               String pos1Title = pos1.getTitle() ;
               boolean pos1Adult = pos1.isAdult() ;
               String pos1Release = pos1.getRelease() ;
               Bundle args = new Bundle();
               args.putString("Overview", storyline);
               args.putString("Title", pos1Title);
               args.putString("release", pos1Release);
               args.putBoolean("isAdult", pos1Adult);
               args.putFloat("rating", pos1Rating);
               args.putString("language", language);

               Intent intent = new Intent(getContext(), DetailActivity.class);
               intent.putExtras(args);
               getContext().startActivity(intent);
           }
       });

        return convertView;
    }


    class ViewHolder{

        private TextView title ;
      //  private TextView overview ;
      //  private TextView language ;
        private ImageView imageView ;
        private RatingBar ratingBar ;
       // private TextView releaseDate ;
        private TextView adult ;
    }
}


