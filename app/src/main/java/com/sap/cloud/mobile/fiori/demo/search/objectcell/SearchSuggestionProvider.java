package com.sap.cloud.mobile.fiori.demo.search.objectcell;

import android.app.SearchManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SearchRecentSuggestionsProvider;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.support.annotation.AnyRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.sap.cloud.mobile.fiori.demo.R;
import com.sap.cloud.mobile.fiori.demo.object.BizObject;

import java.util.ArrayList;
import java.util.List;

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

/**
 * Provides access to the dictionary database.
 */
public class SearchSuggestionProvider extends SearchRecentSuggestionsProvider {
    public static final String AUTHORITY = "com.sap.cloud.mobile.fiori.demo.search.objectcell.SearchSuggestionProvider";
    // UriMatcher stuff
    private static final int SEARCH_ADDRESS = 0;
    private static final int GET_ADDRESS = 1;
    private static final int SEARCH_SUGGEST = 2;
    private static final UriMatcher sURIMatcher = buildUriMatcher();

    private static List<BizObject> mSuggestions;
    private static List<BizObject> mMatchedSuggestions;

    String TAG = SearchSuggestionProvider.class.getCanonicalName();

    public SearchSuggestionProvider() {
        setupSuggestions(AUTHORITY, DATABASE_MODE_QUERIES);
    }

    /**
     * Builds up a UriMatcher for search suggestion and shortcut refresh queries.
     */
    @NonNull
    private static UriMatcher buildUriMatcher() {
        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        // to get definitions...
        matcher.addURI(AUTHORITY, "objectcell", SEARCH_ADDRESS);
        matcher.addURI(AUTHORITY, "objectcell/#", GET_ADDRESS);
        // to get suggestions...
        matcher.addURI(AUTHORITY, SearchManager.SUGGEST_URI_PATH_QUERY, SEARCH_SUGGEST);
        matcher.addURI(AUTHORITY, SearchManager.SUGGEST_URI_PATH_QUERY + "/*", SEARCH_SUGGEST);
        return matcher;
    }


    static void setData(@NonNull List<BizObject> data) {
        mSuggestions.addAll(data);
    }

    public static List<BizObject> getMatchedSuggestions() {
        return mMatchedSuggestions;
    }

    /**
     * Handles all the dictionary searches and suggestion queries from the Search Manager.
     * When requesting a specific word, the uri alone is required.
     * When searching all of the dictionary for matches, the selectionArgs argument must carry
     * the search query as the first element.
     * All other arguments are ignored.
     */
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, @Nullable String[] selectionArgs, String sortOrder) {
        // Use the UriMatcher to see what kind of query we have and format the db query accordingly
        switch (sURIMatcher.match(uri)) {
            case SEARCH_SUGGEST:
                if (selectionArgs == null) {
                throw new IllegalArgumentException("selectionArgs must be provided for the Uri: " + uri);
            }
            return getSuggestions(selectionArgs[0]);
            case SEARCH_ADDRESS:
                if (selectionArgs == null) {
                    throw new IllegalArgumentException("selectionArgs must be provided for the Uri: " + uri);
                }
                return getSuggestions(selectionArgs[0]);
            default:
                throw new UnsupportedOperationException();
        }
    }

    @Override
    public boolean onCreate() {
        mSuggestions = new ArrayList<>();
        mMatchedSuggestions = new ArrayList<>();
        return true;
    }

    @NonNull
    private Cursor getSuggestions(@NonNull String query) {
        String[] columns = new String[]{BaseColumns._ID, SearchManager.SUGGEST_COLUMN_ICON_1, SearchManager.SUGGEST_COLUMN_TEXT_1, SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID};
        MatrixCursor cursor = new MatrixCursor(columns);

        mMatchedSuggestions.clear();
        int index = 0;
        for (BizObject bizObject : mSuggestions) {
            if (toString(bizObject).toUpperCase().contains(query.toUpperCase())) {
                mMatchedSuggestions.add(bizObject);
                cursor.addRow(new Object[]{Integer.toString(index), getUriToDrawable(getContext(), R.drawable.ic_access_time_black_24dp), toString(bizObject), Integer.toString(index++)});
            }
        }
        return cursor;
    }

    private String toString(BizObject object) {
        return object.getHeadline() + object.getSubHeadline() + object.getFootnote();
    }

    /**
     * get uri to drawable or any other resource type if u wish
     * @param context - context
     * @param drawableId - drawable res id
     * @return - uri
     */
    public static final Uri getUriToDrawable(@NonNull Context context, @AnyRes int drawableId) {
        Uri imageUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                "://" + context.getResources().getResourcePackageName(drawableId)
                + '/' + context.getResources().getResourceTypeName(drawableId)
                + '/' + context.getResources().getResourceEntryName(drawableId) );
        return imageUri;
    }

}
