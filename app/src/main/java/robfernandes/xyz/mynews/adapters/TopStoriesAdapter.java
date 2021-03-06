package robfernandes.xyz.mynews.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import robfernandes.xyz.mynews.ui.activities.NewsDisplayActivity;
import robfernandes.xyz.mynews.network.model.APIResponseTopStories;
import robfernandes.xyz.mynews.R;

/**
 * Created by Roberto Fernandes on 12/12/2018.
 */
public class TopStoriesAdapter extends RecyclerView.Adapter<TopStoriesAdapter.ViewHolder> {
    private final List<APIResponseTopStories.Result> mNewsResultsList;
    private final Context mContext;

    public TopStoriesAdapter(List<APIResponseTopStories.Result> newsResultsList, Context context) {
        mNewsResultsList = newsResultsList;
        mContext = context;
    }

    @NonNull
    @Override
    public TopStoriesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.news_row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopStoriesAdapter.ViewHolder viewHolder, int i) {
        APIResponseTopStories.Result news = mNewsResultsList.get(i);
        String newsTitle = news.getTitle();
        String newsCategory = news.getSection();
        String date = news.getUpdatedDate();
        if (date != null && date.length() > 11) {
            date = date.substring(0, 10); //only year, month and day
        }
        String imageURL = "";

        //if there is only 1 image take the url of that one, if there are more, take the medium size
        if (news.getMultimedia().size() == 1) {
            imageURL = news.getMultimedia().get(0).getUrl();
        } else if (news.getMultimedia().size() > 1) {
            imageURL = news.getMultimedia().get(1).getUrl();
        }

        if (news.getMultimedia().size() > 0) {
            Glide.with(mContext).load(imageURL).into(viewHolder.image);
        }

        if (!news.getSubsection().equals("")) {
            newsCategory += " > " + news.getSubsection();
        }

        viewHolder.title.setText(newsTitle);
        viewHolder.category.setText(newsCategory);
        viewHolder.date.setText(date);
    }

    @Override
    public int getItemCount() {
        if (mNewsResultsList == null) {
            return 0;
        }
        return mNewsResultsList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView title;
        private final ImageView image;
        private final TextView category;
        private final TextView date;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.news_row_title);
            image = itemView.findViewById(R.id.news_row_image);
            category = itemView.findViewById(R.id.news_row_category);
            date = itemView.findViewById(R.id.news_row_date);


            itemView.setOnClickListener(v -> {
                int itemPosition = getAdapterPosition();
                String url = mNewsResultsList.get(itemPosition).getUrl();

                Intent intent = new Intent(mContext, NewsDisplayActivity.class);
                intent.putExtra("URL", url);
                mContext.startActivity(intent);
            });
        }
    }
}
