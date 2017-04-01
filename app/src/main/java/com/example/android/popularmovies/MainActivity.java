package com.example.android.popularmovies;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.net.URL;


import data.MovieDbAdapter;
import utils.JsonUtils;
import utils.NetworkUtils;

public class MainActivity extends AppCompatActivity implements MovieDbAdapter.ListItemClickListener {

    final static String SORT_POPULAR = "popular";
    final static String SORT_TOP_RATED = "top_rated";

    public static final String CLICKED_ITEM_INDEX = "clicked_item_index" ;
    public static final String MOVIE_POSTER_PATH = "movie_poster_path" ;



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
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_movieposter);

        GridLayoutManager glm = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(glm);
        mRecyclerView.setHasFixedSize(true);



        mPosterPathList = new String[2];
        mPosterPathList[0] = "Dummy data item 1";
        mPosterPathList[1] = "Dummy data item 2";

        mLoadBar = (ProgressBar) findViewById(R.id.pb_main_activity);
        mErrorTextView = (TextView) findViewById(R.id.tv_error);





        mAdapter = new MovieDbAdapter(2, mPosterPathList, this, this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemViewCacheSize(10);

        mRecyclerView.setDrawingCacheEnabled(true);



        builtUrlForPopular = NetworkUtils.generateApiUrlForMovieDb(SORT_POPULAR);
        builtUrlForTopRated = NetworkUtils.generateApiUrlForMovieDb(SORT_TOP_RATED);
        new FetchMovieDbTask().execute(builtUrlForPopular);




    }




    private void refreshRecyclerView(String[] s)  {
        mPosterPathList = s;
        mAdapter = new MovieDbAdapter(listItemCount, s, this, this);

        mRecyclerView.setAdapter(mAdapter);

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

                return true;

            case R.id.action_top_rated:

                new FetchMovieDbTask().execute(builtUrlForTopRated);

                return true;

                default:
                    return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {

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
                refreshRecyclerView(s);
            }



        }




    }


}
