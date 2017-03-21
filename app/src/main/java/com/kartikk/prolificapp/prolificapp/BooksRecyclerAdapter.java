package com.kartikk.prolificapp.prolificapp;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kartikk.prolificapp.prolificapp.databinding.CardBookBinding;
import com.kartikk.prolificapp.prolificapp.models.Book;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kartikk on 3/21/2017.
 */

public class BooksRecyclerAdapter extends RecyclerView.Adapter<BooksRecyclerAdapter.ViewHolder> {

    List<Book> bookList;
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
    public void onBindViewHolder(BooksRecyclerAdapter.ViewHolder holder, int position) {
        CardBookBinding cardBookBinding = DataBindingUtil.findBinding(holder.itemView);
        cardBookBinding.setResult(bookList.get(position));
        cardBookBinding.executePendingBindings();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        if(bookList!=null)
        return bookList.size();
        else
            return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
