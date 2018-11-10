package com.sap.cloud.mobile.fiori.demo.formcell;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Loader;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.request.RequestOptions;
import com.sap.cloud.mobile.fiori.demo.R;
import com.sap.cloud.mobile.fiori.demo.object.BizObject;
import com.sap.cloud.mobile.fiori.demo.object.ObjectCellSpec;
import com.sap.cloud.mobile.fiori.demo.object.Priority;
import com.sap.cloud.mobile.fiori.formcell.GenericListPickerFormCellActivity;
import com.sap.cloud.mobile.fiori.object.ObjectCell;

import java.util.ArrayList;
import java.util.List;

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
            mObjectCellSpec = new ObjectCellSpec(R.layout.object_cell_1, NUM_BIZ_OBJECTS, 1, 1,
                    false,
                    true, false, true,
                    true, true, true);
            mObjectCellSpec.setShuffleIconStack(true);
            mObjectCellSpec.setShuffleStatus(true);
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
    public void onLoaderReset(Loader<List<BizObject>> loader) {
        mData.clear();
    }

    @NonNull
    @Override
    protected ObjectCell onCreateView(int viewType, @NonNull Context context) {
        ObjectCell cell = new ObjectCell(getApplicationContext());
        cell.setPadding(0, cell.getPaddingTop(), 0, cell.getPaddingBottom());
        return cell;
    }

    @Override
    protected void onBindView(@NonNull ObjectCell cell, Integer position) {
        if (mObjectCellSpec == null) {
            mObjectCellSpec = getObjectCellSpec();
        }
        if (mData.size() > 0) {
            BizObject obj = mData.get(position);

            //for espresso testing
            cell.setContentDescription("ObjectCell#" + obj.getId());

            if (mObjectCellSpec.hasIconStack()) {
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

            if (mObjectCellSpec.hasDetailImage()) {

                if (obj.getDetailImageResId() != 0) {
                    cell.setDetailImageCharacter(null);
                    cell.setDetailImage(obj.getDetailImageResId());
                    cell.setDetailImageDescription(R.string.avatar);
                } else if (obj.getDetailImageUri() != null) {
                    RequestOptions cropOptions = new RequestOptions().placeholder(
                            R.drawable.rectangle);
                    cell.setDetailImageCharacter(null);
                    cell.setDetailImageDescription(R.string.avatar);
                } else {
                    cell.setDetailImage(null);
                    cell.setDetailImageCharacter(obj.getSubHeadline().substring(0, 1));
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
                        //colors of other 2 icons are set in drawable
                        cell.setStatusColor(Color.RED, index);
                    } else if (obj.getStatusId() == R.drawable.ic_warning_black_24dp) {
                        statusDescId = R.string.warning;
                    }
                    cell.setStatus(obj.getStatusId(), index, statusDescId);
                    index++;
                }
                if (mObjectCellSpec.hasFirstStatus()) {
                    cell.setStatus(obj.getPriority().toString(), index);
                    if (obj.getPriority() == Priority.HIGH) {
                        cell.setStatusColor(Color.RED, index);
                    } else {
                        cell.setStatusColor(0, index);
                    }
                }

            } else {
                if (mObjectCellSpec.hasFirstStatus()) {
                    cell.setStatus(obj.getPriority().toString(), 0);
                    if (obj.getPriority() == Priority.HIGH) {
                        cell.setStatusColor(Color.RED, 0);
                    } else {
                        cell.setStatusColor(0, 0);
                    }
                }
                if (mObjectCellSpec.hasSecondStatus()) {
                    int statusDescId = android.R.string.ok;

                    if (obj.getStatusId() == R.drawable.ic_error_black_24dp) {
                        statusDescId = R.string.error;
                        //colors of other 2 icons are set in drawable
                        cell.setStatusColor(Color.RED, 1);
                    } else if (obj.getStatusId() == R.drawable.ic_warning_black_24dp) {
                        statusDescId = R.string.warning;
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

