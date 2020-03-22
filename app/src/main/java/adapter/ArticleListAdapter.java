package adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.honetware.technology.technews.R;
import com.honetware.technology.technews.WebViewActivity;
import com.honetware.technology.technews.persistence.BookMark;
import com.honetware.technology.technews.persistence.DataStore;

import java.util.List;

import com.honetware.technology.technews.model.Article;

import utils.Constants;
import utils.Utils;

public class ArticleListAdapter  extends RecyclerView.Adapter<ArticleListAdapter.MyViewHolder>{

    Context context;
    List<Article> articleList;

    public ArticleListAdapter(Context context, List<Article> articleList) {
        this.context = context;
        this.articleList = articleList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Article article = articleList.get(position);

        Picasso.get()
                .load(article.getUrlToImage() !=null ? article.getUrlToImage(): Constants.Companion.getPlaceholder_image())
                .into(holder.imageView);
        holder.title.setText(article.getTitle());

        holder.publish_date.setText(Utils.toReadableDate(article.getPublishedAt()));

        holder.bookmark.setOnClickListener(bookmarkListener(article));
    }

    public View.OnClickListener bookmarkListener( Article article){
        return v -> {

            DataStore dataStore = DataStore.getInstance(context);
            BookMark bookMark = new BookMark();
            bookMark.setLink(article.getUrl());
            bookMark.setPublish_date(article.getPublishedAt());
            bookMark.setTitle(article.getTitle());

            dataStore.insertBookMark(bookMark);
        };
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView title, publish_date,bookmark;
        public ImageView imageView;

        public MyViewHolder(View view) {
            super(view);
            title =  view.findViewById(R.id.title);
            publish_date =  view.findViewById(R.id.published_time);
            imageView = view.findViewById(R.id.imageView);
            bookmark = view.findViewById(R.id.bookmark);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Article article = articleList.get(position);

                    Intent intent = new Intent(context, WebViewActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("url", article.getUrl());

                    context.startActivity(intent);
                }
            });
        }
    }

}
