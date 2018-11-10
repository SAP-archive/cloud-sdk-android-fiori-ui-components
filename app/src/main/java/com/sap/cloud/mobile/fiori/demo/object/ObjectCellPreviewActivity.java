package com.sap.cloud.mobile.fiori.demo.object;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.target.Target;
import com.sap.cloud.mobile.fiori.demo.ApiDemos;
import com.sap.cloud.mobile.fiori.demo.R;
import com.sap.cloud.mobile.fiori.object.ObjectCell;

public class ObjectCellPreviewActivity extends BaseObjectCellActivity {
    private static final String TAG = "ObjectCellPreviewActivity";
    private ObjectCellSpec mObjectCellSpec;
    @Override
    protected ObjectCellSpec getObjectCellSpec() {
        if (mObjectCellSpec == null){
            if (isTablet()){
                //on tablet we use multiple-column layout which does not have image and description
                mObjectCellSpec = new ObjectCellSpec(R.layout.object_cell_4, 4, 3, 1,
                        true,
                        true, false, false,
                        false, false, false);
            }else {
                mObjectCellSpec = new ObjectCellSpec(R.layout.object_cell_preview, 4, 3, 1,
                        true,
                        true, true, true,
                        true/*to test whether it would be ignored*/, true, false);
            }
        }
        return mObjectCellSpec;
    }

    @Override
    protected void configureRecyclerView() {
        if (isTablet()){
            mRecyclerView.setAdapter(mAdapter);

            GridLayoutManager manager = new GridLayoutManager(this, 2);
            manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    //header and footer use whole width
                    int itemType = mAdapter.getItemViewType(position);
                    if ( itemType == ObjectCellPreviewRecyclerAdapter.HEADER_TYPE || itemType == ObjectCellPreviewRecyclerAdapter.FOOTER_TYPE){
                        return 2;
                    }
                    return 1;
                }
            });
            mRecyclerView.setLayoutManager(manager);
            mRecyclerView.setHasFixedSize(true);
//            mRecyclerView.addItemDecoration(
//                    new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        }else {
            super.configureRecyclerView();
        }
    }

    @NonNull
    protected ObjectCellRecyclerAdapter createObjectCellRecyclerAdapter() {
        return new ObjectCellPreviewRecyclerAdapter(this, Glide.with(this), getObjectCellSpec());
    }

    public static class ObjectCellPreviewRecyclerAdapter extends ObjectCellRecyclerAdapter{

        private Activity mActivity;
        static final int HEADER_TYPE = -1;
        static final int ITEM_TYPE = 0;
        static final int FOOTER_TYPE = 1;

        public ObjectCellPreviewRecyclerAdapter(Activity activity, RequestManager glide,
                ObjectCellSpec ocSpec) {
            super(glide, ocSpec);
            this.mActivity = activity;
        }

        @Override
        public int getItemCount() {
            //including header and footer
            return mObjects.size() + 2;
        }

        @Override
        public int getItemViewType(int position) {
            if (position == 0){
                return HEADER_TYPE;
            }else if (position == (getItemCount()-1)){
                return FOOTER_TYPE;
            }
            return ITEM_TYPE;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(
                ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            View view;
            if (viewType == HEADER_TYPE){
                view = inflater.inflate(R.layout.object_cell_preview_header, parent,
                        false);
                return new HeaderViewHolder(view);
            }else if (viewType == FOOTER_TYPE){
                view = inflater.inflate(R.layout.object_cell_preview_footer, parent,
                        false);
                return new FooterViewHolder(view);
            }
            return super.onCreateViewHolder(parent, viewType);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder,
                int position) {

            if (position == 0){
                TextView headerText = holder.itemView.findViewById(R.id.headerText);
                headerText.setText(R.string.objectcell_header);
            }else if (position == (getItemCount()-1)){
                //TextView footerText = holder.itemView.findViewById(R.id.footerText);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setClass(mActivity, BaseObjectCellActivity.class);
                        mActivity.startActivity(intent);
                    }
                });

            }else{
                super.onBindViewHolder(holder, position - 1);
            }
        }

        public static class HeaderViewHolder extends ViewHolder {
            public HeaderViewHolder(@NonNull View itemView) {
                super(itemView);
            }
        }

        public static class FooterViewHolder extends ViewHolder {
            public FooterViewHolder(@NonNull View itemView) {
                super(itemView);
            }
        }
    }
}
