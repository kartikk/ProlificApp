package com.kartikk.prolificapp.prolificapp;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.kartikk.prolificapp.prolificapp.databinding.ActivityMainBinding;
import com.kartikk.prolificapp.prolificapp.models.Book;
import com.kartikk.prolificapp.prolificapp.util.Helper;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding activityMainBinding;
    RecyclerView recyclerView;
    BooksRecyclerAdapter booksRecyclerAdapter;
    LinearLayoutManager linearLayoutManager;
    static final String TAG = MainActivity.class.getSimpleName();

    public final static String LIST_STATE_KEY = "recycler_list_state";
    Parcelable listState;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        recyclerView = activityMainBinding.booksRecyclerView;
        Call<List<Book>> call = Helper.getRetrofitEndpoints().getBooks();
        // TODO handle network issues better
        call.enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                booksRecyclerAdapter = new BooksRecyclerAdapter(response.body());
                recyclerView.setAdapter(booksRecyclerAdapter);
                linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setVisibility(View.VISIBLE);
                Log.d(TAG, "Get books success" + response.body());
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
                Log.d(TAG, "Get books failed" + t.getMessage());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add:
                Intent addIntent = new Intent(this, AddBookActivity.class);
                startActivity(addIntent);
                return true;
            case R.id.menu_seed:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (linearLayoutManager != null) {
            listState = linearLayoutManager.onSaveInstanceState();
            outState.putParcelable(LIST_STATE_KEY, listState);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null)
            listState = savedInstanceState.getParcelable(LIST_STATE_KEY);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (linearLayoutManager != null && listState != null) {
            linearLayoutManager.onRestoreInstanceState(listState);
        }
    }
}
