package com.example.android.nishantspopularmovies;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import utils.JsonUtils;
import utils.NetworkUtils;

public class MainActivity extends AppCompatActivity {

    final static String SORT_POPULAR = "popular";
    final static String SORT_TOP_RATED = "top_rated";
    private static final String LOG_TAG = MainActivity.class.getSimpleName() ;
    ArrayList<String> movieDbPosterList = null;

    private TextView mJsonTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mJsonTextView = (TextView) findViewById(R.id.tv_url_display);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                URL builtUrl = NetworkUtils.buildUrl(SORT_POPULAR);
                new FetchMovieDbTask().execute(builtUrl);

                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
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


            for (String posterList : s
                 ) {

                mJsonTextView.append(posterList + "\n\n\n");
            }

        }
    }
}
