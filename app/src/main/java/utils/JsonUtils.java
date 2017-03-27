package utils;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Nishant on 23-03-2017.
 */

public class JsonUtils {

    private final static String LOG_TAG = JsonUtils.class.getSimpleName();


    public static String[] getMovieDetailStringsFromJson(String movieDbStr)
            throws JSONException {




        final String DB_RESULT = "results";

        final String DB_MOVIE_POSTER = "poster_path";

        final String DB_OVERVIEW = "overview";

        final String DB_ID = "id";

        final String DB_ORIGINAL_TITLE = "original_title";

        final String DB_USER_RATING = "vote_average";

        final String DB_RELEASE_DATE = "release_date";

        String mPosterPath;

        String mMovieOriginalTitle;

        String mMovieOverview;

        String mMovieId;

        String mUserRating;

        String mReleaseDate;


        JSONObject movieDbJson = new JSONObject(movieDbStr);


        JSONArray movieDbArray = movieDbJson.getJSONArray(DB_RESULT);





        int arrayLength = movieDbArray.length();

        String[] posterList = new String[arrayLength];
        String[] originalTitle = new String[arrayLength];
        String[] movieId = new String[arrayLength];
        Map<String, String> idPosterMap = new HashMap<String, String>();


        for (int i = 0; i < arrayLength ; i++) {
            JSONObject movieObject = movieDbArray.getJSONObject(i);
            mPosterPath = movieObject.getString(DB_MOVIE_POSTER);
            mMovieOriginalTitle = movieObject.getString(DB_ORIGINAL_TITLE);
            mMovieId = movieObject.getString(DB_ID);
            mReleaseDate = movieObject.getString(DB_RELEASE_DATE);
            mMovieOverview = movieObject.getString(DB_OVERVIEW);
            mUserRating = movieObject.getString(DB_USER_RATING);
            movieId[i] = mMovieId;
            posterList[i] = mPosterPath;
            originalTitle[i] = mMovieOriginalTitle;
            idPosterMap.put(movieId[i], posterList[i]);





            Log.d(LOG_TAG, "getMovieDetailStringsFromJson: " + movieId[i] + " " + originalTitle[i]);



        }



        return posterList;

    }
}
