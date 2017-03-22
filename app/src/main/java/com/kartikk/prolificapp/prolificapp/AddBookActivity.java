package com.kartikk.prolificapp.prolificapp;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.kartikk.prolificapp.prolificapp.databinding.ActivityAddBookBinding;
import com.kartikk.prolificapp.prolificapp.models.UpdateBook;
import com.kartikk.prolificapp.prolificapp.util.Helper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Kartikk on 3/21/2017.
 */

public class AddBookActivity extends AppCompatActivity {

    ActivityAddBookBinding activityAddBookBinding;
    TextInputLayout titleTextInputLayout, authorTextInputLayout, publisherTextInputLayout, categoriesTextInputLayout;
    AppCompatButton submitButton;
    Boolean titleValid = false, authorValid = false, publisherValid = false, categoriesValid = false;
    String titleText, authorText, publisherText, categoriesText;

    static final String TAG = AddBookActivity.class.getSimpleName();

    // set to true when config is changed, used to prevent error message from showing on config change
    Boolean titleConfigChange = true, authorConfigChange = true, publisherConfigChange = true, categoriesConfigChange = true;

    // TODO test on low dpi

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAddBookBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_book);
        activityAddBookBinding.executePendingBindings();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        submitButton = activityAddBookBinding.submitButton;

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateBook();
                finish();
            }
        });
        titleTextInputLayout = activityAddBookBinding.titleEditTextLayout;
        titleTextInputLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().trim().length() == 0) {
                    if (!titleConfigChange) {
                        titleTextInputLayout.setError(getString(R.string.title_error));
                        titleTextInputLayout.setErrorEnabled(true);
                    }
                    titleValid = false;
                } else {
                    titleTextInputLayout.setErrorEnabled(false);
                    titleText = editable.toString().trim();
                    titleValid = true;
                }
                titleConfigChange = false;
                handleSubmitButton();
            }
        });

        authorTextInputLayout = activityAddBookBinding.authorEditTextLayout;
        authorTextInputLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().trim().length() == 0) {
                    if (!authorConfigChange) {
                        authorTextInputLayout.setError(getString(R.string.author_error));
                        authorTextInputLayout.setErrorEnabled(true);
                    }
                    authorValid = false;
                } else {
                    authorTextInputLayout.setErrorEnabled(false);
                    authorText = editable.toString().trim();
                    authorValid = true;
                }
                authorConfigChange = false;
                handleSubmitButton();
            }
        });

        publisherTextInputLayout = activityAddBookBinding.publisherEditTextLayout;
        publisherTextInputLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().trim().length() == 0) {
                    if (!publisherConfigChange) {
                        publisherTextInputLayout.setError(getString(R.string.publisher_error));
                        publisherTextInputLayout.setErrorEnabled(true);
                    }
                    publisherValid = false;
                } else {
                    publisherTextInputLayout.setErrorEnabled(false);
                    publisherText = editable.toString().trim();
                    publisherValid = true;
                }
                publisherConfigChange = false;
                handleSubmitButton();
            }
        });

        categoriesTextInputLayout = activityAddBookBinding.categoriesEditTextLayout;
        categoriesTextInputLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().trim().length() == 0) {
                    if (!categoriesConfigChange) {
                        categoriesTextInputLayout.setError(getString(R.string.categories_error));
                        categoriesTextInputLayout.setErrorEnabled(true);
                    }
                    categoriesValid = false;
                } else {
                    categoriesTextInputLayout.setErrorEnabled(false);
                    categoriesText = editable.toString().trim();
                    categoriesValid = true;
                }
                categoriesConfigChange = false;
                handleSubmitButton();
            }
        });

    }

    private void handleSubmitButton() {
        if (submitButton != null) {
            if (titleValid && authorValid && publisherValid && categoriesValid) {
                submitButton.setEnabled(true);
            } else {
                submitButton.setEnabled(false);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_book, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.menu_done:
                if (titleValid && authorValid && publisherValid && categoriesValid) {
                    updateBook();
                    finish();
                    return true;
                }
                if (titleTextInputLayout != null && !titleValid) {
                    titleTextInputLayout.setError(getString(R.string.title_error));
                    titleTextInputLayout.setErrorEnabled(true);
                }
                if (authorTextInputLayout != null && !authorValid) {
                    authorTextInputLayout.setError(getString(R.string.author_error));
                    authorTextInputLayout.setErrorEnabled(true);
                }
                if (publisherTextInputLayout != null && !publisherValid) {
                    publisherTextInputLayout.setError(getString(R.string.publisher_error));
                    publisherTextInputLayout.setErrorEnabled(true);
                }
                if (categoriesTextInputLayout != null && !categoriesValid) {
                    categoriesTextInputLayout.setError(getString(R.string.categories_error));
                    categoriesTextInputLayout.setErrorEnabled(true);
                }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (titleTextInputLayout != null && titleText != null) {
            titleConfigChange = true;
            titleTextInputLayout.getEditText().setText(titleText);
        }
        if (authorTextInputLayout != null && authorText != null) {
            authorConfigChange = true;
            authorTextInputLayout.getEditText().setText(authorText);
        }
        if (publisherTextInputLayout != null && publisherText != null) {
            publisherConfigChange = true;
            publisherTextInputLayout.getEditText().setText(publisherText);
        }
        if (categoriesTextInputLayout != null && categoriesText != null) {
            categoriesConfigChange = true;
            categoriesTextInputLayout.getEditText().setText(categoriesText);
        }
    }

    private void updateBook() {
        UpdateBook updateBook = new UpdateBook(titleText, authorText, publisherText, categoriesText);
        Call<Void> call = Helper.getRetrofitEndpoints().postBook(updateBook);
        // TODO handle network issues better
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d(TAG, "Update success, response: " + response.body());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

                Log.d(TAG, "Update failed, message: " + t.getMessage());
            }
        });
    }
}
