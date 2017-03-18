package com.example.tarunkukreja.moviesdb;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * Created by tarunkukreja on 13/03/17.
 */

public class DetailActivity extends AppCompatActivity {

    private TextView title ;
    private TextView overview ;
    private TextView language ;
    private ImageView imageView ;
    private RatingBar ratingBar ;
    private TextView releaseDate ;
    private TextView adult ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_view);


        title = (TextView) findViewById(R.id.detail_movie_title);
        language = (TextView) findViewById(R.id.detail_lang);
        overview = (TextView) findViewById(R.id.detail_overview);
        imageView = (ImageView) findViewById(R.id.image_view_detail);
        adult = (TextView)findViewById(R.id.detail_adult) ;
        ratingBar = (RatingBar)findViewById(R.id.detail_rating);
        releaseDate = (TextView)findViewById(R.id.detail_release) ;

        Bundle bundle = getIntent().getExtras() ;
        String storyline = bundle.getString("Overview") ;

        overview.setText(storyline);


    }

//    public class MovieDetailAdapter extends ArrayAdapter<MoviePage> {
//        int resource ;
//        List<MoviePage> moviePageList ;
//        LayoutInflater inflater ;
//
//        public MovieDetailAdapter(Context context, int resource, List<MoviePage> objects) {
//            super(context, resource, objects);
//            this.resource = resource ;
//            moviePageList = objects ;
//
//            inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE) ;
//        }
//
//        @NonNull
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//
//           ViewHolder holder = null ;
//
//            if(convertView == null) {
//
//                holder = new ViewHolder();
//                convertView = inflater.inflate(resource, null);
//
//                holder.title = (TextView) convertView.findViewById(R.id.detail_movie_title);
//                holder.language = (TextView) convertView.findViewById(R.id.detail_lang);
//                holder.overview = (TextView) convertView.findViewById(R.id.detail_overview);
//                holder.imageView = (ImageView) convertView.findViewById(R.id.image_view_detail);
//                holder.adult = (TextView)convertView.findViewById(R.id.detail_adult) ;
//                holder.ratingBar = (RatingBar)convertView.findViewById(R.id.detail_rating);
//                holder.releaseDate = (TextView)convertView.findViewById(R.id.detail_release) ;
//
//                convertView.setTag(holder);
//            }
//            else {
//                holder = (ViewHolder)convertView.getTag() ;
//            }
//
//            holder.title.setText("Title: " + moviePageList.get(position).getTitle());
//            holder.language.setText("Language: " + moviePageList.get(position).getLanguage());
//            holder.overview.setText("Overview: " + moviePageList.get(position).getOverview());
//            holder.releaseDate.setText("Release Date" + moviePageList.get(position).getRelease());
//            if(moviePageList.get(position).isAdult()){
//                holder.adult.setText("Adult: " + "A");
//            }
//            else{
//                holder.adult.setText("Adult: " + "U/A");
//            }
//
//            // holder.imageView.setText("Image url: " + moviePageList.get(position).getImage());
//            String url = moviePageList.get(position).getImage();
//
//            Picasso.with(getContext()).load(url)
//                    .into(holder.imageView);
//
//            float rating = moviePageList.get(position).getRating() ;
//            holder.ratingBar.setRating(rating/2);
//
//            return convertView;
//        }
//
//        class ViewHolder{
//
//            private TextView title ;
//            private TextView overview ;
//            private TextView language ;
//            private ImageView imageView ;
//            private RatingBar ratingBar ;
//            private TextView releaseDate ;
//            private TextView adult ;
//        }
//    }
}
