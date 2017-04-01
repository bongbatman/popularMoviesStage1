package data;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.popularmovies.R;
import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;

import utils.NetworkUtils;

/**
 * Created by Nishant on 24-03-2017.
 */

public class MovieDbAdapter extends RecyclerView.Adapter<MovieDbAdapter.MovieDbViewHolder> {

    private static final String LOG_TAG = MovieDbAdapter.class.getSimpleName() ;
    private static String[] movieIdList;
    private static int itemCount;
    final private ListItemClickListener mOnClickListener;
    final private Context mContext;




    public MovieDbAdapter(int itemCount, String[] moivePoserUrlList, ListItemClickListener clickListener, Context context) {
        this.itemCount = itemCount;
        this.movieIdList = moivePoserUrlList;
        mOnClickListener = clickListener;
        this.mContext = context;
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
           requiredUrl = String.valueOf(NetworkUtils.generatePosterUrl(movieIdList[position]));

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


        holder.bind(requiredUrl, requiredUrl);
    }

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }







    @Override
    public int getItemCount() {
        return itemCount;
    }





   class MovieDbViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{



        private ImageView mImageView;

        public MovieDbViewHolder(View itemView) {
            super(itemView);


            mImageView = (ImageView) itemView.findViewById(R.id.iv_movie_poster);
            itemView.setOnClickListener(this);

        }

        void bind (String listIndex, String imageUrl){

            

            Picasso.with(mContext).load(imageUrl).into(mImageView);
        }

       @Override
       public void onClick(View v) {
           int clickedPosition = getAdapterPosition();
           mOnClickListener.onListItemClick(clickedPosition);
       }
   }






}
