package com.sap.cloud.mobile.fiori.demo.formcell;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Loader;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.sap.cloud.mobile.fiori.demo.R;
import com.sap.cloud.mobile.fiori.demo.object.BizObject;
import com.sap.cloud.mobile.fiori.demo.object.ObjectCellSpec;
import com.sap.cloud.mobile.fiori.demo.object.Priority;
import com.sap.cloud.mobile.fiori.formcell.GenericListPickerFormCellActivity;
import com.sap.cloud.mobile.fiori.object.ObjectCell;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GenericObjectCellPickerActivity extends GenericListPickerFormCellActivity<ObjectCell, Integer> implements
        LoaderManager.LoaderCallbacks<List<BizObject>> {
    private static final String TAG = "BaseObjectCellActivity";

    public static final int NUM_BIZ_OBJECTS = 300;

    private ObjectCellSpec mObjectCellSpec;

    @NonNull
    private List<BizObject> mData = new ArrayList<>();

    /**
     * Subclasses need to override this method to specify how the object cells are to be rendered.
     */
    protected ObjectCellSpec getObjectCellSpec() {
        if (mObjectCellSpec == null) {
            mObjectCellSpec = new ObjectCellSpec(R.layout.object_cell_1, NUM_BIZ_OBJECTS, 2, 1,
                    false,
                    true, false, false,
                    false, true, false);
            mObjectCellSpec.setShuffleIconStack(false);
            mObjectCellSpec.setShuffleStatus(true);
            mObjectCellSpec.setHideEmptyDescription(true);
        }
        return mObjectCellSpec;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLoaderManager().initLoader(0, null, this);
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
    public void onLoadFinished(Loader<List<BizObject>> loader, @NonNull List<BizObject> data) {
        Log.d(TAG, "onLoadFinished");
        mData.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    protected int getSelectorGravity() {
        return Gravity.CENTER_VERTICAL;
    }

    @Override
    public void onLoaderReset(Loader<List<BizObject>> loader) {
        mData.clear();
    }

    @NonNull
    @Override
    protected ObjectCell onCreateView(int viewType, @NonNull Context context) {
        ObjectCell view = new ObjectCell(context);
        mObjectCellSpec = getObjectCellSpec();
        if (view.getLines() != mObjectCellSpec.getLines()) {
            view.setLines(mObjectCellSpec.getLines());
        }
        if (view.getHeadlineLines() != mObjectCellSpec.getHeadlineLines()) {
            view.setHeadlineLines(mObjectCellSpec.getHeadlineLines());
        }
        view.setPreserveIconImageContainer(mObjectCellSpec.hasContainerLayout());
        view.setPreserveDescriptionSpacing(!mObjectCellSpec.getHideEmptyDescription());
        // Default top and bottom padding on ObjectCell are 4dp and 2dp respectively. Make sure that top padding is also 2dp
        view.setPadding(0, view.getPaddingBottom(), 0, view.getPaddingBottom());
        return view;
    }

    @Override
    protected void onBindView(@NonNull ObjectCell cell, Integer position) {
        int sapUiNegativeText = cell.getContext().getColor(R.color.sap_ui_negative_text);
        int sapUiPositiveText = cell.getContext().getColor(R.color.sap_ui_positive_text);
        int sapUiCriticalText = cell.getContext().getColor(R.color.sap_ui_critical_text);
        if (!mData.isEmpty()) {
            BizObject obj = mData.get(position);

            //for espresso testing
            cell.setContentDescription("ObjectCell#" + obj.getId());

            Random random = new Random();
            if (mObjectCellSpec.hasIconStack() && (!mObjectCellSpec.hasContainerLayout() || random.nextBoolean())) {
                cell.clearIcons();
                int baseIndex = 0;
                if (mObjectCellSpec.shouldShuffleIconStack()) {
                    baseIndex = position % 3;
                }
                for (int i = 0; i < 3; i++) {
                    if (baseIndex == i) {
                        cell.setIcon("" + obj.getPendingTasks(), i);
                        if (obj.getPendingTasks() > 200) {
                            cell.setIconColor(Color.RED, i);
                        }
                    } else if ((baseIndex + 1) % 3 == i) {
                        cell.setIcon(R.drawable.ic_dot_black_12dp, i, R.string.object_cell_icon_attention_desc);
                        if (obj.isNeedsAttention()) {
                            cell.setIconColor(Color.GREEN, i);
                        } else {
                            cell.setIconColor(Color.LTGRAY, i);
                        }
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
                    Glide.with(this).load(obj.getDetailImageUri()).apply(cropOptions).into(cell.prepareDetailImageView());
                    cell.setDetailImageDescription(R.string.avatar);
                } else {
                    cell.setDetailImage(null);
                    if (TextUtils.isEmpty(obj.getSubHeadline())) {
                        cell.setDetailImageCharacter("?");
                    } else {
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
                        cell.setStatusColor(sapUiNegativeText, index);
                    } else if (obj.getStatusId() == R.drawable.ic_warning_black_24dp) {
                        statusDescId = R.string.warning;
                        cell.setStatusColor(sapUiCriticalText, index);
                    } else {
                        cell.setStatusColor(sapUiPositiveText, index);
                    }
                    cell.setStatus(obj.getStatusId(), index, statusDescId);
                    index++;
                }
                if (mObjectCellSpec.hasFirstStatus()) {
                    if (mObjectCellSpec.isDynamicStatus()) {
                        cell.setDynamicStatusWidth(true);
                        cell.setStatus(obj.getInfo(), index);
                    } else {
                        cell.setStatus(obj.getPriority().toString(), index);
                        if (obj.getPriority() == Priority.HIGH) {
                            cell.setStatusColor(sapUiNegativeText, index);
                        } else {
                            cell.setStatusColor(cell.getDefaultStatusColor(), index);
                        }
                    }
                }

            } else {
                if (mObjectCellSpec.hasFirstStatus()) {
                    if (mObjectCellSpec.isDynamicStatus()) {
                        cell.setDynamicStatusWidth(true);
                        cell.setStatus(obj.getInfo(), 0);
                    } else {
                        cell.setStatus(obj.getPriority().toString(), 0);
                        if (obj.getPriority() == Priority.HIGH) {
                            cell.setStatusColor(sapUiNegativeText, 0);
                        } else {
                            cell.setStatusColor(cell.getDefaultStatusColor(), 0);
                        }
                    }
                }
                if (mObjectCellSpec.hasSecondStatus()) {
                    int statusDescId = android.R.string.ok;

                    if (obj.getStatusId() == R.drawable.ic_error_black_24dp) {
                        statusDescId = R.string.error;
                        //colors of other 2 icons are set in drawable
                        cell.setStatusColor(sapUiNegativeText, 1);
                    } else if (obj.getStatusId() == R.drawable.ic_warning_black_24dp) {
                        statusDescId = R.string.warning;
                        cell.setStatusColor(sapUiCriticalText, 1);
                    } else {
                        cell.setStatusColor(sapUiPositiveText, 1);
                    }

                    cell.setStatus(obj.getStatusId(), 1, statusDescId);
                }
            }
            if (mObjectCellSpec.hasAction()) {
                cell.setSecondaryActionIcon(R.drawable.ic_cloud_download_black_24dp);
                cell.setSecondaryActionIconDescription(R.string.download);
                cell.setSecondaryActionOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ObjectCell parent = (ObjectCell) v.getParent();
                        Toast toast = Toast.makeText(v.getContext(),
                                "Item: " + parent.getHeadline() + " is clicked.",
                                Toast.LENGTH_SHORT);
                        toast.show();
                    }
                });
            }
        } else {
            cell.setHeadline("Content loading");
        }
    }

    @Override
    protected int getItemTopMargin() {
        return 0;
    }

    @Override
    protected int getItemBottomMargin() {
        return 0;
    }

    @Override
    protected int getItemViewType(int position) {
        return 0;
    }


    @Override
    protected int getItemCount() {
        return mData.size();
    }

    @Override
    protected Integer getId(int pos) {
        return pos;
    }

    public static class BizObjectLoader extends AsyncTaskLoader<List<BizObject>> {
        private ObjectCellSpec mObjectCellSpec;

        BizObjectLoader(Context context, ObjectCellSpec objectCellSpec) {
            super(context);
            mObjectCellSpec = objectCellSpec;
        }

        @Override
        protected void onStartLoading() {
            forceLoad();
        }

        @Override
        public List<BizObject> loadInBackground() {
            return BizObject.createBizObjectsList(mObjectCellSpec.getCounts(), mObjectCellSpec.getLanguage());
        }
    }
}

