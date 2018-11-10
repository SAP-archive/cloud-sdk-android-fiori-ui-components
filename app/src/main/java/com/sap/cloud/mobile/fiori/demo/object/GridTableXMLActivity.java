package com.sap.cloud.mobile.fiori.demo.object;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.sap.cloud.mobile.fiori.demo.AbstractDemoActivity;
import com.sap.cloud.mobile.fiori.demo.R;
import com.sap.cloud.mobile.fiori.object.GridTableRow;

public class GridTableXMLActivity extends GridTableActivity {
    private static final String TAG = "GridTableXMLActivity";
    private ObjectCellSpec mObjectCellSpec;
    @Override
    protected ObjectCellSpec getObjectCellSpec() {
        if (mObjectCellSpec == null){
            mObjectCellSpec = new ObjectCellSpec(isTablet()?R.layout.grid_table_columns:R.layout.object_cell_1, 300, 3, 1,
                    true,
                    true, true, true,
                    true, true, false);
        }
        return mObjectCellSpec;
    }

    @NonNull
    protected ObjectCellRecyclerAdapter createObjectCellRecyclerAdapter() {
        if (isTablet()) {
            return new GridTableXMLRecyclerAdapter(this, Glide.with(this), getObjectCellSpec());
        }else{
            return super.createObjectCellRecyclerAdapter();
        }
    }


    @Override
    protected void configureRecyclerView() {
        if (isTablet()) {
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            /*
             * If you want to use StickyHeaders in your project, here is a link to the github
             * repository: https://github.com/Doist/RecyclerViewExtensions/tree/master/StickyHeaders
             * and replace the LinearLayoutManager line above with the commented line below
             */
//            mRecyclerView.setLayoutManager(new StickyHeadersLinearLayoutManager<GridTableRecyclerAdapter>(this));
            mRecyclerView.setHasFixedSize(true);
            mRecyclerView.addItemDecoration(
                    new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        }else {
            super.configureRecyclerView();
        }
    }

    public static class GridTableXMLRecyclerAdapter extends GridTableRecyclerAdapter{

        public GridTableXMLRecyclerAdapter(@NonNull AbstractDemoActivity activity, RequestManager glide,
                                           ObjectCellSpec ocSpec) {
            super(activity, glide, ocSpec);
        }

        @Override
        public int getItemCount() {
            //including header
            return mObjects.size() + 1;
        }

        @Override
        public int getItemViewType(int position) {
            if (position == 0){
                return HEADER_TYPE;
            }
            return ITEM_TYPE;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(
                ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            if (viewType == HEADER_TYPE){
                GridTableRow view = (GridTableRow) inflater.inflate(R.layout.grid_table_header, parent,
                        false);
                return new GridTableRecyclerAdapter.HeaderViewHolder(view);
            }else{
                GridTableRow view = (GridTableRow) inflater.inflate(mObjectCellSpec.getLayoutId(), parent,
                        false);
                return new GridTableRecyclerAdapter.RowViewHolder(view);
            }
        }

    }
}
