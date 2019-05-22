package com.sap.cloud.mobile.fiori.demo.formcell;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnItemTouchListener;
import android.support.v7.widget.SearchView;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.sap.cloud.mobile.fiori.demo.R;
import com.sap.cloud.mobile.fiori.demo.hierarchy.HierarchyViewDemo;
import com.sap.cloud.mobile.fiori.demo.object.Priority;
import com.sap.cloud.mobile.fiori.formcell.GenericListPickerFormCellActivity;
import com.sap.cloud.mobile.fiori.hierarchy.HierarchyAccessoryView;
import com.sap.cloud.mobile.fiori.hierarchy.HierarchyObjectCell;
import com.sap.cloud.mobile.fiori.search.FioriSearchView;
import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GenericHierarchyObjectCellPickerActivity extends GenericListPickerFormCellActivity<HierarchyObjectCell, String> {
    private static final int NUM_BIZ_OBJECTS = 1000;
    protected static int sSapUiFieldActiveBorderColor;
    protected static int sSapUiNegativeText;
    protected static int sSapUiCriticalText;
    protected static int sSapUiPositiveText;
    protected static int sSapUiNeutralText;
    final String ROOT = "Root";
    final String ROOT_PARENT_ID = "Root-Parent";
    final String ABSOLUTE_PARENT = "Absolute Parent";
    final String ROOT_ID = "Root-9-4";
    RecyclerView mRecyclerView;
    FioriSearchView mFioriSearchView;
    Lorem lorem = new LoremIpsum();
    Random mRandom = new Random();
    private List<String> mOriginalData = new ArrayList<>();
    private List<String> mData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mRecyclerView = findViewById(com.sap.cloud.mobile.fiori.R.id.filterList);
        LinearLayout layout = findViewById(R.id.picker_layout);
        mFioriSearchView = new FioriSearchView(mRecyclerView.getContext());
        mFioriSearchView.setQueryHint("Search");
        mFioriSearchView.setIconifiedByDefault(false);
        mFioriSearchView.setScanEnabled(false);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        int margin = (int) getResources().getDimension(R.dimen.margin_8dp_standard);
        params.rightMargin = margin;
        params.leftMargin = margin;
        params.topMargin = margin;
        params.bottomMargin = margin;
        mFioriSearchView.setLayoutParams(params);
        mFioriSearchView.setElevation(margin);
        int pos = layout.getChildCount() > 1 ? 1 : 0;
        layout.addView(mFioriSearchView, pos);

        sSapUiFieldActiveBorderColor = getColor(R.color.sap_ui_field_active_border_color);
        sSapUiNegativeText = getColor(R.color.sap_ui_negative_text);
        sSapUiPositiveText = getColor(R.color.sap_ui_positive_text);
        sSapUiCriticalText = getColor(R.color.sap_ui_critical_text);
        sSapUiNeutralText = getColor(R.color.sap_ui_neutral_text);

        initialize();

        mRecyclerView.addOnItemTouchListener(new OnItemTouchListener() {
            GestureDetector gestureDetector = new GestureDetector(getApplicationContext(), new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    // return true as FioriTouchListener wants to handle the SingleTapUp events
                    return true;
                }
            });

            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    View target = mRecyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
                    HierarchyObjectCell cell = null;
                    if (target instanceof LinearLayout) {
                        cell = (HierarchyObjectCell) ((LinearLayout) target).getChildAt(1);
                    }
                    if (cell != null && cell.isAccessoryClicked(motionEvent, cell)) {
                        String id = (String) cell.getHeadline();
                        Intent intent = new Intent();
                        intent.putExtra("root", id);
                        intent.putExtra("startedForResult", true);
                        intent.putExtra("singleSelect", false);
                        intent.setClass(GenericHierarchyObjectCellPickerActivity.this, HierarchyViewDemo.class);
                        startActivityForResult(intent, 100);
                    } else if (cell != null) {
                        cell.setBackgroundResource(R.color.transparent);
                    }
                }
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean b) {

            }
        });

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        mFioriSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        mFioriSearchView.setOnCollapseBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFioriSearchView.clearFocus();
            }
        });
        mFioriSearchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    ((ImageView) findViewById(R.id.search_mag_icon)).setImageResource(R.drawable.ic_arrow_back_black_24dp);
                } else {
                    ((ImageView) findViewById(R.id.search_mag_icon)).setImageResource(R.drawable.ic_search);
                }
            }
        });

        mFioriSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                mData.clear();
                for (int i = 0; i < mOriginalData.size(); i++) {
                    if (mOriginalData.get(i).toLowerCase().contains(s.toLowerCase())) {
                        mData.add(mOriginalData.get(i));
                    }
                }
                notifyDataSetChanged();
                return true;
            }
        });
    }

    @NonNull
    @Override
    protected HierarchyObjectCell onCreateView(int viewType, @NonNull Context context) {
        HierarchyObjectCell view = new HierarchyObjectCell(context);
        view.setPreserveIconStackSpacing(false);
        view.setPreserveDetailImageSpacing(false);
        view.setPreserveIconImageContainer(false);
        view.setPreserveDescriptionSpacing(false);
        // Default top and bottom padding on ObjectCell are 4dp and 2dp respectively. Make sure that top padding is also 2dp
        view.setPadding(0, view.getPaddingBottom(), 0, view.getPaddingBottom());
        view.setActionTopAlign(true);
        return view;
    }

    @Override
    protected void onBindView(@NonNull HierarchyObjectCell cell, String id) {
        cell.setSelected(false);
        cell.setHeadline(id);
        cell.setSubheadline(lorem.getWords(5));
        cell.setFootnote(lorem.getWords(9));
        int random = mRandom.nextInt(3);
        Priority priority = random == 0 ? Priority.HIGH : (random == 1 ? Priority.LOW : Priority.NORM);
        cell.clearStatuses();
        cell.setStatus(priority.toString(), 0);
        if (priority == Priority.HIGH) {
            cell.setStatusColor(sSapUiNegativeText, 0);
        } else {
            cell.setStatusColor(cell.getDefaultStatusColor(), 0);
        }
        int statusDescId;
        int statusDrawableId;
        int statusColor;

        if (priority == Priority.HIGH) {
            statusDescId = R.string.high;
            statusDrawableId = R.drawable.ic_error_black_24dp;
            statusColor = sSapUiNegativeText;
        } else if (priority == Priority.NORM) {
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

        ((HierarchyAccessoryView) cell.getAccessoryView()).setAccessoryText(Integer.toString(getChildCountForParent(id)));
//        cell.setBackgroundResource(R.color.transparent);
    }

    @Override
    protected int getItemViewType(int position) {
        return 0;
    }

    @Override
    protected int getItemCount() {
        return mData.isEmpty() ? 0 : NUM_BIZ_OBJECTS;
    }

    @Override
    protected String getId(int pos) {
        if (pos < mData.size()) {
            return mData.get(pos);
        }
        return null;
    }

    private void initialize() {
        List<String> temp = new ArrayList<>();
        mOriginalData.add(ABSOLUTE_PARENT);
        temp.add(ABSOLUTE_PARENT);
        while (mOriginalData.size() < NUM_BIZ_OBJECTS && !temp.isEmpty()) {
            String parent = temp.remove(0);
            for (int i = 0; i < getChildCountForParent(parent); i++) {
                mOriginalData.add(getChildId(parent, i));
                temp.add(getChildId(parent, i));
            }
        }
        mData.addAll(mOriginalData);
    }

    protected String getChildId(String parentId, int pos) {
        if (ABSOLUTE_PARENT.equals(parentId)) {
            return ROOT_PARENT_ID;
        } else if (ROOT_PARENT_ID.equals(parentId)) {
            return ROOT;
        } else {
            return parentId + "-" + pos;
        }
    }

    protected int getChildCountForParent(@Nullable String id) {
        if (id == null || id.equals(ABSOLUTE_PARENT) || id.equals(ROOT_PARENT_ID)) {
            return 1;
        } else {
            return id.equals(ROOT_ID) ? 10 : (id.length() % 2 == 0 ? id.length() * 5 : 0);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (data != null) {
                ArrayList<String> newSelections = data.getStringArrayListExtra("selections");
                List<String> prevSelection = getSelections();
                prevSelection.addAll(newSelections);
                setSelections(prevSelection);
                notifyDataSetChanged();
            }
        }
    }

    @Override
    protected int getSelectorGravity() {
        return Gravity.CENTER;
    }
}
