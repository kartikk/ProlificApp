package com.kartikk.prolificapp.prolificapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RadioButton;

import com.crashlytics.android.Crashlytics;
import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringSystem;
import com.kartikk.prolificapp.prolificapp.databinding.ActivityMainBinding;
import com.kartikk.prolificapp.prolificapp.models.Book;
import com.kartikk.prolificapp.prolificapp.models.UpdateBook;
import com.kartikk.prolificapp.prolificapp.util.Helper;

import io.fabric.sdk.android.Fabric;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public ActivityMainBinding activityMainBinding;
    RecyclerView recyclerView;
    BooksRecyclerAdapter booksRecyclerAdapter;
    LinearLayoutManager linearLayoutManager;
    static final String TAG = MainActivity.class.getSimpleName();
    Context context;

    Boolean sortTitle = true, sortAuthor = false, sortAsc = true, sortDesc = false;
    int sortMethodCode = BooksRecyclerAdapter.ID_ASC;

    RadioButton titleRadioButton, authorRadioButton, ascRadio, descRadio;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        context = this;
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        recyclerView = activityMainBinding.booksRecyclerView;
        final FloatingActionButton fab = activityMainBinding.mainFAB;
        SpringSystem springSystem = SpringSystem.create();
        SpringConfig springConfig = new SpringConfig(800, 15);
        final Spring spring = springSystem.createSpring();
        spring.setSpringConfig(springConfig);
        spring.addListener(new SimpleSpringListener() {
            @Override
            public void onSpringUpdate(Spring spring) {
                float value = (float) spring.getCurrentValue();
                float scale = 1f - (value * 0.5f);
                fab.setScaleX(scale);
                fab.setScaleY(scale);
            }
        });
        fab.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        spring.setEndValue(1f);
                        return true;
                    case MotionEvent.ACTION_UP:
                        spring.setEndValue(0f);
                        Intent addIntent = new Intent(getApplicationContext(), AddBookActivity.class);
                        startActivity(addIntent);
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_sort:
                showSortDialog();
                return true;
            case R.id.menu_delete_all:
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(getString(R.string.delete_all_title));
                builder.setPositiveButton(getString(R.string.delete_all_confirm_alert), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteAllBooks();
                        dialogInterface.dismiss();
                    }
                });
                builder.setNegativeButton(getString(R.string.delete_all_cancel_alert), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.show();
                return false;
            case R.id.menu_seed:
                seedData();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void seedData() {
        UpdateBook book1 = new UpdateBook("User Interface Development: Beginner's Guide", "Jason Morris", "O'Reilly Media", "interface, ui, android");
        Call<Void> call = Helper.getRetrofitEndpoints().postBook(book1);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    UpdateBook book2 = new UpdateBook("Programming Android", "Zigurd Mednieks, Laird Dornin, G. Blake Meike, Masumi Nakamura", "O'Reilly Media", "android");
                    Call<Void> call1 = Helper.getRetrofitEndpoints().postBook(book2);
                    call1.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful() && response.code() == 200) {
                                UpdateBook book3 = new UpdateBook("Android 4 Application Development", "Reto Meier", "Wrox", "android,professional");
                                Call<Void> call2 = Helper.getRetrofitEndpoints().postBook(book3);
                                call2.enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        if (response.isSuccessful() && response.code() == 200) {
                                            UpdateBook book4 = new UpdateBook("iOS Programming: The Big Nerd Ranch Guide", "Joe Conway and Aaron Hillegass", "Big Nerd Ranch", "big nerd ranch, ios");
                                            Call<Void> call3 = Helper.getRetrofitEndpoints().postBook(book4);
                                            call3.enqueue(new Callback<Void>() {
                                                @Override
                                                public void onResponse(Call<Void> call, Response<Void> response) {
                                                    if (response.isSuccessful() && response.code() == 200) {
                                                        Snackbar.make(activityMainBinding.mainActivityLinearLayout, R.string.seed_success, Snackbar.LENGTH_LONG).show();
                                                        updateRecyclerView();
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<Void> call, Throwable t) {
                                                    Snackbar.make(activityMainBinding.mainActivityLinearLayout, R.string.seed_failed, Snackbar.LENGTH_LONG).show();
                                                    Log.d(TAG, "Seed failed, message: " + t.getMessage());
                                                }
                                            });
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {
                                        Snackbar.make(activityMainBinding.mainActivityLinearLayout, R.string.seed_failed, Snackbar.LENGTH_LONG).show();
                                        Log.d(TAG, "Seed failed, message: " + t.getMessage());
                                    }
                                });
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Snackbar.make(activityMainBinding.mainActivityLinearLayout, R.string.seed_failed, Snackbar.LENGTH_LONG).show();
                            Log.d(TAG, "Seed failed, message: " + t.getMessage());
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Snackbar.make(activityMainBinding.mainActivityLinearLayout, R.string.seed_failed, Snackbar.LENGTH_LONG).show();
                Log.d(TAG, "Seed failed, message: " + t.getMessage());
            }
        });
    }

    private void showSortDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_sort, null);
        titleRadioButton = (RadioButton) dialogView.findViewById(R.id.dialogSortTitleRadioButton);
        authorRadioButton = (RadioButton) dialogView.findViewById(R.id.dialogSortAuthorRadioButton);
        ascRadio = (RadioButton) dialogView.findViewById(R.id.dialogAscRadioButton);
        descRadio = (RadioButton) dialogView.findViewById(R.id.dialogDecRadioButton);
        titleRadioButton.setChecked(sortTitle);
        authorRadioButton.setChecked(sortAuthor);
        ascRadio.setChecked(sortAsc);
        descRadio.setChecked(sortDesc);
        builder.setView(dialogView).setPositiveButton(getString(R.string.dialog_sort_positive), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                sortAuthor = authorRadioButton.isChecked();
                sortTitle = titleRadioButton.isChecked();
                sortAsc = ascRadio.isChecked();
                sortDesc = descRadio.isChecked();
                if (sortTitle) {
                    if (sortAsc) {
                        sortMethodCode = BooksRecyclerAdapter.TITLE_ASC;
                    } else if (sortDesc) {
                        sortMethodCode = BooksRecyclerAdapter.TITLE_DESC;
                    }
                } else if (sortAuthor) {
                    if (sortAsc) {
                        sortMethodCode = BooksRecyclerAdapter.AUTHOR_ASC;
                    } else if (sortDesc) {
                        sortMethodCode = BooksRecyclerAdapter.AUTHOR_DESC;
                    }
                }
                booksRecyclerAdapter.changeSorting(sortMethodCode);
            }
        });
        builder.create().show();
    }

    private void deleteAllBooks() {
        Call<Void> call = Helper.getRetrofitEndpoints().deleteAllBooks();
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    Log.d(TAG, "Delete all books success, response: " + response.body());
                    if (activityMainBinding != null) {
                        Snackbar.make(activityMainBinding.mainActivityLinearLayout, R.string.delete_all_success, Snackbar.LENGTH_LONG).show();
                    }
                    int count = booksRecyclerAdapter.getItemCount();
                    booksRecyclerAdapter.setBookList(new ArrayList<Book>(), sortMethodCode);
                    booksRecyclerAdapter.notifyItemRangeRemoved(0, count);
//                    updateRecyclerView();
                } else {
                    if (activityMainBinding != null) {
                        Snackbar.make(activityMainBinding.mainActivityLinearLayout, R.string.delete_all_fail, Snackbar.LENGTH_LONG).show();
                    }
                    Log.d(TAG, "Incorrect response for delete all, response: " + response);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                if (activityMainBinding != null) {
                    Snackbar.make(activityMainBinding.mainActivityLinearLayout, R.string.delete_all_fail, Snackbar.LENGTH_LONG).show();
                }
                Log.d(TAG, "Delete all books failed, message: " + t.getMessage());
            }
        });
    }

    public void updateRecyclerView() {
        Call<List<Book>> call = Helper.getRetrofitEndpoints().getBooks();
        call.enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    if (booksRecyclerAdapter == null) {
                        booksRecyclerAdapter = new BooksRecyclerAdapter(response.body(), context, sortMethodCode);
                        recyclerView.setAdapter(booksRecyclerAdapter);
                    } else {
                        booksRecyclerAdapter.setBookList(response.body(), sortMethodCode);
                        booksRecyclerAdapter.notifyDataSetChanged();
                    }
                    linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setVisibility(View.VISIBLE);
                    Log.d(TAG, "Get books success, response: " + response.body());
                } else {
                    if (activityMainBinding != null) {
                        Snackbar.make(activityMainBinding.mainActivityLinearLayout, R.string.main_recycler_fail, Snackbar.LENGTH_LONG).show();
                    }
                    Log.d(TAG, "Incorrect response for get books, response: " + response);
                }
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
                Log.d(TAG, "Get books failed, message: " + t.getMessage());
                if (activityMainBinding != null) {
                    Snackbar.make(activityMainBinding.mainActivityLinearLayout, R.string.main_recycler_fail, Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateRecyclerView();
    }
}
