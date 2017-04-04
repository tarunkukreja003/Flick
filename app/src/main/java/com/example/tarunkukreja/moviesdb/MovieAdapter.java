package com.example.tarunkukreja.moviesdb;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by tarunkukreja on 16/03/17.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder> implements Filterable{

    private static final String LOG_TAG = MovieAdapter.class.getSimpleName() ;

  //  int resource ;
    List<MoviePage> moviePageList ;
   // LayoutInflater inflater ;
    //CardView cardView ;
    Context context ;
    Typeface roboto ;

    public MovieAdapter(List<MoviePage> objects) {
       // super(objects);
     //   this.resource = resource ;
        moviePageList = objects ;

       // inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE) ;
    }

//    @NonNull
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//
//        ViewHolder holder = null ;
//
//        if(convertView == null) {
//
//            Log.d(LOG_TAG, "ConvertView is null") ;
//
//            holder = new ViewHolder();
//            convertView = inflater.inflate(resource, null);
//
//            holder.title = (TextView) convertView.findViewById(R.id.movie_title);
//         //   holder.language = (TextView) convertView.findViewById(R.id.lang);
//           // holder.overview = (TextView) convertView.findViewById(R.id.overview);
//            holder.imageView = (ImageView) convertView.findViewById(R.id.image_view);
//    //        holder.adult = (TextView)convertView.findViewById(R.id.adult) ;
//  //          holder.ratingBar = (RatingBar)convertView.findViewById(R.id.rating);
////            holder.id = (TextView)convertView.findViewById(R.id.mov_id) ;
//            cardView = (CardView)convertView.findViewById(R.id.card_view) ;
//            // holder.releaseDate = (TextView)convertView.findViewById(R.id.release) ;
//
//            convertView.setTag(holder);
//        }
//        else {
//            Log.d(LOG_TAG, "ConvertView is not NULL") ;
//            holder = (ViewHolder)convertView.getTag() ;
//        }
//
//        holder.title.setText(moviePageList.get(position).getTitle());
////        holder.language.setText("Language: " + moviePageList.get(position).getLanguage());
////        holder.overview.setText("Overview: " + moviePageList.get(position).getOverview());
////        holder.releaseDate.setText("Release Date" + moviePageList.get(position).getRelease());
////        if(moviePageList.get(position).isAdult()){
////            holder.adult.setText("A");
////        }
////        else{
////            holder.adult.setText("U/A");
////        }
//
//        // holder.imageView.setText("Image url: " + moviePageList.get(position).getImage());
//        String url = moviePageList.get(position).getImage();
//
//        Picasso.with(getContext()).load(url)
//                .into(holder.imageView);
////
////        float rating = moviePageList.get(position).getRating() ;
////        holder.ratingBar.setRating(rating/2);
////        long movie_id = moviePageList.get(position).getId() ;
////        holder.id.setText(String.valueOf(movie_id));
//
//
//       convertView.setOnClickListener(new View.OnClickListener() {
//           @Override
//           public void onClick(View v) {
//
//           }
//       });
//
//        return convertView;
//    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false) ;
        context = view.getContext() ;
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {


        holder.title.setText(moviePageList.get(position).getTitle());
        String url = moviePageList.get(position).getImage();

        Picasso.with(context).load(url)
                .into(holder.imageView);


       final MoviePage pos1 = moviePageList.get(position) ;
        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String storyline = pos1.getOverview();
                String language = pos1.getLanguage() ;
                float pos1Rating = pos1.getRating() ;
                String pos1Title = pos1.getTitle() ;
                boolean pos1Adult = pos1.isAdult() ;
                String pos1Release = pos1.getRelease() ;
                long pos1id = pos1.getId() ;
                String pos1url = pos1.getImage() ;
                Bundle args = new Bundle();
                args.putString("Overview", storyline);
                args.putString("Title", pos1Title);
                args.putString("release", pos1Release);
                args.putBoolean("isAdult", pos1Adult);
                args.putFloat("rating", pos1Rating);
                args.putString("language", language);
                args.putLong("id", pos1id);
                args.putString("image_url", pos1url);
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtras(args);
                context.startActivity(intent);
            }
        });

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String storyline = pos1.getOverview();
                String language = pos1.getLanguage() ;
                float pos1Rating = pos1.getRating() ;
                String pos1Title = pos1.getTitle() ;
                boolean pos1Adult = pos1.isAdult() ;
                String pos1Release = pos1.getRelease() ;
                long pos1id = pos1.getId() ;
                String pos1url = pos1.getImage() ;
                Bundle args = new Bundle();
                args.putString("Overview", storyline);
                args.putString("Title", pos1Title);
                args.putString("release", pos1Release);
                args.putBoolean("isAdult", pos1Adult);
                args.putFloat("rating", pos1Rating);
                args.putString("language", language);
                args.putLong("id", pos1id);
                args.putString("image_url", pos1url);
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtras(args);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return (null != moviePageList ? moviePageList.size() : 0);
    }

    @Override
    public Filter getFilter() {
        return null;
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title ;
        //  private TextView overview ;
        //  private TextView language ;
        ImageView imageView ;
        View rootView ;
        //  private RatingBar ratingBar ;
        // private TextView releaseDate ;
        //  private TextView adult ;
        //  private TextView id ;

        public MyViewHolder(View view){
            super(view);
//            view.setOnClickListener(this);
           rootView = view ;
           title = (TextView) view.findViewById(R.id.movie_title);
            //   holder.language = (TextView) convertView.findViewById(R.id.lang);
            // holder.overview = (TextView) convertView.findViewById(R.id.overview);
            imageView = (ImageView) view.findViewById(R.id.image_view);
            roboto = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto 500.ttf") ;
            //        holder.adult = (TextView)convertView.findViewById(R.id.adult) ;
            //          holder.ratingBar = (RatingBar)convertView.findViewById(R.id.rating);
//            holder.id = (TextView)convertView.findViewById(R.id.mov_id) ;
           // cardView = (CardView)view.findViewById(R.id.card_view) ;

        }


//        @Override
//        public void onClick(View v) {
//            String storyline = pos1.getOverview();
//            String language = pos1.getLanguage() ;
//            float pos1Rating = pos1.getRating() ;
//            String pos1Title = pos1.getTitle() ;
//            boolean pos1Adult = pos1.isAdult() ;
//            String pos1Release = pos1.getRelease() ;
//            long pos1id = pos1.getId() ;
//            String pos1url = pos1.getImage() ;
//            Bundle args = new Bundle();
//            args.putString("Overview", storyline);
//            args.putString("Title", pos1Title);
//            args.putString("release", pos1Release);
//            args.putBoolean("isAdult", pos1Adult);
//            args.putFloat("rating", pos1Rating);
//            args.putString("language", language);
//            args.putLong("id", pos1id);
//            args.putString("image_url", pos1url);
//            Intent intent = new Intent(context, DetailActivity.class);
//            intent.putExtras(args);
//            context.startActivity(intent);
//        }
    }
}
//
//class ViewListener extends RecyclerView.ViewHolder implements View.OnClickListener{
//
//    public ViewListener(View itemView) {
//        super(itemView);
//    }
//
//    @Override
//    public void onClick(View v) {
//
//    }
//}


