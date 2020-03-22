package adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.honetware.technology.technews.R;
import com.honetware.technology.technews.WebViewActivity;
import com.honetware.technology.technews.persistence.BookMark;
import com.honetware.technology.technews.persistence.DataStore;

import java.util.List;

import utils.Utils;

public class BookMarksAdapter extends RecyclerView.Adapter<BookMarksAdapter.MyViewHolder>{
    List<BookMark> bookMarkList;
    Context context;

    public BookMarksAdapter(List<BookMark> bookMarkList, Context context) {
        this.bookMarkList = bookMarkList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bookmark_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        BookMark bookMark = bookMarkList.get(position);
        holder.title.setText(bookMark.getTitle());
        holder.publish_date.setText(Utils.toReadableDate(bookMark.getPublish_date()));

        holder.delete.setOnClickListener(bookmarkListener(bookMark));
    }

    public View.OnClickListener bookmarkListener(BookMark bookMark){
        return v -> {

            DataStore dataStore = DataStore.getInstance(context);
            dataStore.deleteBookMark(bookMark);
        };
    }

    @Override
    public int getItemCount() {
        return bookMarkList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView title, publish_date,delete;
        public MyViewHolder(@NonNull View view) {
            super(view);
            title =  view.findViewById(R.id.title);
            publish_date =  view.findViewById(R.id.published_time);
            delete = view.findViewById(R.id.delete);

            view.setOnClickListener(v -> {
                int position = getAdapterPosition();
                BookMark bookMark = bookMarkList.get(position);
                Intent intent = new Intent(context, WebViewActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("url", bookMark.getLink());

                context.startActivity(intent);
            });
        }
    }
}
