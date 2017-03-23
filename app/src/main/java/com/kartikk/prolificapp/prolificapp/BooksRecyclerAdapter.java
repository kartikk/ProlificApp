package com.kartikk.prolificapp.prolificapp;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kartikk.prolificapp.prolificapp.databinding.CardBookBinding;
import com.kartikk.prolificapp.prolificapp.models.Book;
import com.kartikk.prolificapp.prolificapp.util.Helper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Kartikk on 3/21/2017.
 */

public class BooksRecyclerAdapter extends RecyclerView.Adapter<BooksRecyclerAdapter.ViewHolder> {

    private List<Book> bookList = new ArrayList<>();
    static String TAG = BooksRecyclerAdapter.class.getSimpleName();
    Context parentContext;
    public static final int ID_ASC = 0, TITLE_ASC = 1, AUTHOR_ASC = 2, ID_DESC = 3, TITLE_DESC = 4, AUTHOR_DESC = 5;

    public void setBookList(List<Book> bookList, int sortMethodFlag) {
        this.bookList = bookList;
        changeSorting(sortMethodFlag);
    }

    public void changeSorting(final int sortMethodFlag) {
        Collections.sort(bookList, new Comparator<Book>() {
            @Override
            public int compare(Book book, Book t1) {
                if (sortMethodFlag == ID_ASC) {
                    return book.getId() - t1.getId();
                } else if (sortMethodFlag == ID_DESC) {
                    return t1.getId() - book.getId();
                } else if (sortMethodFlag == TITLE_DESC) {
                    return t1.getTitle().compareToIgnoreCase(book.getTitle());
                } else if (sortMethodFlag == TITLE_ASC) {
                    return book.getTitle().compareToIgnoreCase(t1.getTitle());
                } else if (sortMethodFlag == AUTHOR_DESC) {
                    return t1.getAuthor().compareToIgnoreCase(book.getAuthor());
                } else if (sortMethodFlag == AUTHOR_ASC) {
                    return book.getAuthor().compareToIgnoreCase(t1.getAuthor());
                }
                return 0;
            }
        });
        notifyDataSetChanged();
    }

    public BooksRecyclerAdapter(List<Book> bookList, Context context, int sortMethodFlag) {
        this.bookList = new ArrayList<>(bookList);
        parentContext = context;
        changeSorting(sortMethodFlag);
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
                                    if (response.isSuccessful() && response.code() == 204) {
                                        Log.d(TAG, "Book deletion success, response: " + response.body());
                                        if (parentContext != null) {
                                            MainActivity mainActivity = (MainActivity) parentContext;
                                            mainActivity.updateRecyclerView();
                                            Snackbar.make(mainActivity.activityMainBinding.mainActivityLinearLayout, R.string.delete_book_success, Snackbar.LENGTH_LONG).show();
                                        }
                                    } else {
                                        Log.d(TAG, "Invalid response for delete book, response: " + response);
                                        if (parentContext != null) {
                                            MainActivity mainActivity = (MainActivity) parentContext;
                                            Snackbar.make(mainActivity.activityMainBinding.mainActivityLinearLayout, R.string.delete_book_failed, Snackbar.LENGTH_LONG).show();
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<Void> call, Throwable t) {
                                    Log.d(TAG, "Book deletion failed, message: " + t.getMessage());
                                    if (parentContext != null) {
                                        MainActivity mainActivity = (MainActivity) parentContext;
                                        Snackbar.make(mainActivity.activityMainBinding.mainActivityLinearLayout, R.string.delete_book_failed, Snackbar.LENGTH_LONG).show();
                                    }
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
