package com.example.tarunkukreja.moviesdb;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by tarunkukreja on 13/03/17.
 */

public class DetailActivity extends AppCompatActivity {

    private static final String LOG_TAG = DetailActivity.class.getSimpleName() ;

    private TextView title ;
    private TextView overview ;
    private TextView language ;
    private ImageView imageView ;
    private RatingBar ratingBar ;
    private TextView releaseDate ;
    private TextView adult ;

    Uri videoUri  = null;

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
        String titledetail = bundle.getString("Title") ;
        String lang = bundle.getString("language") ;
        String release = bundle.getString("release") ;
        boolean isAdult = bundle.getBoolean("isAdult") ;
        float rating = bundle.getFloat("rating") ;

        overview.setText(storyline);
        title.setText(titledetail) ;
        language.setText(lang);
        ratingBar.setRating(rating/2);
        releaseDate.setText(release);

        if(isAdult){
            adult.setText("A");
        }
        else{
            adult.setText("U/A");
        }


    }

    private class MovieTrailRev extends AsyncTask<String, String, Void>{

        private HttpURLConnection urlConnection = null;
        private BufferedReader reader = null;
        private String moviesJsonStr = null ;

        @Override
        protected Void doInBackground(String... params) {
            String baseUrl = "http://api.themoviedb.org/3/movie/" ;
            String movId = "id" ;
            String video = "videos" ;
            final String MOVIES_API_KEY = "api_key" ;
            videoUri = Uri.parse(baseUrl).buildUpon()
                    .appendPath(movId)
                    .appendPath(video)
                    .appendQueryParameter(MOVIES_API_KEY, BuildConfig.MOVIESDB_API_KEY)
                    .build();

            try {
                URL vidUrl = new URL(videoUri.toString()) ;
                urlConnection = (HttpURLConnection)vidUrl.openConnection() ;
                urlConnection.connect();
                Log.d(LOG_TAG, "URL connected") ;

                InputStream inputStream = urlConnection.getInputStream() ;

                reader = new BufferedReader(new InputStreamReader(inputStream));

                StringBuffer stringBuffer = new StringBuffer() ;
                String line = " ";

                if((line = reader.readLine())!= null){

                    stringBuffer.append(line) ;
                }

                if(stringBuffer.length() == 0){
                    return null;
                }

                moviesJsonStr = stringBuffer.toString() ;

                final String RESULTS = "results" ;
                final String NAME = "name" ;
                final String VID_KEY = "key" ;
                final String VID_ID = "id" ;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
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
