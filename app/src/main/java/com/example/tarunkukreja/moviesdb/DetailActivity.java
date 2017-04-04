package com.example.tarunkukreja.moviesdb;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

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

/**
 * Created by tarunkukreja on 13/03/17.
 */

public class DetailActivity extends AppCompatActivity {

    private static final String LOG_TAG = DetailActivity.class.getSimpleName() ;


     Typeface crimson_reg ;
     Typeface alegreya  ;
     Typeface asap ;
     Typeface arapey_italic ;
     Typeface abel ;
     Typeface roboto ;
     Typeface roboto_italic ;
     Typeface slabo ;
     Typeface abhya_libre ;
     Typeface alegreya_italic ;
     Typeface open_sans ;


//String hello = new String() ;

   // crimson_reg = Typeface.createFromAsset(getAssets(), "fonts/CrimsonText-Italic.ttf") ;

    private TextView title ;
    private TextView overview ;
    private TextView language ;
    private ImageView imageView ;
    private RatingBar ratingBar ;
    private TextView releaseDate ;
    private TextView adult ;
    private TextView dur ;
    private TextView trailer_text ;
    private TextView genre1 ;
    private TextView cast ;

//    private TextView genre2 ;
//    private TextView genre3 ;
    private Toolbar toolbar ;

    Uri videoUri  = null;
    Uri detailUri  = null;
    Uri castUri = null;

    ListView trailer_listView;
    long id ;
    String movId ;
    String VID_ID ;

    String runtime = null ;

   // String trailer_name ;

   // LinearLayout linearLayout ;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.ToolbarTheme);
        setContentView(R.layout.detail_view);
      //  setContentView(R.layout.activity_list);

        crimson_reg = Typeface.createFromAsset(getAssets(), "fonts/CrimsonText-Italic.ttf") ;
        alegreya = Typeface.createFromAsset(getAssets(), "fonts/Alegreya-Regular.ttf") ;
        roboto = Typeface.createFromAsset(getAssets(), "fonts/Roboto 500.ttf") ;
        asap = Typeface.createFromAsset(getAssets(), "fonts/Asap-Bold.ttf") ;
        abel = Typeface.createFromAsset(getAssets(), "fonts/Abel regular.ttf") ;
        arapey_italic = Typeface.createFromAsset(getAssets(), "fonts/Arapey italic.ttf") ;
        roboto_italic = Typeface.createFromAsset(getAssets(), "fonts/Roboto 700italic.ttf") ;
        slabo = Typeface.createFromAsset(getAssets(), "fonts/Slabo 27px regular.ttf") ;
        alegreya_italic = Typeface.createFromAsset(getAssets(), "fonts/Alegreya Sans italic.ttf") ;
        abhya_libre = Typeface.createFromAsset(getAssets(), "fonts/Abhaya Libre 700.ttf") ;
        open_sans = Typeface.createFromAsset(getAssets(), "fonts/OpenSans-Regular.ttf") ;
        Window window = this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.colorPrimary));

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.inflateMenu(R.menu.main_menu);

        title = (TextView) findViewById(R.id.detail_movie_title);
        language = (TextView) findViewById(R.id.detail_lang);
        overview = (TextView) findViewById(R.id.detail_overview);
        imageView = (ImageView) findViewById(R.id.image_view_detail);
        adult = (TextView)findViewById(R.id.detail_adult) ;
        ratingBar = (RatingBar)findViewById(R.id.detail_rating);
        releaseDate = (TextView)findViewById(R.id.detail_release) ;
        dur = (TextView)findViewById(R.id.detail_duration) ;
        genre1 = (TextView)findViewById(R.id.genre1) ;
        cast = (TextView)findViewById(R.id.castList) ;
        trailer_listView = (ListView)findViewById(R.id.trailer_list_view) ;
        //trailer_text = (TextView)findViewById(R.id.list_textView) ;

        //linearLayout = (LinearLayout) findViewById(R.id.trailerLinearLayout);
        Log.d(LOG_TAG, "Linear Layout pointed out") ;
//        genre2 = (TextView)findViewById(R.id.genre2) ;
//        genre3 = (TextView)findViewById(R.id.genre3) ;
       // trailer_listView = (ListView)findViewById(R.id.list_detail) ;

        Bundle bundle = getIntent().getExtras() ;
        String storyline = bundle.getString("Overview") ;
        String titledetail = bundle.getString("Title") ;
        String lang = bundle.getString("language") ;
        //String release = bundle.getString("release") ;
        boolean isAdult = bundle.getBoolean("isAdult") ;
        float rating = bundle.getFloat("rating") ;
        id = bundle.getLong("id") ;
        movId = String.valueOf(id) ;
        String image_url = bundle.getString("image_url") ;

        overview.setTypeface(roboto);
        language.setTypeface(alegreya);
        releaseDate.setTypeface(open_sans);
        cast.setTypeface(abhya_libre);
        adult.setTypeface(roboto);
        title.setTypeface(roboto);
        overview.setText(storyline);
        title.setText(titledetail) ;
        language.setText(lang);
        ratingBar.setRating(rating/2);
        //releaseDate.setText(release);

        if(isAdult){
            adult.setText("A");
        }
        else{
            adult.setText("U/A");
        }

        Picasso.with(this).load(image_url)
                .into(imageView);


        new MovieTrailRev(this).execute("https://api.themoviedb.org/3/movie/" + id + "/videos?api_key=" + BuildConfig.MOVIESDB_API_KEY);
        Log.d(LOG_TAG, "Video APi called") ;
        new MovDuration().execute("https://api.themoviedb.org/3/movie/" + id + "?api_key=" + BuildConfig.MOVIESDB_API_KEY) ;
        Log.d(LOG_TAG, "Detail APi called") ;
        new Cast().execute("https://api.themoviedb.org/3/movie/" + id + "/credits/?api_key=" + BuildConfig.MOVIESDB_API_KEY) ;
        Log.d(LOG_TAG, "Cast API called") ;
        new ReleaseDate().execute("https://api.themoviedb.org/3/movie/" + id + "/release_dates?api_key=" + BuildConfig.MOVIESDB_API_KEY) ;
        Log.d(LOG_TAG, "Release API called") ;
    }

    private class MovieTrailRev extends AsyncTask<String, String, List<TrailerCLass>> {

        private HttpURLConnection urlConnection = null;
        private BufferedReader reader = null;
        private String moviesJsonStr = null;
        Context context ;
        public MovieTrailRev(Context context){
         this.context = context ;
        }

        @Override
        protected List<TrailerCLass> doInBackground(String... params) {
            String baseUrl = "http://api.themoviedb.org/3/movie/";
            String video = "videos";
            final String MOVIES_API_KEY = "api_key";
            videoUri = Uri.parse(baseUrl).buildUpon()
                    .appendPath(movId)
                    .appendPath(video)
                    .appendQueryParameter(MOVIES_API_KEY, BuildConfig.MOVIESDB_API_KEY)
                    .build();


            try {
                URL vidUrl = new URL(videoUri.toString());
                urlConnection = (HttpURLConnection) vidUrl.openConnection();
                urlConnection.connect();
                Log.d(LOG_TAG, "Video URL connected");

                InputStream inputStream = urlConnection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(inputStream));

                StringBuffer stringBuffer = new StringBuffer();
                String line = " ";

                if ((line = reader.readLine()) != null) {

                    stringBuffer.append(line);
                }

                if (stringBuffer.length() == 0) {
                    return null;
                }

                moviesJsonStr = stringBuffer.toString();

                final String RESULTS = "results";
                final String NAME = "name";
               // final String VID_KEY = "key";
                VID_ID = "key";
                List<TrailerCLass> trailerList;
                TrailerCLass trailerCLass  ;
                try {
                    JSONObject mainObj = new JSONObject(moviesJsonStr);
                    JSONArray trailerArray = mainObj.getJSONArray(RESULTS);
                    trailerList = new ArrayList<>();

                    for (int i = 0; i < trailerArray.length(); i++) {
                        trailerCLass = new TrailerCLass() ;
                        JSONObject subObj = trailerArray.getJSONObject(i);
                        String trailerName = subObj.getString(NAME);
                        String vid_id = subObj.getString(VID_ID) ;

                        trailerCLass.setTrailer_name(trailerName);
                        trailerCLass.setTrailer_vid_key(vid_id);

                        trailerList.add(trailerCLass);
                        Log.d(LOG_TAG, "Trailer " + i + " added") ;
                    }
                    return trailerList;
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                    Log.d(LOG_TAG, "video url disconnected");

                    if (reader != null) {
                        try {
                            reader.close();
                            Log.d(LOG_TAG, " tariler reader closed");
                        } catch (IOException e) {
                            Log.e(LOG_TAG, "Some issues with Reader");
                            e.printStackTrace();
                        }
                    }
                }


            }


            return null;
        }

        @Override
        protected void onPostExecute(List<TrailerCLass> list) {
            Log.d(LOG_TAG, "onPostExecute called") ;
            super.onPostExecute(list);

            TrailerAdapter trailerAdapter = new TrailerAdapter(context, R.layout.activity_list, list) ;
            trailer_listView.setAdapter(trailerAdapter);
            Log.d(LOG_TAG, "Trailer Adapter Set") ;




//            LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
//         //   LinearLayout linearLayout = (LinearLayout) findViewById(R.id.trailerLinearLayout);
//
//            if(linearLayout == null){
//                Log.d(LOG_TAG, "LinewarLayout is null") ;
//                linearLayout = (LinearLayout)findViewById(R.id.trailerLinearLayout) ;
//            }
//            linearLayout.removeAllViews();
//
//            for (int i = 0; i < list.size(); i++) {
//
//
//                final View view = inflater.inflate(R.layout.activity_list, null);
//                view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
//                linearLayout.addView(view);
//                trailer_text = (TextView)findViewById(R.id.list_textView) ;
//                trailer_text.setText(null);
//                final String text = list.get(i) ;
//                trailer_text.setText(text);
//                Log.d(LOG_TAG, "Trailer" + i + "added");
//
//                view.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + VID_ID));
//                        Intent webIntent = new Intent(Intent.ACTION_VIEW,
//                                Uri.parse("http://www.youtube.com/watch?v=" + VID_ID));
//                        try {
//                            startActivity(appIntent);
//                        } catch (ActivityNotFoundException ex) {
//                            startActivity(webIntent);
//                        }
//                    }
//                });
//
//            }
            //linearLayout.removeAllViews();

//
//            ArrayAdapter<String> trailerAdapter = new ArrayAdapter<String>(DetailActivity.this, R.layout.activity_list, list) ;
//            trailer_listView.setAdapter(trailerAdapter);
//            trailer_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//
//                }
//            });

            //}
        }

    }

    private class MovDuration extends AsyncTask<String, String, List<String>> {

       // Context context ;
//        public MovDuration(Context context){
//            this.context = context ;
//        }

        private HttpURLConnection urlConnection = null;
        private BufferedReader reader = null;
        private String moviesJsonStr = null ;
        @Override
        protected List<String> doInBackground(String... params) {
            String baseUrl = "http://api.themoviedb.org/3/movie/";
            final String MOVIES_API_KEY = "api_key";

            detailUri = Uri.parse(baseUrl).buildUpon()
                    .appendPath(movId)
                    .appendQueryParameter(MOVIES_API_KEY, BuildConfig.MOVIESDB_API_KEY)
                    .build();

            try {

                URL detailUrl = new URL(detailUri.toString());
                urlConnection = (HttpURLConnection) detailUrl.openConnection();
                urlConnection.connect();
                Log.d(LOG_TAG, "URL connected");

                InputStream inputStream = urlConnection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(inputStream));

                StringBuffer stringBuffer = new StringBuffer();
                String line = " ";

                if ((line = reader.readLine()) != null) {

                    stringBuffer.append(line);
                }

                if (stringBuffer.length() == 0) {
                    return null;
                }

                moviesJsonStr = stringBuffer.toString();

                final String DUR = "runtime" ;
                final String GENRE = "genres" ;
                final String GENRE_NAME = "name" ;

                List<String> genreList  = new ArrayList<>();

                JSONObject mainObj = new JSONObject(moviesJsonStr) ;
                runtime = mainObj.getString(DUR) ;

                JSONArray genreArray  = mainObj.getJSONArray(GENRE) ;

                for(int i = 0; i<genreArray.length();i++){
                    JSONObject subObj = genreArray.getJSONObject(i) ;
                    String genre_name = subObj.getString(GENRE_NAME) ;
                    genreList.add(genre_name);
                }
                return genreList ;
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e)
            {
                e.printStackTrace();
            } finally

            {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                    Log.d(LOG_TAG, "url disconnected");

                    if (reader != null) {
                        try {
                            reader.close();
                            Log.d(LOG_TAG, "reader closed");
                        } catch (IOException e) {
                            Log.e(LOG_TAG, "Some issues with Reader");
                            e.printStackTrace();
                        }
                    }
                }


            }
            return null;

        }

        @Override
        protected void onPostExecute(List<String> res) {
            super.onPostExecute(res);

            //List<String> genreList ;

            // genre = null;
           // dur.setText(s);
            StringBuilder genres = new StringBuilder();
           for(int i = 0 ; i< res.size() ; i++){
               //genreList = new ArrayList<>() ;
               String genre = res.get(i) ;
                genres.append(genre + ((i+1) == res.size()?"":", "));
               //genreStrBuffer.append(genre) ;

           }
           genre1.setTypeface(alegreya_italic);
           //genreStrBuffer.toString().split(", ") ;
         // String[] genreList = genreStrBuffer.toString().split(", ");

          //  String genreLis = genreStrBuffer.toString().replace(" ", ", ") ;
//            for(String genres :genreList){
//                genres.concat()
//            }

           genre1.setText(genres.toString());
           dur.setTypeface(roboto_italic);
           dur.setText(runtime + " min");

        }
    }


    private class Cast extends AsyncTask<String, String,List<String>>{

        private HttpURLConnection urlConnection = null;
        private BufferedReader reader = null;
        private String moviesJsonStr = null ;



        @Override
        protected List<String> doInBackground(String... params) {

            String baseUrl = "http://api.themoviedb.org/3/movie/";
            final String MOVIES_API_KEY = "api_key";
            final String CREDITS = "credits" ;

            castUri = Uri.parse(baseUrl).buildUpon()
                    .appendPath(movId)
                    .appendPath(CREDITS)
                    .appendQueryParameter(MOVIES_API_KEY, BuildConfig.MOVIESDB_API_KEY)
                    .build();

            try {
                URL castUrl = new URL(castUri.toString());

                urlConnection = (HttpURLConnection) castUrl.openConnection();
                urlConnection.connect();
                Log.d(LOG_TAG, "URL connected");

                InputStream inputStream = urlConnection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(inputStream));

                StringBuffer stringBuffer = new StringBuffer();
                String line = " ";

                if ((line = reader.readLine()) != null) {

                    stringBuffer.append(line);
                }

                if (stringBuffer.length() == 0) {
                    return null;
                }

                moviesJsonStr = stringBuffer.toString();

                final String CAST = "cast";
                final String NAME = "name";
                JSONObject mainObj = new JSONObject(moviesJsonStr) ;
                JSONArray castArray = mainObj.getJSONArray(CAST) ;
                List<String> castList = new ArrayList<>() ;
                for(int i = 0 ; i<3 ; i++){
                    JSONObject castObj = castArray.getJSONObject(i) ;
                    String castName = castObj.getString(NAME) ;
                    castList.add(castName);
                }
                return castList ;
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e)
            {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(List<String> stringList) {
            super.onPostExecute(stringList);


            StringBuffer castList = new StringBuffer() ;


            // dur.setText(s);
            for(int i = 0 ; i< stringList.size() ; i++){
                String genre = stringList.get(i) ;
                castList.append(genre + (((i+1) == stringList.size())?"":", "))  ;

            }
            cast.setText(castList.toString());
        }


    }

    private class ReleaseDate extends AsyncTask<String, String, List<ReleaseClass>>{

        private HttpURLConnection urlConnection = null;
        private BufferedReader reader = null;
        private String moviesJsonStr = null ;





        @Override
        protected  List<ReleaseClass> doInBackground(String... params) {

            String baseUrl = "http://api.themoviedb.org/3/movie/";
            final String release = "release_dates" ;
            final String MOVIES_API_KEY = "api_key";

            Uri releaseUri = Uri.parse(baseUrl).buildUpon()
                         .appendPath(movId)
                         .appendPath(release)
                         .appendQueryParameter(MOVIES_API_KEY, BuildConfig.MOVIESDB_API_KEY)
                         .build();
            try {
                URL releaseUrl = new URL(releaseUri.toString());

                urlConnection = (HttpURLConnection) releaseUrl.openConnection();
                urlConnection.connect();
                Log.d(LOG_TAG, "URL connected");

                InputStream inputStream = urlConnection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(inputStream));

                StringBuffer stringBuffer = new StringBuffer();
                String line = " ";

                if ((line = reader.readLine()) != null) {

                    stringBuffer.append(line);
                }

                if (stringBuffer.length() == 0) {
                    return null;
                }

                moviesJsonStr = stringBuffer.toString();

                final String RESULTS = "results";
                final String REL_DATES = "release_dates" ;
                final String RELEASE = "release_date" ;
                final String COUNTRY = "iso_3166_1" ;

                JSONObject mainObj = new JSONObject(moviesJsonStr);
                JSONArray results = mainObj.getJSONArray(RESULTS) ;

                List<ReleaseClass> requiredArray = new ArrayList<>() ;

                ReleaseClass releaseClass ;

                for(int i = 0 ; i<results.length() ; i++){

                    releaseClass = new ReleaseClass() ;
                    JSONObject subObj = results.getJSONObject(i);
                    String nation = subObj.getString(COUNTRY) ;
                   // requiredArray.add(nation);
                    releaseClass.setNation(nation);
                    JSONArray relArray = subObj.getJSONArray(REL_DATES) ;

                        JSONObject relObj = relArray.getJSONObject(0) ;
                        String releaseReq = relObj.getString(RELEASE) ;
                        releaseClass.setReleaseDate(releaseReq);
                        requiredArray.add(releaseClass);


                }return requiredArray ;

            }catch (JSONException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
            return null ;
        }

        @Override
        protected void onPostExecute(List<ReleaseClass> result) {
            //ReleaseClass releaseClass  = new ReleaseClass();
            super.onPostExecute(result);
            String reqRelease = null ;
            for(int i = 0 ; i< result.size() ; i++){
                 String country = result.get(i).getNation() ;

                if(country.equals("IN")){
                    Log.d(LOG_TAG, "Indian Release") ;
//                    int index = result.indexOf(releaseClass);
//                    Log.d(LOG_TAG, "At Index " + index) ;
                    String longRelease = result.get(i).getReleaseDate() ;
                    reqRelease = longRelease.substring(0,10);
                    Log.d(LOG_TAG, "Date " + reqRelease) ;
                }
            }
            releaseDate.setText(reqRelease);





        }
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//
//        getMenuInflater().inflate(R.menu.menu_detail, menu);
//        return true ;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        if(item.getItemId() == R.id.detail_refresh){
//            Log.d(LOG_TAG, "onRefresh") ;
//           new MovieTrailRev(this).execute("https://api.themoviedb.org/3/movie/" + id + "/videos?api_key=" + BuildConfig.MOVIESDB_API_KEY);
//           Log.d(LOG_TAG, "Video APi called") ;
//           new MovDuration().execute("https://api.themoviedb.org/3/movie/" + id + "?api_key=" + BuildConfig.MOVIESDB_API_KEY) ;
//            Log.d(LOG_TAG, "Detail APi called") ;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }



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
