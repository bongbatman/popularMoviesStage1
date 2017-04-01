package com.example.android.nishantspopularmovies;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmovies.DetailsActivity;

import java.net.URL;
import java.util.ArrayList;

import data.MovieDbAdapter;
import utils.JsonUtils;
import utils.NetworkUtils;

public class MainActivity extends AppCompatActivity implements MovieDbAdapter.ListItemClickListener {

    final static String SORT_POPULAR = "popular";
    final static String SORT_TOP_RATED = "top_rated";
    private static final String LOG_TAG = MainActivity.class.getSimpleName() ;
    public static final String CLICKED_ITEM_INDEX = "clicked_item_index" ;
    public static final String MOVIE_POSTER_PATH = "movie_poster_path" ;
    ArrayList<String> movieDbPosterList = null;

    private TextView mJsonTextView;

    private MovieDbAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private String[] mPosterPathList;
    static String[] mMovieOriginalTitleList;
    static String[] mMovieOverviewList;
    static String[] mMovieIdList;
    static String[] mUserRatingList;
    static String[] mReleaseDateList;
    private int listItemCount;

    TextView mErrorTextView;
    ProgressBar mLoadBar;
    URL builtUrlForPopular;
    URL builtUrlForTopRated;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mPosterPathList = new String[2];
        mPosterPathList[0] = "Dummy data item 1";
        mPosterPathList[1] = "Dummy data item 2";

        mLoadBar = (ProgressBar) findViewById(R.id.pb_main_activity);
        mErrorTextView = (TextView) findViewById(R.id.tv_error);



        mRecyclerView = (RecyclerView) findViewById(R.id.rv_movieposter);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        GridLayoutManager glm = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(glm);
        mRecyclerView.setHasFixedSize(true);


        mAdapter = new MovieDbAdapter(2, mPosterPathList, this, this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemViewCacheSize(10);

        mRecyclerView.setDrawingCacheEnabled(true);
//        mRecyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);




        builtUrlForPopular = NetworkUtils.generateApiUrlForMovieDb(SORT_POPULAR);
        builtUrlForTopRated = NetworkUtils.generateApiUrlForMovieDb(SORT_TOP_RATED);
        new FetchMovieDbTask().execute(builtUrlForPopular);



    }

    private void refershRecyclerView(String[] s)  {
        mPosterPathList = s;
        mAdapter = new MovieDbAdapter(listItemCount, s, this, this);

        mRecyclerView.setAdapter(mAdapter);
        Log.d(LOG_TAG, "onPostExecute: " + "String array length = " + listItemCount);
    }

    private void showdata() {
        mErrorTextView.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void hideData(){
        mErrorTextView.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.INVISIBLE);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id){
            case R.id.action_popular:

                new FetchMovieDbTask().execute(builtUrlForPopular);
                Log.d(LOG_TAG, "onOptionsItemSelected: Popular " + builtUrlForPopular.toString());
                return true;

            case R.id.action_top_rated:

                new FetchMovieDbTask().execute(builtUrlForTopRated);
                Log.d(LOG_TAG, "onOptionsItemSelected: Top Rated "+ builtUrlForTopRated.toString());
                return true;

                default:
                    return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        Toast.makeText(this, "Clicked Item = " + clickedItemIndex, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra(CLICKED_ITEM_INDEX, clickedItemIndex);
        intent.putExtra(MOVIE_POSTER_PATH, mPosterPathList[clickedItemIndex]);
        intent.putExtra(JsonUtils.DB_RELEASE_DATE, mReleaseDateList[clickedItemIndex]);
        intent.putExtra(JsonUtils.DB_USER_RATING, mUserRatingList[clickedItemIndex]);
        intent.putExtra(JsonUtils.DB_ORIGINAL_TITLE, mMovieOriginalTitleList[clickedItemIndex]);
        intent.putExtra(JsonUtils.DB_OVERVIEW, mMovieOverviewList[clickedItemIndex]);
        startActivity(intent);
    }

    class FetchMovieDbTask extends AsyncTask<URL, Void, String[]> {

        @Override
        protected void onPreExecute() {
            mLoadBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String[] doInBackground(URL... params) {
            String jsonStr = null;
            String[] posterPathList = null;
            try {
                jsonStr = NetworkUtils.getResponseFromHttpUrl(params[0]);
                posterPathList = JsonUtils.getMoviePosterPathFromJson(jsonStr);
                mMovieOriginalTitleList = JsonUtils.getMovieOriginalTitleFromJson(jsonStr);
                mMovieIdList = JsonUtils.getMovieIdFromJson(jsonStr);
                mReleaseDateList = JsonUtils.getMovieReleaseDateFromJson(jsonStr);
                mUserRatingList = JsonUtils.getMovieUserRatingFromJson(jsonStr);
                mMovieOverviewList = JsonUtils.getMovieOverviewFromJson(jsonStr);
//                movieDbPosterList = JsonUtils.getMoviePosterPathFromJson(jsonStr);
//                Log.d(LOG_TAG, "doInBackground: " + movieDbPosterList.get(0));
            } catch (Exception e) {
                e.printStackTrace();
            }

            return posterPathList;

        }

        @Override
        protected void onPostExecute(String[] s) {

            mLoadBar.setVisibility(View.INVISIBLE);

            if (s == null) {
                hideData();
            }else{
                listItemCount = s.length;
                showdata();
                refershRecyclerView(s);
            }

//            for (String posterList : s
//                 ) {
//
//               mPosterPathList = posterList;
//            }

        }



//        @Override
//        public void onListItemClick(int clickedItemIndex) {
//            Toast.makeText(getApplicationContext(), "Clicked from asyncTask " + clickedItemIndex, Toast.LENGTH_SHORT).show();
//        }

    }


}
