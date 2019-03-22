package com.sap.cloud.mobile.fiori.demo.object;

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
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.sap.cloud.mobile.fiori.demo.AbstractDemoActivity;
import com.sap.cloud.mobile.fiori.demo.R;
import com.sap.cloud.mobile.fiori.object.KeylineDividerItemDecoration;
import com.sap.cloud.mobile.fiori.object.ObjectCell;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BaseObjectCellActivity extends AbstractDemoActivity implements
        LoaderManager.LoaderCallbacks<List<BizObject>> {
    private static final String TAG = "BaseObjectCellActivity";

    public static final int NUM_BIZ_OBJECTS = 300;

    protected ObjectCellRecyclerAdapter mAdapter;
    protected RecyclerView mRecyclerView;
    private ConstraintLayout mConstraintLayout;
    private ProgressBar mProgressBar;
    private ObjectCellSpec mObjectCellSpec;
    protected static int sSapUiFieldActiveBorderColor;
    protected static int sSapUiNegativeText;
    protected static int sSapUiCriticalText;
    protected static int sSapUiPositiveText;
    protected static int sSapUiNeutralText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.content_base_object_cell);

        mProgressBar = findViewById(R.id.progress);
        mConstraintLayout = findViewById(R.id.my_constraint);

        sSapUiFieldActiveBorderColor = getColor(R.color.sap_ui_field_active_border_color);
        sSapUiNegativeText = getColor(R.color.sap_ui_negative_text);
        sSapUiPositiveText = getColor(R.color.sap_ui_positive_text);
        sSapUiCriticalText = getColor(R.color.sap_ui_critical_text);
        sSapUiNeutralText = getColor(R.color.sap_ui_neutral_text);

        mAdapter = createObjectCellRecyclerAdapter();
        mRecyclerView = findViewById(R.id.my_recycler_view);
        configureRecyclerView();
        getLoaderManager().initLoader(0, null, this);
    }

    protected void configureRecyclerView() {
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(
                new KeylineDividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    @NonNull
    protected ObjectCellRecyclerAdapter createObjectCellRecyclerAdapter() {
        return new ObjectCellRecyclerAdapter(Glide.with(this), getObjectCellSpec());
    }

    /**
     * Subclasses need to override this method to specify how the object_placeholder cells are to be rendered.
     */
    protected ObjectCellSpec getObjectCellSpec() {
        if (mObjectCellSpec == null) {
            mObjectCellSpec = new ObjectCellSpec(R.layout.object_cell_1, NUM_BIZ_OBJECTS, 3, 1,
                    true,
                    true, true, true,
                    false, true, true);
        }
        return mObjectCellSpec;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @NonNull
    @Override
    public Loader<List<BizObject>> onCreateLoader(int id, Bundle args) {
        return new BizObjectLoader(getBaseContext(), getObjectCellSpec());
    }

    @Override
    public void onLoadFinished(Loader<List<BizObject>> loader, List<BizObject> data) {
        Log.d(TAG, "onLoadFinished");
        mAdapter.setObjects(data);
        // The list should now be shown.
        mProgressBar.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
        ObjectAnimator animation = ObjectAnimator.ofFloat(mRecyclerView, "alpha", 0, 100);
        animation.setDuration(1000);
        animation.start();

        mConstraintLayout.postInvalidate();
    }

    @Override
    public void onLoaderReset(Loader<List<BizObject>> loader) {
        mAdapter.setObjects(null);
    }

    public static class BizObjectLoader extends AsyncTaskLoader<List<BizObject>> {
        private ObjectCellSpec mObjectCellSpec;

        public BizObjectLoader(Context context, ObjectCellSpec objectCellSpec) {
            super(context);
            mObjectCellSpec = objectCellSpec;
        }

        @Override
        protected void onStartLoading() {
            forceLoad();
        }

        @NonNull
        @Override
        public List<BizObject> loadInBackground() {
            List<BizObject> objects = BizObject.createBizObjectsList(mObjectCellSpec);
            return objects;
        }
    }

    public static class ObjectCellRecyclerAdapter extends
            RecyclerView.Adapter<ObjectCellRecyclerAdapter.ViewHolder> {
        private static final String TAG = "ObjectCellRecyclerAdapter";

        @Nullable
        protected List<BizObject> mObjects = new ArrayList<BizObject>();
        protected RequestManager mGlide;
        protected ObjectCellSpec mObjectCellSpec;

        public ObjectCellRecyclerAdapter(RequestManager glide, ObjectCellSpec ocSpec) {
            mGlide = glide;
            mObjectCellSpec = ocSpec;
        }

        public void setObjects(@Nullable List<BizObject> objects) {
            mObjects.clear();
            if (objects != null) {
                mObjects = objects;
            }
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(
                ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            ObjectCell view;
            if (mObjectCellSpec.getLayoutId() != 0) {
                LayoutInflater inflater = LayoutInflater.from(context);
                view = (ObjectCell) inflater.inflate(mObjectCellSpec.getLayoutId(), parent,
                        false);
            } else {
                view = new ObjectCell(context);
            }
            if (view.getLines() != mObjectCellSpec.getLines()) {
                view.setLines(mObjectCellSpec.getLines());
            }
            if (view.getHeadlineLines() != mObjectCellSpec.getHeadlineLines()) {
                view.setHeadlineLines(mObjectCellSpec.getHeadlineLines());
            }
            view.setPreserveIconImageContainer(mObjectCellSpec.hasContainerLayout());
            view.setPreserveDescriptionSpacing(!mObjectCellSpec.getHideEmptyDescription());
            //only use async rendering when we need to recycle ObjectCells
            //view.setAsyncRendering(mObjectCellSpec.getCounts() >20);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder,
                                     int position) {
            BizObject obj = mObjects.get(position);


            ObjectCell cell = holder.objectCell;
            //for espresso testing
            cell.setContentDescription("ObjectCell#" + obj.getId());

            Random random = new Random();
            if (mObjectCellSpec.hasIconStack() && (!mObjectCellSpec.hasContainerLayout() || random.nextBoolean())){
                cell.clearIcons();
                int baseIndex = 0;
                if (mObjectCellSpec.shouldShuffleIconStack()) {
                    baseIndex = position % 3;
                }
                for (int i = 0; i < 3; i++) {
                    if (baseIndex == i) {
                        cell.setIcon("" + obj.getPendingTasks(), 0);

//                        if (obj.getPendingTasks()>200){
//                            cell.setIconColor(BaseObjectCellActivity.sSapUiNegativeText, i);
//                        }
                    } else if ((baseIndex + 1) % 3 == i) {
                        cell.setIcon(R.drawable.ic_dot_black_12dp, i, R.string.object_cell_icon_attention_desc);
//                        if (obj.isNeedsAttention()){
//                            cell.setIconColor(BaseObjectCellActivity.sSapUiNegativeText, i);
//                        }
                    } else {
                        if (obj.isProtected()) {
                            cell.setIcon(R.drawable.ic_lock_outline_black_24dp, i, R.string.object_cell_icon_protection_desc);
                        } else {
                            cell.setIcon(R.drawable.ic_lock_open_black_24dp, i, R.string.object_cell_icon_not_protection_desc);
                        }
                    }
                }
            }

            if (mObjectCellSpec.hasDetailImage() && (!mObjectCellSpec.hasContainerLayout() || random.nextBoolean())) {

                if (obj.getDetailImageResId() != 0) {
                    cell.setDetailImageCharacter(null);
                    //Glide won't improve vector image performance. Basically it's the same as: cell
                    // .setDetailImage(obj.getDetailImageResId()); Also circleCrop won't work.
                    //This is just to demonstrate how to use local vector resource with Glide
//                mGlide.load(null).apply(cropOptions.fallback(obj.getDetailImageResId())).into(
//                        cell.prepareDetailImageView());
                    cell.setDetailImage(obj.getDetailImageResId());
                    cell.setDetailImageDescription(R.string.avatar);
                } else if (obj.getDetailImageUri() != null) {
                    RequestOptions cropOptions = new RequestOptions().placeholder(
                            R.drawable.rectangle);
                    cell.setDetailImageCharacter(null);
                    holder.target = mGlide.load(obj.getDetailImageUri()).apply(cropOptions).into(
                            cell.prepareDetailImageView());
                    cell.setDetailImageDescription(R.string.avatar);
                } else {
                    cell.setDetailImage(null);
                    if (TextUtils.isEmpty(obj.getSubHeadline())){
                        cell.setDetailImageCharacter("?");
                    }else{
                        cell.setDetailImageCharacter(obj.getSubHeadline().substring(0, 1));
                    }
                }
            } else {
                cell.setPreserveDetailImageSpacing(false);

            }

            if (cell.getHeadlineLines() > 1) {
                cell.setHeadline(obj.getHeadline() + " " + obj.getFootnote());
            } else {
                cell.setHeadline(obj.getHeadline());
            }
            if (mObjectCellSpec.hasSubheadline()) {
                cell.setSubheadline(obj.getSubHeadline());
            }
            if (mObjectCellSpec.hasFootnote()) {
                cell.setFootnote(obj.getFootnote());
            }

            cell.setDescription(obj.getDescription());
            cell.clearStatuses();
            boolean shuffleStatus = mObjectCellSpec.isShuffleStatus() && position % 2 == 1;
            if (shuffleStatus) {
                int index = 0;
                if (mObjectCellSpec.hasSecondStatus()) {
                    int statusDescId = android.R.string.ok;

                    if (obj.getStatusId() == R.drawable.ic_error_black_24dp) {
                        statusDescId = R.string.error;
                        cell.setStatusColor(BaseObjectCellActivity.sSapUiNegativeText, index);
                    } else if (obj.getStatusId() == R.drawable.ic_warning_black_24dp) {
                        statusDescId = R.string.warning;
                        cell.setStatusColor(BaseObjectCellActivity.sSapUiCriticalText, index);
                    } else {
                        cell.setStatusColor(BaseObjectCellActivity.sSapUiPositiveText, index);
                    }
                    cell.setStatus(obj.getStatusId(), index, statusDescId);
                    index++;
                }
                if (mObjectCellSpec.hasFirstStatus()) {
                    if (mObjectCellSpec.isDynamicStatus()){
                        cell.setDynamicStatusWidth(true);
                        cell.setStatus(obj.getInfo(), index);
                    }else {
                        cell.setStatus(obj.getPriority().toString(), index);
                        if (obj.getPriority() == Priority.HIGH) {
                            cell.setStatusColor(BaseObjectCellActivity.sSapUiNegativeText, index);
                        } else {
                            cell.setStatusColor(cell.getDefaultStatusColor(), index);
                        }
                    }
                }

            } else {
                if (mObjectCellSpec.hasFirstStatus()) {
                    if (mObjectCellSpec.isDynamicStatus()){
                        cell.setDynamicStatusWidth(true);
                        cell.setStatus(obj.getInfo(), 0);
                    }else {
                        cell.setStatus(obj.getPriority().toString(), 0);
                        if (obj.getPriority() == Priority.HIGH) {
                            cell.setStatusColor(BaseObjectCellActivity.sSapUiNegativeText, 0);
                        } else {
                            cell.setStatusColor(cell.getDefaultStatusColor(), 0);
                        }
                    }
                }
                if (mObjectCellSpec.hasSecondStatus()) {
                    int statusDescId = android.R.string.ok;

                    if (obj.getStatusId() == R.drawable.ic_error_black_24dp) {
                        statusDescId = R.string.error;
                        //colors of other 2 icons are set in drawble
                        cell.setStatusColor(BaseObjectCellActivity.sSapUiNegativeText, 1);
                    } else if (obj.getStatusId() == R.drawable.ic_warning_black_24dp) {
                        statusDescId = R.string.warning;
                        cell.setStatusColor(BaseObjectCellActivity.sSapUiCriticalText, 1);
                    } else {
                        cell.setStatusColor(BaseObjectCellActivity.sSapUiPositiveText, 1);
                    }

                    cell.setStatus(obj.getStatusId(), 1, statusDescId);
                }
            }
            if (mObjectCellSpec.hasAction()) {
                cell.setSecondaryActionIcon(R.drawable.ic_cloud_download_black_24dp);
                cell.setSecondaryActionIconDescription(R.string.download);
                cell.setSecondaryActionOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(@NonNull View v) {
                        ObjectCell parent = (ObjectCell) v.getParent();
                        Toast toast = Toast.makeText(v.getContext(),
                                "Item: " + parent.getHeadline() + " is clicked.",
                                Toast.LENGTH_SHORT);
                        toast.show();
                    }
                });
            }
        }

        @Override
        public void onViewRecycled(ViewHolder holder) {
            //when an object_placeholder cell is recycled, image may become text view, in which case the
            // clear won't be automatically called by glide
            if (holder.target != null) {
                mGlide.clear(holder.target);
            }
            super.onViewRecycled(holder);
        }

        @Override
        public int getItemCount() {
            return mObjects.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {

            public ObjectCell objectCell;
            public Target target;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                if (itemView instanceof ObjectCell) {
                    objectCell = (ObjectCell) itemView;
                }
            }
        }
    }

}
