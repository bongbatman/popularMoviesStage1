package utils;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.ArrayList;

/**
 * Created by Nishant on 23-03-2017.
 */

public class JsonUtils {

    private final static String LOG_TAG = JsonUtils.class.getSimpleName();


    public static String[] getMovieDetailStringsFromJson(String movieDbStr)
            throws JSONException {




        final String DB_RESULT = "results";

        final String DB_MOVIE_POSTER = "poster_path";

        String mPosterPath;


        JSONObject movieDbJson = new JSONObject(movieDbStr);


        JSONArray movieDbArray = movieDbJson.getJSONArray(DB_RESULT);



        int arrayLength = movieDbArray.length();

        String[] mPosterList = new String[arrayLength];

        for (int i = 0; i < arrayLength ; i++) {
            JSONObject movieObject = movieDbArray.getJSONObject(i);
            mPosterPath = movieObject.getString(DB_MOVIE_POSTER);
            mPosterList[i] = mPosterPath;
            Log.d(LOG_TAG, "getMovieDetailStringsFromJson: " + mPosterList[i]);



        }



        return mPosterList;

    }
}
