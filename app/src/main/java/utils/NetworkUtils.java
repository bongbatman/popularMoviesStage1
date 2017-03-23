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
    final static String MOVIEDB_API_KEY = "b410a107f37009673dc9a65d75f317b3";



    public static URL buildUrl(String movieDbQuerySortPrefrence) {
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

        Log.d(LOG_TAG, "buildUrl: " + url);
        return url;
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
                Log.d(LOG_TAG, "getResponseFromHttpUrl: " + jsonStr);
                return jsonStr;
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }


    }



}
