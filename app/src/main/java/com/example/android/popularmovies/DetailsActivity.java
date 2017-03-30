package com.example.android.popularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.nishantspopularmovies.MainActivity;
import com.example.android.nishantspopularmovies.R;
import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;

import utils.JsonUtils;
import utils.NetworkUtils;

public class DetailsActivity extends AppCompatActivity {

    TextView mMovieOriginalTitleTextView;
    TextView mMovieReleaseDate;
    ImageView mMoviePosterImageView;
    TextView mMovieUserRating;
    TextView mMovieOverview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        mMovieOriginalTitleTextView = (TextView) findViewById(R.id.tv_movie_original_title);
        mMovieReleaseDate = (TextView) findViewById(R.id.tv_movie_release_date);
        mMoviePosterImageView = (ImageView) findViewById(R.id.iv_movie_poster_details_activity);
        mMovieUserRating = (TextView) findViewById(R.id.tv_movie_user_rating);
        mMovieOverview = (TextView) findViewById(R.id.tv_movie_overview);

        Intent intent = getIntent();


        if (intent.hasExtra(MainActivity.CLICKED_ITEM_INDEX)) {
            String number = String.valueOf(intent.getIntExtra(MainActivity.CLICKED_ITEM_INDEX, 0));
            String urlPath = intent.getStringExtra(MainActivity.MOVIE_POSTER_PATH);
            String originalTitle = intent.getStringExtra(JsonUtils.DB_ORIGINAL_TITLE);
            String releaseDate = intent.getStringExtra(JsonUtils.DB_RELEASE_DATE);
            String userRating = intent.getStringExtra(JsonUtils.DB_USER_RATING);
            String overview = intent.getStringExtra(JsonUtils.DB_OVERVIEW);


            String fullPosterUrl = null;
            try {
                fullPosterUrl = String.valueOf(NetworkUtils.generatePosterUrl(urlPath));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            Picasso.with(this).load(fullPosterUrl).resize(500,680).centerInside().into(mMoviePosterImageView);
            mMovieOriginalTitleTextView.setText(originalTitle);
            mMovieReleaseDate.setText(releaseDate);
            mMovieUserRating.setText(userRating+"/10");
            mMovieOverview.setText(overview);
        }

    }
}
