package com.example.tarunkukreja.moviesdb;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

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

    private static final String LOG_TAG = MainActivity.class.getSimpleName() ;
    Uri popularUri = null ;

    ListView listView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "OnCreate called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView)findViewById(R.id.listView) ;
    }



    private class MoviesDB extends AsyncTask<String, String, List<MoviePage>>{



        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String moviesJsonStr = null ;

        @Override
        protected List<MoviePage> doInBackground(String... params) {



            try {
                String basePopularUrl = "http://api.themoviedb.org/3/movie/popular?" ;

                final String MOVIES_API_KEY = "api_key" ;

                popularUri = Uri.parse(basePopularUrl).buildUpon()
                        .appendQueryParameter(MOVIES_API_KEY, BuildConfig.MOVIESDB_API_KEY)
                        .build();
                URL popularUrl = new URL(popularUri.toString()) ;

                urlConnection = (HttpURLConnection)popularUrl.openConnection() ;
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
                final String OVERVIEW = "overview" ;
                final String TITLE = "original_title" ;
                final String LANGUAGE = "original_language" ;

                try{

                    JSONObject mainObj = new JSONObject(moviesJsonStr) ;
                    JSONArray moviesArray = mainObj.getJSONArray(RESULTS) ;

                    MoviePage moviePage = new MoviePage();

                    List<MoviePage> moviePageArrayList = new ArrayList<MoviePage>() ;

                    for(int i=0; i<moviesArray.length(); i++) {

                        JSONObject subObj = moviesArray.getJSONObject(i);
                        String overview = subObj.getString(OVERVIEW);
                        String lang = subObj.getString(LANGUAGE);
                        String title = subObj.getString(TITLE);

                        moviePage.setTitle(title);
                        moviePage.setLanguage(lang);
                        moviePage.setOverview(overview);

                        moviePageArrayList.add(i, moviePage);
                        Log.d(LOG_TAG, "Insertion" + i + "done");
                    }

                        return moviePageArrayList;

                }catch (JSONException e){
                    Log.e(LOG_TAG, "Error in JSON") ;
                    e.printStackTrace();
                }


            } catch (MalformedURLException e) {
                Log.e(LOG_TAG, "Error in Malformed") ;
                e.printStackTrace();
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error");
                e.printStackTrace();
            } finally {
                if(urlConnection != null){
                    urlConnection.disconnect();
                    Log.d(LOG_TAG, "url disconnected");

                    if(reader != null){
                        try {
                            reader.close();
                            Log.d(LOG_TAG, "reader closed");
                        } catch (IOException e) {
                            Log.e(LOG_TAG, "Some issues with Reader") ;
                            e.printStackTrace();
                        }
                    }
                }



            }


            return null;
        }

        @Override
        protected void onPostExecute(List<MoviePage> res) {
            Log.d(LOG_TAG, "onPostExecute called") ;
            super.onPostExecute(res);

            MovieAdapter movieAdapter = new MovieAdapter(getApplicationContext(), R.layout.row, res);
            listView.setAdapter(movieAdapter);

        }
    }

//    private List<MoviePage> getMoviesDataJson(String  moviesJsonStr)throws JSONException{
//
//        final String RESULTS = "results" ;
//        final String OVERVIEW = "overview" ;
//        final String TITLE = "original_title" ;
//        final String LANGUAGE = "original_language" ;
//        List<MoviePage> moviePageArrayList  = null;
//
//        try{
//
//            JSONObject mainObj = new JSONObject(moviesJsonStr) ;
//            JSONArray moviesArray = mainObj.getJSONArray(RESULTS) ;
//
//            MoviePage moviePage = new MoviePage();
//
//            moviePageArrayList = new ArrayList<MoviePage>() ;
//
//            for(int i=0; i<moviesArray.length(); i++){
//
//                JSONObject subObj = moviesArray.getJSONObject(i) ;
//                String overview = subObj.getString(OVERVIEW) ;
//                String lang = subObj.getString(LANGUAGE) ;
//                String title = subObj.getString(TITLE) ;
//
//               moviePage.setTitle(title);
//               moviePage.setLanguage(lang);
//               moviePage.setOverview(overview);
//
//              moviePageArrayList.add(moviePage);
//            }
//
//        }catch (JSONException e){
//            Log.e(LOG_TAG, "Error in JSON") ;
//               e.printStackTrace();
//        }
//        return moviePageArrayList ;
//
//    }



    public class MovieAdapter extends ArrayAdapter<MoviePage> {

        int resource ;
        List<MoviePage> moviePageList ;
        LayoutInflater inflater ;

        public MovieAdapter(Context context, int resource, List<MoviePage> objects) {
            super(context, resource, objects);
            this.resource = resource ;
            moviePageList = objects ;

            inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE) ;
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null ;

            if(convertView == null) {

                holder = new ViewHolder();
                convertView = inflater.inflate(resource, null);

                holder.title = (TextView) convertView.findViewById(R.id.movie_title);
                holder.language = (TextView) convertView.findViewById(R.id.lang);
                holder.overview = (TextView) convertView.findViewById(R.id.overview);

                convertView.setTag(holder);
            }
            else {
                holder = (ViewHolder)convertView.getTag() ;
            }

            holder.title.setText("Title: " + moviePageList.get(position).getTitle());
            holder.language.setText("Language: " + moviePageList.get(position).getLanguage());
            holder.overview.setText("Overview: " + moviePageList.get(position).getOverview());
            return convertView;
        }

        class ViewHolder{

            private TextView title ;
            private TextView overview ;
            private TextView language ;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true ;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_refresh){
            Log.d(LOG_TAG, "onRefresh") ;
            new MoviesDB().execute("http://api.themoviedb.org/3/movie/popular?api_key=aaffe5cad18de82872dc777a55d9ac51");
            return true ;
        }
        return super.onOptionsItemSelected(item);
    }
}