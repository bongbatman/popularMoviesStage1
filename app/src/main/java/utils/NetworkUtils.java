package utils;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Nishant on 23-03-2017.
 */

public class NetworkUtils {

    final static String LOG_TAG = NetworkUtils.class.getSimpleName();

    final static String MOVIEDB_BASE_URL =
            "https://api.themoviedb.org/3/movie";

    final static String PARAM_QUERY = "api_key";
    final static String MOVIEDB_API_KEY = "enter_api_key_here";
    private static final String BASE_POSTER_URL = "http://image.tmdb.org/t/p";
    private static final String PATH_POSTER_SIZE = "w780";



    public static URL generateApiUrlForMovieDb(String movieDbQuerySortPrefrence) {
        Uri builtUri = Uri.parse(MOVIEDB_BASE_URL).buildUpon()
                .appendPath(movieDbQuerySortPrefrence)
                .appendQueryParameter(PARAM_QUERY, MOVIEDB_API_KEY)
                .build();



        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


        return url;
    }

    public static URL generatePosterUrl (String posterPath) throws MalformedURLException {
        Uri posterUri = Uri.parse(BASE_POSTER_URL).buildUpon()
                .appendPath(PATH_POSTER_SIZE)
                .appendEncodedPath(posterPath)
                .build();
        URL builtPosterUrl;
        builtPosterUrl = new URL(posterUri.toString());

        return builtPosterUrl;

    }



    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                String jsonStr = scanner.next();

                return jsonStr;
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }


    }



}
