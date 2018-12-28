/*
 * Copyright (C) 2010 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sap.cloud.mobile.fiori.demo.search.dictionary;

import static android.content.SearchRecentSuggestionsProvider.DATABASE_MODE_QUERIES;

import static com.sap.cloud.mobile.onboarding.qrcodereader.google.QRCodeReaderActivity.REQ_CAMERA;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.support.annotation.NonNull;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.sap.cloud.mobile.fiori.demo.AbstractDemoActivity;
import com.sap.cloud.mobile.fiori.demo.R;
import com.sap.cloud.mobile.fiori.demo.search.DemoSearchBarcodeValidator;
import com.sap.cloud.mobile.fiori.search.FioriSearchView;

/**
 * The searchable_dictionary activity for the dictionary.
 * Displays search results triggered by the search dialog and handles
 * actions from search suggestions.
 */
public class SearchableDictionary extends AbstractDemoActivity {

    private static final String TAG = SearchableDictionary.class.getCanonicalName();
    private ListView mListView;
    private FioriSearchView mFioriSearchView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchable_dictionary);

        mListView = findViewById(R.id.definition_list);

        mFioriSearchView = findViewById(R.id.search);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        mFioriSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        mFioriSearchView.setIconifiedByDefault(false);
        mFioriSearchView.setScanEnabled(true);
        mFioriSearchView.setOnCollapseBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFioriSearchView.clearFocus();
            }
        });
        mFioriSearchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    ((ImageView) findViewById(R.id.search_mag_icon)).setImageResource(R.drawable.ic_arrow_back_black_24dp);
                } else {
                    ((ImageView) findViewById(R.id.search_mag_icon)).setImageResource(R.drawable.ic_search);
                }
            }
        });
        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(@NonNull Intent intent) {
        // Because this activity has set launchMode="singleTop", the system calls this method
        // to deliver the intent if this activity is currently the foreground activity when
        // invoked again (when the user executes a search from this activity, we don't create
        // a new instance of this activity, so the system delivers the search intent here)
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        mListView.setAdapter(null);
        if (Intent.ACTION_VIEW.equals(intent.getAction())) {
            // handles a click on a search suggestion; launches activity to show word
            Intent wordIntent = new Intent(this, WordActivity.class);
            wordIntent.setData(intent.getData());
            startActivity(wordIntent);
        } else if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            // handles a search query
            String query = intent.getStringExtra(SearchManager.QUERY);
            showResults(query);
            SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this, DictionaryProvider.AUTHORITY, DATABASE_MODE_QUERIES);
            suggestions.saveRecentQuery(query, null);
        }
    }

    /**
     * Searches the dictionary and displays results for the given query.
     *
     * @param query The search query
     */
    private void showResults(String query) {

        Cursor cursor = managedQuery(DictionaryProvider.CONTENT_URI, null, null,
                new String[]{query}, null);

        if (cursor != null) {
            // Specify the columns we want to display in the result
            String[] from = new String[]{DictionaryDatabase.KEY_WORD, DictionaryDatabase.KEY_DEFINITION};

            // Specify the corresponding layout elements where we want the columns to go
            int[] to = new int[]{R.id.word, R.id.definition};

            mListView.setAdapter(new SimpleCursorAdapter(this, R.layout.searchable_dictionary_word, cursor, from, to));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQ_CAMERA && resultCode == Activity.RESULT_OK && DemoSearchBarcodeValidator.getBarcode() != null) {
            mFioriSearchView.setQuery(DemoSearchBarcodeValidator.getBarcode().displayValue, true);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
