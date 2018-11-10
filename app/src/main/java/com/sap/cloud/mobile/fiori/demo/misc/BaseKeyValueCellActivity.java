package com.sap.cloud.mobile.fiori.demo.misc;

import android.animation.ObjectAnimator;
import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.target.Target;
import com.sap.cloud.mobile.fiori.demo.AbstractDemoActivity;
import com.sap.cloud.mobile.fiori.demo.R;
import com.sap.cloud.mobile.fiori.misc.KeyValueCell;
import com.sap.cloud.mobile.fiori.object.ObjectCell;

import java.util.ArrayList;
import java.util.List;

public class BaseKeyValueCellActivity extends AbstractDemoActivity implements
        LoaderManager.LoaderCallbacks<List<Item>> {
    private static final String TAG = "KeyValueCellActivity";
    public static final String CALL_TEXT = "This is the number to be called: ";

    public static final int NUM_ITEMS = 300;
    public static final int LINES = 3;

    private KeyValueSpec mKeyValueSpec;
    protected KeyValueRecyclerAdapter mAdapter;
    protected RecyclerView mRecyclerView;
    private ConstraintLayout mConstraintLayout;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getContentLayout());

        mProgressBar = findViewById(R.id.progress);
        mConstraintLayout = findViewById(R.id.my_constraint);

        mAdapter = createKeyValueRecyclerAdapter();
        mRecyclerView = findViewById(R.id.my_recycler_view);
        configureRecyclerView();
        getLoaderManager().initLoader(0, null, this);
    }

    protected int getContentLayout(){
        return R.layout.content_base_object_cell;
    }

    protected void configureRecyclerView() {
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        if (getKeyValueSpec().getLines() == 0){
            mRecyclerView.setHasFixedSize(false);
        }else {
            mRecyclerView.setHasFixedSize(true);
        }
        mRecyclerView.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    @NonNull
    protected KeyValueRecyclerAdapter createKeyValueRecyclerAdapter() {
        return new KeyValueRecyclerAdapter(Glide.with(this), getKeyValueSpec());
    }

    /**
     * Subclasses need to override this method to specify how the object cells are to be rendered.
     */
    public KeyValueSpec getKeyValueSpec() {
        if (mKeyValueSpec == null) {
            mKeyValueSpec = new KeyValueSpec(R.layout.key_value_cell, NUM_ITEMS, LINES, "Item", false);
        }
        return mKeyValueSpec;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @NonNull
    @Override
    public Loader<List<Item>> onCreateLoader(int id, Bundle args) {
        return new ItemLoader(getBaseContext(), getKeyValueSpec());
    }

    @Override
    public void onLoadFinished(Loader<List<Item>> loader, List<Item> data) {
        Log.d(TAG, "onLoadFinished");
        mAdapter.setItems(data);
        // The list should now be shown.
        mProgressBar.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
        ObjectAnimator animation = ObjectAnimator.ofFloat(mRecyclerView, "alpha", 0, 100);
        animation.setDuration(1000);
        animation.start();

        mConstraintLayout.postInvalidate();
    }

    @Override
    public void onLoaderReset(Loader<List<Item>> loader) {
        mAdapter.setItems(null);
    }

    public static class ItemLoader extends AsyncTaskLoader<List<Item>> {
        private KeyValueSpec mKeyValueSpec;

        public ItemLoader(Context context, KeyValueSpec objectCellSpec ) {
            super(context);
            mKeyValueSpec = objectCellSpec;
        }

        @Override
        protected void onStartLoading() {
            forceLoad();
        }

        @NonNull
        @Override
        public List<Item> loadInBackground() {
            List<Item> objects = Item.createItems(mKeyValueSpec);
            return objects;
        }
    }

    public static class KeyValueRecyclerAdapter extends
            RecyclerView.Adapter<KeyValueRecyclerAdapter.ViewHolder> {
        private static final String TAG = "KeyValueRecyclerAdapter";

        @Nullable
        protected List<Item> mItems = new ArrayList<Item>();
        protected RequestManager mGlide;
        protected KeyValueSpec mKeyValueSpec;

        public KeyValueRecyclerAdapter(RequestManager glide, KeyValueSpec spec) {
            mGlide = glide;
            mKeyValueSpec = spec;
        }

        public void setItems(@Nullable List<Item> items) {
            mItems.clear();
            if (items != null) {
                mItems = items;
            }
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(
                ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            KeyValueCell view;
            if (mKeyValueSpec.getLayoutId() != 0){
                LayoutInflater inflater = LayoutInflater.from(context);
                view = (KeyValueCell) inflater.inflate(mKeyValueSpec.getLayoutId(), parent,
                        false);
            }else{
                view = new KeyValueCell(context);
            }
            if (view.getLines() != mKeyValueSpec.getLines()) {
                view.setLines(mKeyValueSpec.getLines());
            }
            if (view.isExpandable() != mKeyValueSpec.isExpandable()){
                view.setExpandable(mKeyValueSpec.isExpandable());
            }
            //only use async rendering when we need to recycle KeyValues
            //view.setAsyncRendering(mKeyValueSpec.getCounts() >20);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder,
                int position) {
            Item obj = mItems.get(position);
            KeyValueCell cell = holder.mKeyValueCell;

            //for espresso testing
            cell.setKey(mKeyValueSpec.getKeyPrefix() + " " + (position + 1));
            cell.setValue(obj.getValue());
            if (mKeyValueSpec.isClickable()){
                cell.setValueOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(@NonNull View v) {
                        Toast toast = Toast.makeText(v.getContext(),
                                CALL_TEXT + ((KeyValueCell)v).getValue(),
                                Toast.LENGTH_SHORT);
                        toast.show();

                    }
                });
            }
        }

        @Override
        public void onViewRecycled(ViewHolder holder) {
            //when an object cell is recycled, image may become text view, in which case the
            // clear won't be automatically called by glide
            if (holder.target != null) {
                mGlide.clear(holder.target);
            }
            super.onViewRecycled(holder);
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {

            public KeyValueCell mKeyValueCell;
            public Target target;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                if (itemView instanceof KeyValueCell){
                    mKeyValueCell = (KeyValueCell) itemView;
                }
            }
        }
    }

}
