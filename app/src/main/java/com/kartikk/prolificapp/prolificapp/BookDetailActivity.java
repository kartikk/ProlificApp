package com.kartikk.prolificapp.prolificapp;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.kartikk.prolificapp.prolificapp.databinding.ActivityBookDetailBinding;
import com.kartikk.prolificapp.prolificapp.models.Book;

/**
 * Created by Kartikk on 3/21/2017.
 */

public class BookDetailActivity extends AppCompatActivity {

    ActivityBookDetailBinding bookDetailBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Book book = getIntent().getParcelableExtra("book");
        bookDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_book_detail);
        bookDetailBinding.setData(book);
        bookDetailBinding.executePendingBindings();
    }
}
