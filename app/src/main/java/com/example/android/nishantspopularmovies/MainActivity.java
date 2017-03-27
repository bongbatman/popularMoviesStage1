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
    private String[] mPosterList;
    private int listItemCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mPosterList = new String[2];
        mPosterList[0] = "Dummy data item 1";
        mPosterList[1] = "Dummy data item 2";



        mRecyclerView = (RecyclerView) findViewById(R.id.rv_movieposter);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        GridLayoutManager glm = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(glm);
        mRecyclerView.setHasFixedSize(true);


        mAdapter = new MovieDbAdapter(2, mPosterList, this, this);
        mRecyclerView.setAdapter(mAdapter);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                URL builtUrl = NetworkUtils.generateApiUrlForMovieDb(SORT_POPULAR);
                new FetchMovieDbTask().execute(builtUrl);

                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void refershRecyclerView(String[] s)  {
        mPosterList = s;
        mAdapter = new MovieDbAdapter(listItemCount, s, this, this);

        mRecyclerView.setAdapter(mAdapter);
        Log.d(LOG_TAG, "onPostExecute: " + "String array length = " + listItemCount);
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        Toast.makeText(this, "Clicked Item = " + clickedItemIndex, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra(CLICKED_ITEM_INDEX, clickedItemIndex);
        intent.putExtra(MOVIE_POSTER_PATH, mPosterList[clickedItemIndex]);
        startActivity(intent);
    }

    class FetchMovieDbTask extends AsyncTask<URL, Void, String[]> {


        @Override
        protected String[] doInBackground(URL... params) {
            String jsonStr = null;
            String[] posterPathList = null;
            try {
                jsonStr = NetworkUtils.getResponseFromHttpUrl(params[0]);
                posterPathList = JsonUtils.getMovieDetailStringsFromJson(jsonStr);
//                movieDbPosterList = JsonUtils.getMovieDetailStringsFromJson(jsonStr);
//                Log.d(LOG_TAG, "doInBackground: " + movieDbPosterList.get(0));
            } catch (Exception e) {
                e.printStackTrace();
            }

            return posterPathList;

        }

        @Override
        protected void onPostExecute(String[] s) {

            listItemCount = s.length;
            refershRecyclerView(s);

//            for (String posterList : s
//                 ) {
//
//               mPosterList = posterList;
//            }

        }



//        @Override
//        public void onListItemClick(int clickedItemIndex) {
//            Toast.makeText(getApplicationContext(), "Clicked from asyncTask " + clickedItemIndex, Toast.LENGTH_SHORT).show();
//        }

    }


}
