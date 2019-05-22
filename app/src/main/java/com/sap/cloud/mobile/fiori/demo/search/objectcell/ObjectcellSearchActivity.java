package com.sap.cloud.mobile.fiori.demo.search.objectcell;

import static com.sap.cloud.mobile.onboarding.qrcodereader.google.QRCodeReaderActivity.REQ_CAMERA;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.LoaderManager;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.sap.cloud.mobile.fiori.demo.AbstractDemoActivity;
import com.sap.cloud.mobile.fiori.demo.R;
import com.sap.cloud.mobile.fiori.demo.object.BaseObjectCellActivity;
import com.sap.cloud.mobile.fiori.demo.object.BizObject;
import com.sap.cloud.mobile.fiori.demo.object.ObjectCellSpec;
import com.sap.cloud.mobile.fiori.demo.search.DemoSearchBarcodeValidator;
import com.sap.cloud.mobile.fiori.search.FioriSearchView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ObjectcellSearchActivity extends AbstractDemoActivity implements LoaderManager.LoaderCallbacks<List<BizObject>> {

    private RecyclerView mRecyclerView;
    private ObjectcellSearchAdapter mAdapter;
    private LinearLayout mFrameLayout;
    private FioriSearchView mFioriSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_objectcell_search);
        mRecyclerView = findViewById(R.id.object_cell_search_recycler);
        mFrameLayout = findViewById(R.id.object_cell_search_constraint);
        setSupportActionBar(findViewById(R.id.my_toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setupResultsList();
        getLoaderManager().initLoader(0, null, this);
        handleIntent(getIntent());
    }

    private void setupResultsList() {
        mAdapter = new ObjectcellSearchAdapter(Glide.with(this), getObjectCellSpec());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @NonNull
    @Override
    public Loader<List<BizObject>> onCreateLoader(int i, Bundle bundle) {
        return new BaseObjectCellActivity.BizObjectLoader(getBaseContext(), getObjectCellSpec());
    }

    @Override
    public void onLoadFinished(Loader<List<BizObject>> loader, List<BizObject> bizObjects) {
        mAdapter.setObjects(bizObjects);
        SearchSuggestionProvider.setData(bizObjects);
        mRecyclerView.setVisibility(View.VISIBLE);
        ObjectAnimator animation = ObjectAnimator.ofFloat(mRecyclerView, "alpha", 0, 100);
        animation.setDuration(1000);
        animation.start();
        mFrameLayout.postInvalidate();
    }

    @Override
    public void onLoaderReset(Loader<List<BizObject>> loader) {
        mAdapter.setObjects(mAdapter.getOriginals());
        SearchSuggestionProvider.setData(mAdapter.getOriginals());
    }

    @NonNull
    private List<BizObject> select(List<Integer> positions) {
        List<BizObject> filtered = new ArrayList<>();
        for (int i : positions) {
            filtered.add(mAdapter.getOriginals().get(i));
        }
        return filtered;
    }

    /**
     * Subclasses need to override this method to specify how the object cells are to be rendered.
     */
    @NonNull
    protected ObjectCellSpec getObjectCellSpec() {
        return new ObjectCellSpec(R.layout.object_cell_1, 20, 2, 1,
                true,
                true, false, true,
                true, true, false);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_VIEW.equals(intent.getAction())) {
            mAdapter.setObjects(Collections.singletonList(
                    SearchSuggestionProvider.getMatchedSuggestions().get(Integer.parseInt(intent.getData().getLastPathSegment()))));
        } else if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            mAdapter.setObjects(SearchSuggestionProvider.getMatchedSuggestions());
        }
    }

    @Override
    protected void onNewIntent(@NonNull Intent intent) {
        /* Because this activity has set launchMode="singleTop", the system calls this method to deliver the intent if this activity is
        currently the foreground activity when invoked again (when the user executes a search from this activity, we don't create a new
        instance of this activity, so the system delivers the search intent here) */
        handleIntent(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        mFioriSearchView = (FioriSearchView) menu.findItem(R.id.menu_search).getActionView();
        if (mFioriSearchView != null) {
            SearchManager manager = (SearchManager) getSystemService(SEARCH_SERVICE);
            SearchableInfo info = manager.getSearchableInfo(getComponentName());
            mFioriSearchView.setIconifiedByDefault(true);
            mFioriSearchView.setSearchableInfo(info);
            mFioriSearchView.setFullScreenSuggestion(true);
            mFioriSearchView.setBackgroundResource(R.color.transparent);
            mFioriSearchView.setScanEnabled(true);
            mFioriSearchView.setMaxWidth(Integer.MAX_VALUE);
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQ_CAMERA && resultCode == Activity.RESULT_OK && DemoSearchBarcodeValidator.getBarcode() != null) {
            mFioriSearchView.setQuery(DemoSearchBarcodeValidator.getBarcode().displayValue, true);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
