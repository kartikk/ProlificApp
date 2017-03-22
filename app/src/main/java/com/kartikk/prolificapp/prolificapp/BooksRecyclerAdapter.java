package com.kartikk.prolificapp.prolificapp;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kartikk.prolificapp.prolificapp.databinding.CardBookBinding;
import com.kartikk.prolificapp.prolificapp.models.Book;
import com.kartikk.prolificapp.prolificapp.util.Helper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Kartikk on 3/21/2017.
 */

public class BooksRecyclerAdapter extends RecyclerView.Adapter<BooksRecyclerAdapter.ViewHolder> {

    private List<Book> bookList;
    static String TAG = BooksRecyclerAdapter.class.getSimpleName();

    public void setBookList(List<Book> bookList) {
        this.bookList = new ArrayList<>(bookList);
    }

    public BooksRecyclerAdapter(List<Book> bookList) {
        this.bookList = new ArrayList<>(bookList);
    }

    @Override
    public BooksRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        CardBookBinding cardBookBinding = DataBindingUtil.inflate(inflater, R.layout.card_book, parent, false);
        ViewHolder viewHolder = new ViewHolder(cardBookBinding.getRoot());
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final BooksRecyclerAdapter.ViewHolder holder, int position) {
        final Context context = holder.itemView.getContext();
        final CardBookBinding cardBookBinding = DataBindingUtil.findBinding(holder.itemView);
        cardBookBinding.setResult(bookList.get(position));
        cardBookBinding.executePendingBindings();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bookDetailIntent = new Intent(context, BookDetailActivity.class);
                bookDetailIntent.putExtra("book", bookList.get(holder.getAdapterPosition()));
                android.support.v4.util.Pair<View, String> p1 = android.support.v4.util.Pair.create((View) cardBookBinding.bookCardTitleTextView, "bookTitle");
                ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context, p1);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    context.startActivity(bookDetailIntent, activityOptionsCompat.toBundle());
                } else {
                    context.startActivity(bookDetailIntent);
                }
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                //TODO switch to Contextual action menu
                final CharSequence[] items = {context.getString(R.string.main_context_delete)};

                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setTitle(context.getString(R.string.main_context_title));
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (item == 0) {
                            Call<Void> call = Helper.getRetrofitEndpoints().deleteBook(bookList.get(holder.getAdapterPosition()).getId());
                            call.enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {
                                    Log.d(TAG, "Book deletion success");
                                }

                                @Override
                                public void onFailure(Call<Void> call, Throwable t) {
                                    Log.d(TAG, "Book deletion failed, message: " + t.getMessage());
                                }
                            });
                        }
                    }
                });
                builder.show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        if (bookList != null)
            return bookList.size();
        else
            return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
