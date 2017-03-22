package com.kartikk.prolificapp.prolificapp;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.kartikk.prolificapp.prolificapp.databinding.ActivityBookDetailBinding;
import com.kartikk.prolificapp.prolificapp.models.Book;
import com.kartikk.prolificapp.prolificapp.models.Checkout;
import com.kartikk.prolificapp.prolificapp.util.Constants;
import com.kartikk.prolificapp.prolificapp.util.Helper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Kartikk on 3/21/2017.
 */

public class BookDetailActivity extends AppCompatActivity {

    ActivityBookDetailBinding bookDetailBinding;
    ShareActionProvider shareActionProvider;
    Book book;

    private static final String TAG = BookDetailActivity.class.getSimpleName();

    // Hardcoded since we don't have a registration page
    private static final String userName = "Kartikk";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        book = getIntent().getParcelableExtra("book");
        bookDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_book_detail);
        bookDetailBinding.setData(book);
        bookDetailBinding.executePendingBindings();
        bookDetailBinding.bookLastCheckedOut.setText(getCheckedOutStatusString());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        bookDetailBinding.bookCheckoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<Void> call = Helper.getRetrofitEndpoints().checkoutBook(book.getId(), new Checkout(userName));
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful() && response.code() == 200) {
                            Log.d(TAG, "Checkout success, response: " + response);
                            Toast.makeText(getApplicationContext(), getString(R.string.checkout_success), Toast.LENGTH_LONG).show();
                            finish();
                        } else {
                            Log.d(TAG, "Invalid response for checkout, response: " + response);
                            Toast.makeText(getApplicationContext(), getString(R.string.checkout_failed), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.d(TAG, "Checkout failed, message: " + t.getMessage());
                        Toast.makeText(getApplicationContext(), getString(R.string.checkout_failed), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    public String getCheckedOutStatusString() {
        if (book != null) {
            if (bookDetailBinding != null) {
                bookDetailBinding.bookLastCheckedOut.setVisibility(View.VISIBLE);
            }
            if (book.isCheckedOutValid()) {
                DateFormat newDateFormat = new SimpleDateFormat("MMMM d, yyyy h:mm:ss a", getResources().getConfiguration().locale);
                newDateFormat.setTimeZone(Calendar.getInstance().getTimeZone());
                String newDateText = newDateFormat.format(book.getLastCheckedOut());
                if (book.isCheckedOutByValid()) {
                    return getString(R.string.book_checked_out) + " " + book.getLastCheckedOutBy() + " @ " + newDateText;
                }
                return getString(R.string.book_checked_out) + " " + newDateText;
            }
            if (book.isCheckedOutByValid())
                return getString(R.string.book_checked_out) + " " + book.getLastCheckedOutBy();
        }
        if (bookDetailBinding != null) {
            bookDetailBinding.bookLastCheckedOut.setVisibility(View.GONE);
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
