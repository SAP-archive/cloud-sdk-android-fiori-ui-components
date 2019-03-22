package com.sap.cloud.mobile.fiori.demo.object;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextPaint;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.sap.cloud.mobile.fiori.common.Utility;
import com.sap.cloud.mobile.fiori.demo.AbstractDemoActivity;
import com.sap.cloud.mobile.fiori.demo.R;
import com.sap.cloud.mobile.fiori.object.GridObjectCell;
import com.sap.cloud.mobile.fiori.object.GridTableRow;
import com.sap.cloud.mobile.fiori.object.ObjectCell;

import java.text.NumberFormat;


public class GridObjectCellActivity extends BaseObjectCellActivity {
    private static final String TAG = "GridObjectCellActivity";
    private ObjectCellSpec mObjectCellSpec;
    public static final String GRID_TABLE_ROW = "GridObjectCell#";
    public static int COUNT = 300;
    protected static int sHeaderColor;
    protected static int sHeaderBackgroundColor;
    protected static int sDataColumnColor;

    @Override
    protected ObjectCellSpec getObjectCellSpec() {
        if (mObjectCellSpec == null){
            mObjectCellSpec = new ObjectCellSpec(R.layout.grid_table_object_cell, 300, 3, 1,
                    true,
                    true, true, true,
                    true, true, false);
        }
        return mObjectCellSpec;
    }

    @NonNull
    protected ObjectCellRecyclerAdapter createObjectCellRecyclerAdapter() {
        return new GridObjectCellRecyclerAdapter(this, Glide.with(this), getObjectCellSpec());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sHeaderColor = getColor(R.color.sap_ui_content_label_color);
        sHeaderBackgroundColor = getColor(R.color.sap_ui_list_header_background);
        sDataColumnColor = getColor(R.color.sap_ui_base_text);
    }

    @Override
    protected void configureRecyclerView() {
        if (isTablet()) {
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            mRecyclerView.setHasFixedSize(true);
            mRecyclerView.addItemDecoration(
                    new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        }else {
            super.configureRecyclerView();
        }
    }

    public static class GridObjectCellRecyclerAdapter extends ObjectCellRecyclerAdapter{

        private AbstractDemoActivity mActivity;
        static final int HEADER_TYPE = -1;
        static final int ITEM_TYPE = 0;
        private final float stuckHeaderElevation;
        private final NumberFormat mCurrencyFormatInstance = NumberFormat.getCurrencyInstance();
        private View.OnClickListener mListener;
        private TextPaint mPricePaint;

        public GridObjectCellRecyclerAdapter(AbstractDemoActivity activity, RequestManager glide,
                ObjectCellSpec ocSpec) {
            super(glide, ocSpec);
            this.mActivity = activity;
            stuckHeaderElevation = activity.getResources().getDimension(R.dimen.card_elevation);
            mListener = new View.OnClickListener() {
                @Override
                public void onClick(@NonNull View v) {
                    CharSequence headline = "";
                    ViewParent parent = v.getParent();
                    if (parent instanceof ObjectCell) {
                        headline = ((ObjectCell) parent).getHeadline();
                    } else if (parent instanceof GridTableRow) {
                        headline = ((TextView) ((GridTableRow) parent).getColumn(1)).getText();
                    }
                    Toast toast = Toast.makeText(v.getContext(),
                            "Item: " + headline + " is clicked.",
                            Toast.LENGTH_SHORT);
                    toast.show();

                }
            };
            mCurrencyFormatInstance.setMaximumFractionDigits(0);
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
            //both header and content are using GridObjectCell
            GridObjectCell view = (GridObjectCell) inflater.inflate(mObjectCellSpec.getLayoutId(), parent,
                    false);
            //image(""), headline, sub headline, description, status, status icon("")

            //headline 40%, description would be 60%
            view.setColumnWidths(88, 0.4F, 150, -1, 150, 68, 68);
            if (viewType == HEADER_TYPE){
                if (!mActivity.isTablet()){
                    View header = inflater.inflate(R.layout.object_cell_preview_header, parent,
                            false);
                    TextView title = header.findViewById(R.id.headerText);
                    title.setText(R.string.grid_objectcell_header);
                    return new HeaderViewHolder(header);
                }
                view.setHeader(true);
                view.setColumnTypes(TextView.class, TextView.class, TextView.class, TextView.class, TextView.class, TextView.class, TextView.class);
                view.setId(R.id.grid_header_row);
                view.setBackgroundColor(sHeaderBackgroundColor);
                view.setLines(1);
                String cImage = "";
                String cHeadline = mActivity.getResources().getString(R.string.grid_table_column_headline);
                String cSubHeadline = mActivity.getResources().getString(R.string.grid_table_column_sub_headline);
                String cDescription = mActivity.getResources().getString(R.string.grid_table_column_description);
                String cPrice = mActivity.getResources().getString(R.string.grid_table_column_price);
                String cStatus = "";
                String cAction = "";
                view.setContents(cImage, cHeadline, cSubHeadline, cDescription, cPrice, cStatus, cAction);
                TextPaint textPaint = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
                textPaint.setTypeface(Utility.getFioriTypeface(Typeface.NORMAL));
                textPaint.setColor(sHeaderColor);
                textPaint.setTextSize(context.getResources().getDimension(R.dimen.grid_table_column_default_header_text_size));
                view.setTextPaint(1, textPaint);
                view.setTextPaint(2, textPaint);
                view.setTextPaint(3, textPaint);
                view.setTextPaint(4, textPaint);
                if (mActivity.isTablet()){
                    View priceView = view.getGridTableRow().getColumn(4);
                    if (priceView != null && priceView instanceof TextView){
                        ((TextView)priceView).setGravity(Gravity.END);
                    }
                }
                return new HeaderViewHolder(view);
            }else{
                view.setLines(2);
                view.setColumnTypes(ImageView.class, TextView.class, TextView.class, TextView.class, TextView.class, ImageView.class, ImageView.class);
                Drawable cImage = AppCompatResources.getDrawable(context, R.drawable.rectangle);
                Drawable cStatus = AppCompatResources.getDrawable(context, R.drawable.rectangle);
                view.setContents(cImage, " ", " ", " ", " ", cStatus, R.drawable.ic_cloud_download_black_24dp);
                //these are default values for ObjectCell, we set them again to make sure table row also gets the same style
                TextPaint headlinePaint = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
                headlinePaint.setTypeface(Utility.getFioriTypeface(Typeface.BOLD));
                headlinePaint.setColor(context.getColor(R.color.material_grey_900));
                headlinePaint.setTextSize(context.getResources().getDimension(R.dimen.object_cell_primary_font_size));
                view.setTextPaint(1, headlinePaint);

                TextPaint subheadlinePaint = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
                subheadlinePaint.setTypeface(Utility.getFioriTypeface(Typeface.NORMAL));
                subheadlinePaint.setColor(context.getColor(R.color.primary_text_default_material_light));
                subheadlinePaint.setTextSize(context.getResources().getDimension(R.dimen.object_cell_primary_font_size));
                view.setTextPaint(2, subheadlinePaint);

                TextPaint descPaint = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
                descPaint.setTypeface(Utility.getFioriTypeface(Typeface.NORMAL));
                descPaint.setColor(context.getColor(R.color.secondary_text_default_material_light));
                descPaint.setTextSize(context.getResources().getDimension(R.dimen.object_cell_secondary_font_size));
                view.setTextPaint(3, descPaint);

                if (mPricePaint == null){
                    //save it for reset
                    mPricePaint = view.getTextPaint(4);
                }
                //device-type specific enhancements
                //layout params will be created after setContents
                if (mActivity.isTablet()) {
                    View priceView = view.getGridTableRow().getColumn(4);
                    if (priceView != null && priceView instanceof TextView){
                        ((TextView)priceView).setGravity(Gravity.END);
                    }
                    View statusView = view.getGridTableRow().getColumn(5);
                    if (statusView != null) {
                        GridTableRow.LayoutParams layoutParams =
                                (GridTableRow.LayoutParams) statusView.getLayoutParams();
                        //top align
                        layoutParams.verticalBias = 0.0F;
                    }
                    View actionView = view.getGridTableRow().getColumn(6);
                    if (actionView != null) {
                        GridTableRow.LayoutParams layoutParams =
                                (GridTableRow.LayoutParams) actionView.getLayoutParams();
                        //top align
                        layoutParams.verticalBias = 0.0F;
                    }
                }else{
                    //set a bigger width to accommodate large number
                    view.getObjectCell().setStatusWidth(Utility.dpToPx(65));
                }
                return new RowViewHolder(view);
            }
        }

        @Override
        public void onBindViewHolder(ViewHolder holder,
                int position) {
            if (position > 0){
                GridObjectCell row = (GridObjectCell) holder.itemView;
                BizObject obj = mObjects.get(position-1);

                //for espresso testing
                row.setContentDescription(GRID_TABLE_ROW +obj.getId());

                Drawable cImage = null;
                if (mObjectCellSpec.hasDetailImage()) {
                    if (obj.getDetailImageResId() != 0) {
                        cImage = AppCompatResources.getDrawable(mActivity, obj.getDetailImageResId());
                    } else if (obj.getDetailImageUri() != null) {
                        //async loading must be put after setContents to avoid timing contention
                    }else{
                        cImage = AppCompatResources.getDrawable(mActivity, R.drawable.object_placeholder);
                    }
                }
                CharSequence cHeadline = obj.getHeadline();
                CharSequence cSubHeadline = obj.getSubHeadline();
                CharSequence cDescription = obj.getDescription();
                Drawable cStatus = AppCompatResources.getDrawable(mActivity, obj.getStatusId());
                cStatus.setColorFilter(null);
                if (obj.getStatusId() == R.drawable.ic_error_black_24dp){
                    cStatus.setColorFilter(BaseObjectCellActivity.sSapUiNegativeText, PorterDuff.Mode.SRC_IN);
                }

                CharSequence cPrice = mCurrencyFormatInstance.format(obj.getPrice());
                row.setSecondaryActionOnClickListener(mListener);
                row.setContents(cImage, cHeadline, cSubHeadline, cDescription, cPrice, cStatus, null);

                //status will be cleared and recreated during setContent, so setTextPaint here
                if (obj.getPrice()>50000){
                    TextPaint highPricePaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
                    highPricePaint.set(mPricePaint);
                    highPricePaint.setColor(Color.RED);
                    row.setTextPaint(4, highPricePaint);
                }else{
                    row.setTextPaint(4, mPricePaint);
                }

                if (obj.getDetailImageUri() != null){
                    ImageView imageView = null;
                    if (mActivity.isTablet()){
                        imageView = (ImageView)row.getGridTableRow().getColumn(0);
                    }else{
                        imageView = row.getObjectCell().prepareDetailImageView();
                    }
                    RequestOptions cropOptions = new RequestOptions().placeholder(
                            R.drawable.object_placeholder);
                    holder.target = mGlide.load(obj.getDetailImageUri()).apply(
                            cropOptions).into(imageView);
                }
            }
        }


        public static class HeaderViewHolder extends ViewHolder {
            public HeaderViewHolder(@NonNull View itemView) {
                super(itemView);
            }
        }

        public static class RowViewHolder extends ViewHolder {
            public RowViewHolder(@NonNull View itemView) {
                super(itemView);
            }
        }

    }

}
