package data;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.nishantspopularmovies.R;

import java.util.zip.Inflater;

/**
 * Created by Nishant on 24-03-2017.
 */

public class MovieDbAdapter extends RecyclerView.Adapter<MovieDbAdapter.MovieDbViewHolder> {

    private static String[] posterUrl;
    private static int itemCount;

    public MovieDbAdapter(int itemCount, String[] moivePoserUrlList) {
        this.itemCount = itemCount;
        this.posterUrl = moivePoserUrlList;
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




        holder.bind(posterUrl[position]);
    }


    @Override
    public int getItemCount() {
        return itemCount;
    }

   class MovieDbViewHolder extends RecyclerView.ViewHolder {


        private TextView mMoviePosterUrlTextView;

        public MovieDbViewHolder(View itemView) {
            super(itemView);

            mMoviePosterUrlTextView = (TextView) itemView.findViewById(R.id.tv_movieposter_url);

        }

        void bind (String listIndex){
            mMoviePosterUrlTextView.setText(listIndex);
        }

    }




}
