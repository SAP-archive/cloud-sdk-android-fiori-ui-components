package com.sap.cloud.mobile.fiori.demo.hierarchy;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.sap.cloud.mobile.fiori.demo.AbstractDemoActivity;
import com.sap.cloud.mobile.fiori.demo.R;
import com.sap.cloud.mobile.fiori.demo.object.BaseObjectCellActivity;
import com.sap.cloud.mobile.fiori.demo.object.BizObject;
import com.sap.cloud.mobile.fiori.demo.object.ObjectCellSpec;
import com.sap.cloud.mobile.fiori.demo.object.ObjectHeaderActivity;
import com.sap.cloud.mobile.fiori.demo.object.Priority;
import com.sap.cloud.mobile.fiori.formcell.ChoiceFormCell;
import com.sap.cloud.mobile.fiori.hierarchy.HierarchyAccessoryView;
import com.sap.cloud.mobile.fiori.hierarchy.HierarchyItemClickListener;
import com.sap.cloud.mobile.fiori.hierarchy.HierarchyObjectCell;
import com.sap.cloud.mobile.fiori.hierarchy.HierarchyObjectPickerCell;
import com.sap.cloud.mobile.fiori.hierarchy.HierarchyView;
import com.sap.cloud.mobile.fiori.hierarchy.HierarchyViewItemAdapter;
import com.sap.cloud.mobile.fiori.object.ObjectCell;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;


public class HierarchyViewDemo extends AbstractDemoActivity implements LoaderManager.LoaderCallbacks<List<BizObject>> {
    public static final int NUM_BIZ_OBJECTS = 300;
    protected static int sSapUiFieldActiveBorderColor;
    protected static int sSapUiNegativeText;
    protected static int sSapUiCriticalText;
    protected static int sSapUiPositiveText;
    protected static int sSapUiNeutralText;
    final String ROOT = "Root";
    final String ROOT_PARENT_ID = "Root-Parent";
    final String ABSOLUTE_PARENT = "Absolute Parent";
    final String ROOT_ID = "Root-9-4";
    String mRoot = ROOT_ID;
    //    final String ROOT_ID = ABSOLUTE_PARENT;
    Set<String> changed = new HashSet<>();
    private ObjectCellSpec mObjectCellSpec;
    private HierarchyView mHierarchyView;
    private HierarchyViewDemoItemAdapter mHierarchyItemViewAdapter;
    private ChoiceFormCell mChoiceFormCell;
    private LinearLayout mLinearLayout;
    boolean mStartedAsPicker;
    boolean mSingleSelect;
    ArrayList<String> mSelections = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        boolean ensurePhoneLayout = false;
        mStartedAsPicker = intent.getBooleanExtra("startedForResult", false);
        mSingleSelect = intent.getBooleanExtra("singleSelect", false);
        if (mStartedAsPicker) {
            setTheme(R.style.DialogOnTablet);
            ensurePhoneLayout = true;
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hierarchy_view_demo);

        Toolbar myToolbar = findViewById(R.id.pickerToolbar);
        if (getSupportActionBar() == null) {
            setSupportActionBar(myToolbar);
        } else {
            myToolbar.setVisibility(View.GONE);
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mLinearLayout = findViewById(R.id.linearLayout);

        getObjectCellSpec();

        mChoiceFormCell = findViewById(R.id.choice);
        mChoiceFormCell.setValueOptions(new String[]{"Children changed", "Item changed", "Parent changed", "Hierarchy Changed"});
        mChoiceFormCell.setValue(1);

        mHierarchyView = findViewById(R.id.hierarchyView);
        mHierarchyView.setVisibility(View.GONE);

        if (ensurePhoneLayout) {
            // using with dialog theme
            mHierarchyView.forceLayoutType(HierarchyView.LayoutType.PHONE);
        }

        mHierarchyItemViewAdapter = new HierarchyViewDemoItemAdapter();
        mHierarchyView.setHierarchyAdapter(mHierarchyItemViewAdapter);

        mHierarchyItemViewAdapter.setHierarchyItemClickListener(new HierarchyItemClickListener<String>() {

            @Override
            public void onClick(@NonNull View view, String id) {
                if (mStartedAsPicker) {
                    HierarchyObjectPickerCell cell = (HierarchyObjectPickerCell) view;
                    if (mStartedAsPicker) {
                        if (mSelections.contains(id)) {
                            mSelections.remove(id);
                            cell.setChecked(false);
                        } else {
                            mSelections.add(id);
                            cell.setChecked(true);
                        }

                        if (!cell.isMultiSelect()) {
                            Intent output = new Intent();
                            output.putStringArrayListExtra("selections", mSelections);
                            setResult(RESULT_OK, output);
                            finish();
                        }
                    } else {
                        Intent intent = new Intent(getApplicationContext(), ObjectHeaderActivity.class);
                        HierarchyViewDemo.this.startActivityForResult(intent, 100);
                    }
                }
            }

            @Override
            public void onLongClick(@NonNull View view, String id) {
                switch (mChoiceFormCell.getValue()) {
                    case 0:
                        mHierarchyItemViewAdapter.notifyChildrenChanged(id);
                        break;
                    case 1:
                        if (changed.contains(id)) {
                            changed.remove(id);
                        } else {
                            changed.add(id);
                        }
                        mHierarchyItemViewAdapter.notifyItemChanged(id);
                        break;
                    case 2:
                        mHierarchyItemViewAdapter.notifyParentChanged(id);
                        break;
                    case 3:
                        mHierarchyItemViewAdapter.notifyHierarchyChanged();
                        break;
                    default: // do nothing
                }
            }
        });


        sSapUiFieldActiveBorderColor = getColor(R.color.sap_ui_field_active_border_color);
        sSapUiNegativeText = getColor(R.color.sap_ui_negative_text);
        sSapUiPositiveText = getColor(R.color.sap_ui_positive_text);
        sSapUiCriticalText = getColor(R.color.sap_ui_critical_text);
        sSapUiNeutralText = getColor(R.color.sap_ui_neutral_text);

        if (intent.hasExtra("root")) {
            mHierarchyItemViewAdapter.setRoot(intent.getStringExtra("root"));
        }

        getLoaderManager().initLoader(0, null, this);
    }

    /**
     * Subclasses need to override this method to specify how the object_placeholder cells are to be rendered.
     */
    protected ObjectCellSpec getObjectCellSpec() {
        if (mObjectCellSpec == null) {
            mObjectCellSpec = new ObjectCellSpec(R.layout.object_cell_1, NUM_BIZ_OBJECTS, 3, 1,
                    true,
                    true, true, true,
                    true, false, false);
        }
        return mObjectCellSpec;
    }

    @NonNull
    @Override
    public Loader<List<BizObject>> onCreateLoader(int id, Bundle args) {
        return new BaseObjectCellActivity.BizObjectLoader(getBaseContext(), getObjectCellSpec());
    }

    @Override
    public void onLoadFinished(Loader<List<BizObject>> loader, List<BizObject> data) {
        mHierarchyItemViewAdapter.setObjects(data);
        // The list should now be shown.
        mHierarchyView.setVisibility(View.VISIBLE);
        mLinearLayout.postInvalidate();
    }

    @Override
    public void onLoaderReset(Loader<List<BizObject>> loader) {
        mHierarchyItemViewAdapter.setObjects(null);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case android.R.id.home:
                Intent output = new Intent();
                output.putStringArrayListExtra("selections", mSelections);
                setResult(RESULT_OK, output);
                finish();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    static class HierarchyItemViewHolder extends HierarchyView.CellHolder<HierarchyObjectCell> {
        public Target target;
        public ObjectCell cell;
        HierarchyAccessoryView mHierarchyAccessoryView;

        HierarchyItemViewHolder(@NonNull HierarchyObjectCell itemView) {
            super(itemView);
            cell = itemView;
            mHierarchyAccessoryView = (HierarchyAccessoryView) itemView.getAccessoryView();
        }
    }

    class HierarchyViewDemoItemAdapter extends HierarchyViewItemAdapter<String, HierarchyItemViewHolder> {
        protected List<BizObject> mObjects = new ArrayList<>();
        Map<String, Integer> map = new HashMap<>();
        RequestOptions mCropOptions = new RequestOptions().placeholder(R.drawable.basketball);
        RequestOptions mPlaceHolder = new RequestOptions().placeholder(R.drawable.basketball);
        RequestManager mGlide = Glide.with(getApplicationContext());
        Random random = new Random();

        public void setObjects(@Nullable List<BizObject> objects) {
            mObjects.clear();
            if (objects != null) {
                mObjects = objects;
            }
            notifyHierarchyChanged();
        }

        @Override
        protected int getChildCountForParent(@Nullable String id) {
            if (id == null) {
                return 0;
            } else if (id.equals(ABSOLUTE_PARENT) || id.equals(ROOT_PARENT_ID)) {
                return 1;
            } else {
                return id.equals(ROOT_ID) ? 10 : (id.length() % 2 == 0 ? id.length() * 5 : 0);
            }
        }

        @Override
        protected String getParentId(@NonNull String id) {
            switch (id) {
                case ABSOLUTE_PARENT:
                    return null;
                case ROOT_PARENT_ID:
                    return ABSOLUTE_PARENT;
                case ROOT:
                    return ROOT_PARENT_ID;
                default:
                    return id.substring(0, id.lastIndexOf('-'));
            }
        }

        @Override
        protected void onViewRecycled(HierarchyItemViewHolder holder) {
            if (holder.target != null) {
                mGlide.clear(holder.target);
            }
            super.onViewRecycled(holder);
        }

        @Override
        protected String getChildId(String parentId, int pos) {
            if (ABSOLUTE_PARENT.equals(parentId)) {
                return ROOT_PARENT_ID;
            } else if (ROOT_PARENT_ID.equals(parentId)) {
                return ROOT;
            } else {
                return parentId + "-" + pos;
            }
        }

        @Override
        protected void onBindItemViewHolder(@NonNull HierarchyItemViewHolder holder, String id) {
            holder.mHierarchyAccessoryView.setAccessoryText(Integer.toString(getChildCountForParent(id)));
            int bindCount = map.containsKey(id) ? map.get(id) + 1 : 1;
            map.put(id, bindCount);
            HierarchyObjectCell cell = (HierarchyObjectCell) holder.cell;
            if (cell instanceof HierarchyObjectPickerCell) {
                HierarchyObjectPickerCell pickerCell = (HierarchyObjectPickerCell) holder.cell;
                pickerCell.setChecked(mSelections.contains(id));
            }

            int position = random.nextInt(NUM_BIZ_OBJECTS);
            BizObject obj = mObjects.get(position);


            if (mObjectCellSpec.hasIconStack() && (!mObjectCellSpec.hasContainerLayout() || random.nextBoolean())) {
                cell.clearIcons();
                int baseIndex = 0;
                if (mObjectCellSpec.shouldShuffleIconStack()) {
                    baseIndex = position % 3;
                }
                for (int i = 0; i < 3; i++) {
                    if ((baseIndex + 1) % 3 == i) {
                        cell.setIcon(R.drawable.ic_star_black_24dp, i, R.string.star);
                    } else {
                        if (obj.isProtected()) {
                            cell.setIcon(R.drawable.ic_attachment_black_24dp, i, R.string.object_cell_icon_protection_desc);
                        } else {
                            cell.setIcon(R.drawable.ic_refresh_black_24dp, i, R.string.object_cell_icon_not_protection_desc);
                        }
                    }
                }
            }

            if (mObjectCellSpec.hasDetailImage() && obj.getDetailImageUri() != null && (!mObjectCellSpec.hasContainerLayout() || random.nextBoolean())) {
                mGlide.load(obj.getDetailImageUri()).apply(mCropOptions).apply(mPlaceHolder).into(cell.prepareDetailImageView());
            } else {
                cell.setPreserveDetailImageSpacing(false);
            }

            cell.setHeadline(id);

            if (mObjectCellSpec.hasSubheadline()) {
                cell.setSubheadline(obj.getSubHeadline());
            }
            if (mObjectCellSpec.hasFootnote()) {
                cell.setFootnote(obj.getFootnote());
            }

//            cell.setDescription(obj.getDescription());
            cell.clearStatuses();
            if (mObjectCellSpec.hasFirstStatus()) {
                cell.setStatus(obj.getPriority().toString(), 0);
                if (obj.getPriority() == Priority.HIGH) {
                    cell.setStatusColor(sSapUiNegativeText, 0);
                } else {
                    cell.setStatusColor(cell.getDefaultStatusColor(), 0);
                }
            }
            if (mObjectCellSpec.hasSecondStatus()) {
                int statusDescId;
                int statusDrawableId;
                int statusColor;

                if (obj.getPriority() == Priority.HIGH) {
                    statusDescId = R.string.high;
                    statusDrawableId = R.drawable.ic_error_black_24dp;
                    statusColor = sSapUiNegativeText;
                } else if (obj.getPriority() == Priority.NORM) {
                    statusDescId = R.string.medium;
                    statusDrawableId = R.drawable.ic_warning_black_24dp;
                    statusColor = sSapUiCriticalText;
                } else {
                    statusDescId = R.string.low;
                    statusDrawableId = R.drawable.ic_check_circle_black_24dp;
                    statusColor = sSapUiPositiveText;
                }

                cell.setStatusColor(statusColor, 1);
                cell.setStatus(statusDrawableId, 1, statusDescId);
            }
        }

        @Override
        protected CharSequence getTitle(String id) {
            return id;
        }

        @Override
        protected HierarchyItemViewHolder onCreateItemViewHolder() {
            Context context = getApplicationContext();
            HierarchyObjectCell cell = mStartedAsPicker ? new HierarchyObjectPickerCell(context) : new HierarchyObjectCell(context);
            cell.setActionTopAlign(true);
            if (mSingleSelect) {
                HierarchyObjectPickerCell pickerCell = (HierarchyObjectPickerCell) cell;
                pickerCell.setMultiSelect(false);
            }
            return new HierarchyItemViewHolder(cell);
        }

        @Override
        protected String getRootId() {
            return mObjects.isEmpty() ? null : mRoot;
        }

        void setRoot(String root) {
            mRoot = root;
        }
    }
}
