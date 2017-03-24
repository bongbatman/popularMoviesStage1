package com.example.android.popularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.android.nishantspopularmovies.MainActivity;
import com.example.android.nishantspopularmovies.R;

import java.net.MalformedURLException;

import utils.NetworkUtils;

public class DetailsActivity extends AppCompatActivity {

    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        mTextView = (TextView) findViewById(R.id.tv_details_activity);

        Intent intent = getIntent();


        if (intent.hasExtra(MainActivity.CLICKED_ITEM_INDEX)) {
            String number = String.valueOf(intent.getIntExtra(MainActivity.CLICKED_ITEM_INDEX, 0));
            String urlPath = intent.getStringExtra(MainActivity.MOVIE_POSTER_PATH);
            String fullPosterUrl = null;
            try {
                fullPosterUrl = String.valueOf(NetworkUtils.generatePosterUrl(urlPath));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            mTextView.setText(fullPosterUrl);
        }

    }
}
