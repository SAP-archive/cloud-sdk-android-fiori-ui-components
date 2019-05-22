package com.sap.cloud.mobile.fiori.demo.object;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.sap.cloud.mobile.fiori.demo.AbstractDemoActivity;
import com.sap.cloud.mobile.fiori.demo.R;
import com.sap.cloud.mobile.fiori.object.GridTableRow;

import java.text.NumberFormat;

public class GridTableActivity extends BaseObjectCellActivity {
    public static final String GRID_TABLE_ROW = "GridTableRow#";
    public static int COUNT = 300;
    private static final String TAG = "GridTableActivity";
    private ObjectCellSpec mObjectCellSpec;
    protected static int sHeaderColor;
    protected static int sHeaderBackgroundColor;
    protected static int sDataColumnColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sHeaderColor = getColor(R.color.sap_ui_content_label_color);
        sHeaderBackgroundColor = getColor(R.color.sap_ui_list_header_background);
        sDataColumnColor = getColor(R.color.sap_ui_base_text);
    }

    @Override
    protected ObjectCellSpec getObjectCellSpec() {
        if (mObjectCellSpec == null){
            mObjectCellSpec = new ObjectCellSpec(isTablet()?R.layout.grid_table:R.layout.object_cell_1, COUNT, 3, 1,
                    true,
                    true, true, true,
                    true/*to test whether it would be ignored*/, true, false);
        }
        return mObjectCellSpec;
    }

    @NonNull
    protected ObjectCellRecyclerAdapter createObjectCellRecyclerAdapter() {
        if (isTablet()) {
            return new GridTableRecyclerAdapter(this, Glide.with(this), getObjectCellSpec());
        }else{
            return super.createObjectCellRecyclerAdapter();
        }
    }

    public static class GridTableRecyclerAdapter extends ObjectCellRecyclerAdapter {

        private AbstractDemoActivity mActivity;
        static final int HEADER_TYPE = -1;
        static final int ITEM_TYPE = 0;
        private final float stuckHeaderElevation;
        private final float mImageHeight;
        private final NumberFormat mCurrencyFormatInstance = NumberFormat.getCurrencyInstance();

        public GridTableRecyclerAdapter(AbstractDemoActivity activity, RequestManager glide,
                ObjectCellSpec ocSpec) {
            super(glide, ocSpec);
            this.mActivity = activity;
            stuckHeaderElevation = activity.getResources().getDimension(R.dimen.card_elevation);
            mImageHeight = activity.getResources().getDimension(R.dimen.object_cell_image_size);
        }

        public boolean isTablet(){
            return mActivity.isTablet();
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
            //both header and content are using GridTableRow
            GridTableRow view = (GridTableRow) inflater.inflate(mObjectCellSpec.getLayoutId(), parent,
                    false);
            //image(""), headline, sub headline, description, status, status icon("")
            view.setPercentageOutOfRemainingWidth(true);
            //headline 40%, description would be 60%
            view.setColumnWidths(88, 0.4F, 150, -1, 150, 68);
            if (viewType == HEADER_TYPE){
                view.setId(R.id.grid_header_row);
                view.setHeader(true);
                view.setBackgroundColor(sHeaderBackgroundColor);
                view.setLines(1);
                TextView cImage = new TextView(context);
                cImage.setId(R.id.row_image);
                cImage.setText("");//no space to show header
                TextView cHeadline = new TextView(context);
                cHeadline.setId(R.id.row_headline);
                cHeadline.setText(R.string.grid_table_column_headline);
                cHeadline.setTextColor(sHeaderColor);
                TextView cSubHeadline = new TextView(context);
                cSubHeadline.setId(R.id.row_sub_headline);
                cSubHeadline.setText(R.string.grid_table_column_sub_headline);
                cSubHeadline.setTextColor(sHeaderColor);
                TextView cDescription = new TextView(context);
                cDescription.setId(R.id.row_description);
                cDescription.setText(R.string.grid_table_column_description);
                cDescription.setTextColor(sHeaderColor);
                TextView cPrice = new TextView(context);
                cPrice.setId(R.id.row_price);
                cPrice.setText(R.string.grid_table_column_price);
                cPrice.setGravity(Gravity.END);
                cPrice.setTextColor(sHeaderColor);
                TextView cStatus = new TextView(context);
                cStatus.setId(R.id.row_status);
                cStatus.setText("");
                view.setColumns(cImage, cHeadline, cSubHeadline, cDescription, cPrice, cStatus);
                return new HeaderViewHolder(view);
            }else{
                view.setLines(2);
                ImageView cImage = new ImageView(context);
                cImage.setId(R.id.row_image);
                TextView cHeadline = new TextView(context);
                cHeadline.setId(R.id.row_headline);
                cHeadline.setTextColor(sDataColumnColor);
                TextView cSubHeadline = new TextView(context);
                cSubHeadline.setId(R.id.row_sub_headline);
                cSubHeadline.setTextColor(sDataColumnColor);
                TextView cDescription = new TextView(context);
                cDescription.setId(R.id.row_description);
                cDescription.setTextColor(sDataColumnColor);
                TextView cPrice = new TextView(context);
                cPrice.setId(R.id.row_price);
                cPrice.setGravity(Gravity.END);
                cPrice.setTextColor(sDataColumnColor);
                ImageView cStatus = new ImageView(context);
                cStatus.setId(R.id.row_status);
                view.setColumns(cImage, cHeadline, cSubHeadline, cDescription, cPrice, cStatus);
                //layout params will be created after setColumns
                GridTableRow.LayoutParams layoutParams = (GridTableRow.LayoutParams)cStatus.getLayoutParams();
                //top align
                layoutParams.verticalBias = 0.0F;

                return new RowViewHolder(view);
            }
        }

        @Override
        public void onBindViewHolder(ViewHolder holder,
                int position) {
            if (position > 0){
                GridTableRow row = (GridTableRow) holder.itemView;
                BizObject obj = mObjects.get(position-1);

                //for espresso testing
                row.setContentDescription(GRID_TABLE_ROW +obj.getId());

                ImageView cImage = null;
                if (mObjectCellSpec.hasDetailImage()) {
                    cImage = row.findViewById(R.id.row_image);
                    if (obj.getDetailImageResId() != 0) {
                        cImage.setImageResource(obj.getDetailImageResId());
                    } else if (obj.getDetailImageUri() != null) {
                        RequestOptions cropOptions = new RequestOptions().placeholder(
                                R.drawable.rectangle);
                        holder.target = mGlide.load(obj.getDetailImageUri()).apply(
                                cropOptions).into(cImage);
                    } else {
                        cImage.setImageResource(R.drawable.object_placeholder);
                    }
                    cImage.setContentDescription("Row image");
                }
                TextView cHeadline = holder.itemView.findViewById(R.id.row_headline);
                cHeadline.setText(obj.getHeadline());

                TextView cSubHeadline = holder.itemView.findViewById(R.id.row_sub_headline);
                cSubHeadline.setText(obj.getSubHeadline());

                TextView cDescription = holder.itemView.findViewById(R.id.row_description);
                cDescription.setText(obj.getDescription());

                ImageView cStatus = holder.itemView.findViewById(R.id.row_status);
                cStatus.setColorFilter(null);
                int statusDescId = android.R.string.ok;
                if (obj.getStatusId() == R.drawable.ic_error_black_24dp){
                    statusDescId = R.string.error;
                    cStatus.setColorFilter(BaseObjectCellActivity.sSapUiNegativeText);
                }else if (obj.getStatusId() == R.drawable.ic_warning_black_24dp){
                    statusDescId = R.string.warning;
                }
                cStatus.setImageResource(obj.getStatusId());
                cStatus.setContentDescription(mActivity.getResources().getString(statusDescId));

                TextView cPrice = holder.itemView.findViewById(R.id.row_price);
                cPrice.setText(mCurrencyFormatInstance.format(obj.getPrice()));
                //don't set columns again, which is bad for performance
                //row.setColumns(cImage, cHeadline, cSubHeadline, cDescription, cPrice, cStatus);

                row.invalidate();
                row.requestLayout();
            }
        }

        public static class HeaderViewHolder extends ViewHolder {
            public HeaderViewHolder(View itemView) {
                super(itemView);
            }
        }

        public static class RowViewHolder extends ViewHolder {
            public RowViewHolder(View itemView) {
                super(itemView);
            }
        }

    }
}
