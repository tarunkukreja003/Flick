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
import android.widget.SearchView;

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
 * Created by tarunkukreja on 19/03/17.
 */

public class FragmentPopular extends Fragment {

    private static final String LOG_TAG = FragmentPopular.class.getSimpleName() ;
    Uri popularUri = null ;

   // GridView gridView ;

    RecyclerView recyclerView ;
    RecyclerView.LayoutManager grid ;

    SwipeRefreshLayout swipeRefreshLayout ;
    private ProgressDialog progressDialog ;
    private SearchView searchView;
    private MenuItem searchMenuItem;
    private MovieAdapter movieAdapter ;
   // WindowManager windowManager ;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

       View view = inflater.inflate(R.layout.fragment_popular, container, false) ;

       swipeRefreshLayout =(SwipeRefreshLayout)view.findViewById(R.id.swipe_refresh) ;
      // gridView = (GridView)view.findViewById(R.id.gridView_pop);
        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_pop);

//        window = getActivity().getWindow() ;
      //  int height = windowManager.getDefaultDisplay().getHeight() ;
       // int height =  window.getAttributes().y ;

        grid = new GridLayoutManager(getActivity(), 2);
      //  view.setLayoutParams(new GridLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int)(height/2.5)));

        new MoviesPop().execute("http://api.themoviedb.org/3/movie/popular?api_key=" + BuildConfig.MOVIESDB_API_KEY) ;

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                new MoviesPop().execute("http://api.themoviedb.org/3/movie/popular?api_key=" + BuildConfig.MOVIESDB_API_KEY) ;
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        return view ;


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       // gridView = (GridView)getActivity().findViewById(R.id.gridView_pop) ;

        setHasOptionsMenu(true);

    }

//    @Override
//    public boolean onQueryTextSubmit(String query) {
//        return false;
//    }
//
//    @Override
//    public boolean onQueryTextChange(String newText) {
//
//        //movieAdapter_search.getFilter().filter(newText);
//        return true;
//    }

    private class MoviesPop extends AsyncTask<String, String, List<MoviePage>>{

        private HttpURLConnection urlConnection = null;
        private BufferedReader reader = null;
        private String moviesJsonStr = null ;



        @Override
        protected List<MoviePage> doInBackground(String... params) {



            try {
                String baseUrl = "http://api.themoviedb.org/3/movie/popular?" ;
              //  final String popular_sort = "popular" ;
                //final String top_rated_sort = "top_rated" ;

                final String MOVIES_API_KEY = "api_key" ;

                popularUri = Uri.parse(baseUrl).buildUpon()
                        .appendQueryParameter(MOVIES_API_KEY, BuildConfig.MOVIESDB_API_KEY)
                        .build();
                URL popularUrl = new URL(popularUri.toString()) ;
                urlConnection = (HttpURLConnection) popularUrl.openConnection();
                urlConnection.connect();
                Log.d(LOG_TAG, "URL connected") ;
//                topRatedUri = Uri.parse(baseUrl).buildUpon()
//                        .appendPath(top_rated_sort)
//                        .appendQueryParameter(MOVIES_API_KEY, BuildConfig.MOVIESDB_API_KEY)
//                        .build();

             //   URL topUrl = new URL(topRatedUri.toString()) ;

//                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
//                if(sharedPreferences.getString(getString(R.string.movies_pref_key), getString(R.string.popular_sort))
//                        .equals(getString(R.string.popular_label))) {

               // }

//                else if(sharedPreferences.getString(getString(R.string.movies_pref_key), getString(R.string.top_rated_sort))
//                        .equals(getString(R.string.top_rated_label))){
//                    urlConnection = (HttpURLConnection)topUrl.openConnection() ;
//                }




                InputStream inputStream = urlConnection.getInputStream() ;

                reader = new BufferedReader(new InputStreamReader(inputStream));

                StringBuffer stringBuffer = new StringBuffer() ;
                String line = " ";

                if((line = reader.readLine())!= null){

                    stringBuffer.append(line) ;
                }

                if(stringBuffer.length() == 0){
                    Log.d(LOG_TAG, "String Buffer is null");
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
                List<MoviePage> moviePageArrayList ;

                try{

                    JSONObject mainObj = new JSONObject(moviesJsonStr) ;
                    JSONArray moviesArray = mainObj.getJSONArray(RESULTS) ;

                    MoviePage moviePage ;

                    moviePageArrayList = new ArrayList<MoviePage>() ;

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
                        long movId = subObj.getLong(MOV_ID) ;


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
             recyclerView.setAdapter(movieAdapter);
//            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
//
//                    Log.d(LOG_TAG, "Item Clicked") ;
//
//                    //  MoviePage pos = res.get(position) ;
//                    MoviePage pos1 = res.get(position);
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

//            gridView.setOnScrollListener(new AbsListView.OnScrollListener() {
//                @Override
//                public void onScrollStateChanged(AbsListView view, int scrollState) {
//
//                }
//
//                @Override
//                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//                 if(firstVisibleItem == 0){
//                     swipeRefreshLayout.setEnabled(true);
//                 }else{
//                     swipeRefreshLayout.setEnabled(false);
//                 }
//                }
//            });



        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_menu, menu);

//        SearchManager searchManager = (SearchManager)getActivity().getSystemService(Context.SEARCH_SERVICE) ;
//       // searchView = (SearchView)getActivity().findViewById(R.id.search_view) ;
//        searchMenuItem = menu.findItem(R.id.search_view) ;
//        searchView = (SearchView)searchMenuItem.getActionView() ;
//
//        searchView.setSearchableInfo(searchManager.
//                getSearchableInfo(getActivity().getComponentName()));
//        searchView.setSubmitButtonEnabled(true);
//        searchView.setOnQueryTextListener(this);

        //return true ;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.action_refresh) {
            Log.d(LOG_TAG, "onRefresh");

            new MoviesPop().execute("http://api.themoviedb.org/3/movie/popular?api_key=" + BuildConfig.MOVIESDB_API_KEY);
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
