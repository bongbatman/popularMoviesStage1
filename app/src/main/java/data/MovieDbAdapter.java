package data;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.nishantspopularmovies.R;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.zip.Inflater;

import utils.NetworkUtils;

import static android.content.ContentValues.TAG;

/**
 * Created by Nishant on 24-03-2017.
 */

public class MovieDbAdapter extends RecyclerView.Adapter<MovieDbAdapter.MovieDbViewHolder> {

    private static final String LOG_TAG = MovieDbAdapter.class.getSimpleName() ;
    private static String[] posterUrl;
    private static int itemCount;
    final private ListItemClickListener mOnClickListener;



    public MovieDbAdapter(int itemCount, String[] moivePoserUrlList, ListItemClickListener clickListener) {
        this.itemCount = itemCount;
        this.posterUrl = moivePoserUrlList;
        mOnClickListener = clickListener;
        notifyDataSetChanged();

    }

    @Override
    public MovieDbViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        int recyclerLayoutId = R.layout.recycler_view_list_item;

        View view = inflater.inflate(recyclerLayoutId, parent, shouldAttachToParentImmediately);

        MovieDbViewHolder viewHolder = new MovieDbViewHolder(view);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MovieDbViewHolder holder, int position) {

        String requiredUrl = null;

        try {
           requiredUrl = String.valueOf(NetworkUtils.generatePosterUrl(posterUrl[position]));
            Log.d(LOG_TAG, "onBindViewHolder: " + "Formed Movie URls = " + requiredUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


        holder.bind(requiredUrl);
    }

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }



    @Override
    public int getItemCount() {
        return itemCount;
    }



   class MovieDbViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


        private TextView mMoviePosterUrlTextView;

        public MovieDbViewHolder(View itemView) {
            super(itemView);

            mMoviePosterUrlTextView = (TextView) itemView.findViewById(R.id.tv_movieposter_url);
            itemView.setOnClickListener(this);

        }

        void bind (String listIndex){
            mMoviePosterUrlTextView.setText(listIndex);
        }

       @Override
       public void onClick(View v) {
           int clickedPosition = getAdapterPosition();
           mOnClickListener.onListItemClick(clickedPosition);
       }
   }






}
