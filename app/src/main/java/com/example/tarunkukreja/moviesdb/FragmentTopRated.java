package com.example.tarunkukreja.moviesdb;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

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



public class FragmentTopRated extends Fragment {

    private static final String LOG_TAG = FragmentTopRated.class.getSimpleName() ;
   // Uri popularUri = null ;
    private Uri nowPlayingUri = null ;


  // GridView  gridViewNowPlaying ;
    private SwipeRefreshLayout swipeRefreshLayout ;
    private RecyclerView recyclerView ;
    private RecyclerView.LayoutManager grid ;
    private ProgressDialog progressDialog ;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top_rated, container, false) ;
      //  gridViewNowPlaying = (GridView)view.findViewById(R.id.gridView_top) ;
        swipeRefreshLayout =(SwipeRefreshLayout)view.findViewById(R.id.swipe_refresh_top) ;
        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_top) ;
        grid = new GridLayoutManager(getActivity(), 2);
        new MoviesTopRated().execute("http://api.themoviedb.org/3/movie/now_playing?api_key=" + BuildConfig.MOVIESDB_API_KEY);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                new MoviesTopRated().execute("http://api.themoviedb.org/3/movie/now_playing?api_key=" + BuildConfig.MOVIESDB_API_KEY) ;
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        return view ;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // gridViewTop = (GridView)getActivity().findViewById(R.id.gridView_top) ;

        setHasOptionsMenu(true);
    }

    private class MoviesTopRated extends AsyncTask<String, String, List<MoviePage>>{

        private HttpURLConnection urlConnection = null;
        private BufferedReader reader = null;
        private String moviesJsonStr = null ;



        @Override
        protected List<MoviePage> doInBackground(String... params) {



            try {
                String baseUrl = "http://api.themoviedb.org/3/movie/now_playing" ;
              //  final String popular_sort = "popular" ;
               // final String top_rated_sort = "top_rated" ;

                final String MOVIES_API_KEY = "api_key" ;

                //popularUri = Uri.parse(baseUrl).buildUpon()
//                        .appendPath(popular_sort)
//                        .appendQueryParameter(MOVIES_API_KEY, BuildConfig.MOVIESDB_API_KEY)
//                        .build();
                nowPlayingUri = Uri.parse(baseUrl).buildUpon()
                        .appendQueryParameter(MOVIES_API_KEY, BuildConfig.MOVIESDB_API_KEY)
                        .build();

                URL topUrl = new URL(nowPlayingUri.toString()) ;
                urlConnection = (HttpURLConnection)topUrl.openConnection() ;
                urlConnection.connect();
                Log.d(LOG_TAG, "URL connected") ;

                // URL popularUrl = new URL(popularUri.toString()) ;

//                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//                if(sharedPreferences.getString(getString(R.string.movies_pref_key), getString(R.string.popular_sort))
//                        .equals(getString(R.string.popular_label))) {
                  //  urlConnection = (HttpURLConnection) popularUrl.openConnection();
                //}

//                else if(sharedPreferences.getString(getString(R.string.movies_pref_key), getString(R.string.top_rated_sort))
//                        .equals(getString(R.string.top_rated_label))){

              //  }


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
                final String TITLE = "title" ;
                final String LANGUAGE = "original_language" ;
                final String IMAGE = "poster_path" ;
                final String ADULT = "adult" ;
                final String RELEASE = "release_date" ;
                final String VOTE = "vote_average" ;
                final String MOV_ID = "id" ;
                List<MoviePage> moviePageArrayListPlaying ;

                try{

                    JSONObject mainObj = new JSONObject(moviesJsonStr) ;
                    JSONArray moviesArray = mainObj.getJSONArray(RESULTS) ;

                    MoviePage moviePage ;

                    moviePageArrayListPlaying = new ArrayList<MoviePage>() ;

                    for(int i=0; i<moviesArray.length(); i++) {

                        StringBuffer moviePosterUrl = null;
                        moviePosterUrl = new StringBuffer() ;
                        moviePosterUrl.append("https://image.tmdb.org/t/p/w500/");
                        moviePage = new MoviePage();

                        JSONObject subObj = moviesArray.getJSONObject(i);
                        String overview = subObj.getString(OVERVIEW);
                        String lang = subObj.getString(LANGUAGE);
                        String  title = subObj.getString(TITLE);
                        String image = subObj.getString(IMAGE) ;
                        String release_date = subObj.getString(RELEASE) ;
                        boolean adult_or_not = subObj.getBoolean(ADULT) ;
                        float vote = subObj.getInt(VOTE) ;
                        long movId = subObj.getLong(MOV_ID);

                        moviePosterUrl.append(image);
                        String posterUrl = moviePosterUrl.toString();


                        moviePage.setTitle(title);
                        moviePage.setLanguage(lang);
                        moviePage.setOverview(overview);
                        moviePage.setImage(posterUrl);
                        moviePage.setAdult(adult_or_not);
                        moviePage.setRelease(release_date);
                        moviePage.setRating(vote);
                        moviePage.setId(movId);

                        moviePageArrayListPlaying.add(i, moviePage);
                        Log.d(LOG_TAG, "Insertion" + i + "done");
                    }

                    return moviePageArrayListPlaying;

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
        protected void onPreExecute() {

            progressDialog = new ProgressDialog(getActivity(), R.style.ProgressDialogTheme) ;
            progressDialog.create();
            progressDialog.setMessage("Loading");
            progressDialog.show();
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(final List<MoviePage> res) {
            Log.d(LOG_TAG, "onPostExecute called") ;
            progressDialog.dismiss();
            super.onPostExecute(res);

            MovieAdapter movieAdapter = new MovieAdapter(res);
            recyclerView.setLayoutManager(grid);
            Log.d(LOG_TAG, "Now_Playing Layout Manager set") ;
            recyclerView.setAdapter(movieAdapter);
            Log.d(LOG_TAG, "Now_Playing adapter set") ;
//            gridViewNowPlaying.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
//
//                    Log.d(LOG_TAG, "Item Clicked") ;
//
//                    //  MoviePage pos = res.get(position) ;
//                    MoviePage pos1 = (MoviePage)parent.getItemAtPosition(position) ;
//                    String storyline = pos1.getOverview() ;
//                    Bundle args = new Bundle() ;
//                    args.putString("Overview", storyline);
//
//                    Intent intent = new Intent(getActivity(), DetailActivity.class) ;
//                    intent.putExtras(args) ;
//                    startActivity(intent);
//
//                }
//            });



        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.action_refresh) {
            Log.d(LOG_TAG, "onRefresh");


            new MoviesTopRated().execute("http://api.themoviedb.org/3/movie/now_playing?api_key=" + BuildConfig.MOVIESDB_API_KEY);

            return true;
        }

//        else if(item.getItemId() == R.id.action_settings){
//
//            Intent intent = new Intent(getActivity(), SettingsActivity.class) ;
//            startActivity(intent);
//            return true ;
//        }
        return super.onOptionsItemSelected(item);
    }
}
