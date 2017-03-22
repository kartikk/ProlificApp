package com.kartikk.prolificapp.prolificapp;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;

import com.kartikk.prolificapp.prolificapp.databinding.ActivityBookDetailBinding;
import com.kartikk.prolificapp.prolificapp.models.Book;
import com.kartikk.prolificapp.prolificapp.util.Constants;

/**
 * Created by Kartikk on 3/21/2017.
 */

public class BookDetailActivity extends AppCompatActivity {

    ActivityBookDetailBinding bookDetailBinding;
    ShareActionProvider shareActionProvider;
    Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        book = getIntent().getParcelableExtra("book");
        bookDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_book_detail);
        bookDetailBinding.setData(book);
        bookDetailBinding.executePendingBindings();
        bookDetailBinding.bookLastCheckedOut.setText(getCheckedOutStatusString());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public String getCheckedOutStatusString() {
        // TODO return date in the correct format
        if (book != null) {
            if (book.isCheckedOutByValid() && book.isCheckedOutValid())
                return getString(R.string.book_checked_out) + " " + book.getLastCheckedOutBy() + " @ " + book.getLastCheckedOut();
            else if (book.isCheckedOutByValid())
                return getString(R.string.book_checked_out) + " " + book.getLastCheckedOutBy();
            else
                return getString(R.string.book_checked_out) + " " + book.getLastCheckedOut();
        }
        return "";
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (book != null && book.isURLValid()) {
            getMenuInflater().inflate(R.menu.menu_share, menu);
            MenuItem menuItem = menu.findItem(R.id.menu_item_share);
            shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, Constants.baseURL + Constants.urlPart1 + book.getUrl());
            shareActionProvider.setShareIntent(shareIntent);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                supportFinishAfterTransition();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
