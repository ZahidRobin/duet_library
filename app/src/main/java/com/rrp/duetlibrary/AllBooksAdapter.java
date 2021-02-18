package com.rrp.duetlibrary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

public class AllBooksAdapter extends RecyclerView.Adapter<AllBooksAdapter.AllBooksHolder> {
    Context context;
    private List<AddBookClass> addBookClassList;


    public AllBooksAdapter(Context context, List<AddBookClass> addBookClassList) {
        this.context = context;
        this.addBookClassList = addBookClassList;
    }

    @NonNull
    @Override
    public AllBooksHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.single_book, parent,false);

        return new AllBooksHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllBooksHolder holder, int position) {
        AddBookClass addBookClass =  addBookClassList.get(position);
        holder.titleTextView.setText(addBookClass.getBookName());
        holder.authorTextView.setText(addBookClass.getAuthorName());
        holder.ssbnTextView.setText(addBookClass.getSsbn());
        Picasso.get()
                .load(addBookClass.getUri())
                .placeholder(R.drawable.book_cover)
                .fit()
                .into(holder.bookCoverImageView);
    }

    @Override
    public int getItemCount() {
        return addBookClassList.size();
    }

    class AllBooksHolder extends RecyclerView.ViewHolder{
        TextView titleTextView,authorTextView,ssbnTextView;
        ImageView bookCoverImageView;
        public AllBooksHolder(@NonNull View itemView) {
            super(itemView);

            titleTextView = itemView.findViewById(R.id.singleBookTitleId);
            authorTextView = itemView.findViewById(R.id.singleBookAuthorId);
            ssbnTextView = itemView.findViewById(R.id.singleBookSSBNId);
            bookCoverImageView = itemView.findViewById(R.id.singleBookImageViewId);
        }
    }
}
