package utils;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by Nishant on 23-03-2017.
 */

public class JsonUtils {

    private final static String LOG_TAG = JsonUtils.class.getSimpleName();

    static final String DB_RESULT = "results";

    final static String DB_MOVIE_POSTER = "poster_path";

    public final static String DB_OVERVIEW = "overview";

    final public static String DB_ID = "id";

    final public static String DB_ORIGINAL_TITLE = "original_title";

    final public static String DB_USER_RATING = "vote_average";

    final public static String DB_RELEASE_DATE = "release_date";

    static String mPosterPath;

    static String mMovieOriginalTitle;

    static String mMovieOverview;

    static String mMovieId;

    static String mUserRating;

    static String mReleaseDate;


    public static String[] getMoviePosterPathFromJson(String movieDbStr)
            throws JSONException {


        JSONObject movieDbJson = new JSONObject(movieDbStr);


        JSONArray movieDbArray = movieDbJson.getJSONArray(DB_RESULT);


        int arrayLength = movieDbArray.length();

        String[] posterPathList = new String[arrayLength];


        for (int i = 0; i < arrayLength; i++) {
            JSONObject movieObject = movieDbArray.getJSONObject(i);
            mPosterPath = movieObject.getString(DB_MOVIE_POSTER);


            posterPathList[i] = mPosterPath;





        }


        return posterPathList;

    }


    public static String[] getMovieIdFromJson(String movieDbStr)
            throws JSONException {


        JSONObject movieDbJson = new JSONObject(movieDbStr);


        JSONArray movieDbArray = movieDbJson.getJSONArray(DB_RESULT);


        int arrayLength = movieDbArray.length();

        String[] movieIdList = new String[arrayLength];


        for (int i = 0; i < arrayLength; i++) {
            JSONObject movieObject = movieDbArray.getJSONObject(i);
            mMovieId = movieObject.getString(DB_ID);


            movieIdList[i] = mMovieId;




        }


        return movieIdList;

    }

    public static String[] getMovieOverviewFromJson(String movieDbStr)
            throws JSONException {


        JSONObject movieDbJson = new JSONObject(movieDbStr);


        JSONArray movieDbArray = movieDbJson.getJSONArray(DB_RESULT);


        int arrayLength = movieDbArray.length();

        String[] movieOverviewList = new String[arrayLength];


        for (int i = 0; i < arrayLength; i++) {
            JSONObject movieObject = movieDbArray.getJSONObject(i);
            mMovieOverview = movieObject.getString(DB_OVERVIEW);


            movieOverviewList[i] = mMovieOverview;





        }


        return movieOverviewList;

    }

    public static String[] getMovieOriginalTitleFromJson(String movieDbStr)
            throws JSONException {


        JSONObject movieDbJson = new JSONObject(movieDbStr);


        JSONArray movieDbArray = movieDbJson.getJSONArray(DB_RESULT);


        int arrayLength = movieDbArray.length();

        String[] movieOriginalTitleList = new String[arrayLength];


        for (int i = 0; i < arrayLength; i++) {
            JSONObject movieObject = movieDbArray.getJSONObject(i);
            mMovieOriginalTitle = movieObject.getString(DB_ORIGINAL_TITLE);


            movieOriginalTitleList[i] = mMovieOriginalTitle;





        }


        return movieOriginalTitleList;

    }

    public static String[] getMovieUserRatingFromJson(String movieDbStr)
            throws JSONException {


        JSONObject movieDbJson = new JSONObject(movieDbStr);


        JSONArray movieDbArray = movieDbJson.getJSONArray(DB_RESULT);


        int arrayLength = movieDbArray.length();

        String[] movieUserRatingList = new String[arrayLength];


        for (int i = 0; i < arrayLength; i++) {
            JSONObject movieObject = movieDbArray.getJSONObject(i);
            mUserRating = movieObject.getString(DB_USER_RATING);


            movieUserRatingList[i] = mUserRating;





        }


        return movieUserRatingList;

    }

    public static String[] getMovieReleaseDateFromJson(String movieDbStr)
            throws JSONException {


        JSONObject movieDbJson = new JSONObject(movieDbStr);


        JSONArray movieDbArray = movieDbJson.getJSONArray(DB_RESULT);


        int arrayLength = movieDbArray.length();

        String[] movieReleaseDateList = new String[arrayLength];


        for (int i = 0; i < arrayLength; i++) {
            JSONObject movieObject = movieDbArray.getJSONObject(i);
            mReleaseDate = movieObject.getString(DB_RELEASE_DATE);


            movieReleaseDateList[i] = mReleaseDate;





        }


        return movieReleaseDateList;

    }







}
