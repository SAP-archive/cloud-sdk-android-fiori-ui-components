package com.sap.cloud.mobile.fiori.demo.formcell;

import static com.sap.cloud.mobile.fiori.common.Utility.getSpacingAdd;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.TextView;

import com.sap.cloud.mobile.fiori.common.FioriItemClickListener;
import com.sap.cloud.mobile.fiori.common.FioriItemTouchListener;
import com.sap.cloud.mobile.fiori.demo.R;
import com.sap.cloud.mobile.fiori.formcell.GenericListPickerFormCell;
import com.sap.cloud.mobile.fiori.formcell.GenericListPickerFormCellActivity;
import com.sap.cloud.mobile.fiori.search.FioriSearchView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GenericTextPickerLongIdActivity extends GenericListPickerFormCellActivity<TextView, Long> {

    @NonNull
    private List<String> mItemList;

    private List<String> backup;

    public GenericTextPickerLongIdActivity() {
        mItemList = new ArrayList<>();
        backup = new ArrayList<>();
        setItemList(new FilterDataClass().setupData(false));
    }

    public void setItemList(@NonNull List<String> itemList) {
        mItemList.addAll(itemList.subList(0, itemList.size() / 4));
        backup.addAll(itemList);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setDisplayShowTitleEnabled(true);
            ab.setTitle("Choose Text");
        }

        RecyclerView recyclerView = findViewById(R.id.filterList);
        recyclerView.addOnItemTouchListener(new FioriItemTouchListener(recyclerView, new FioriItemClickListener() {
            @Override
            public void onClick(@NonNull View view, int position) {
                Long id = getId(position);
                ArrayList<Long> selections = new ArrayList<>();
                selections.add(id);
                Intent intent = new Intent(ACTION_ITEMS_SELECTED);
                intent.putExtra(GenericListPickerFormCell.SELECTED_POS_OR_IDS, selections);
                LocalBroadcastManager.getInstance(GenericTextPickerLongIdActivity.this).sendBroadcast(intent);
                finish();
            }

            @Override
            public void onLongClick(@NonNull View view, int position) {

            }
        }));
    }

    @NonNull
    @Override
    public TextView onCreateView(int viewType, @NonNull Context context) {
        Objects.requireNonNull(context, "Context was null in TextViewFormCellFilterActivity.onCreateView");
        TextView view = new TextView(getApplicationContext());
        view.setTextAppearance(com.sap.cloud.mobile.fiori.R.style.TextAppearance_Fiori_Body1);
        if (Build.VERSION.SDK_INT >= 28) {
            view.setLineHeight((int) getResources().getDimension(com.sap.cloud.mobile.fiori.R.dimen.body1_line_height));
        } else {
            view.setLineSpacing(getSpacingAdd(view.getPaint(), (int) getResources().getDimension(com.sap.cloud.mobile.fiori.R.dimen.body1_line_height)), 1);
        }
        view.setTextColor(getResources().getColor(com.sap.cloud.mobile.fiori.R.color.sap_ui_base_text, context.getTheme()));
        view.setPadding(view.getPaddingStart(), 0, view.getPaddingEnd(), 0);
        return view;
    }

    @Override
    protected void onBindView(@NonNull TextView view, Long id) {
        if (id % 2 != 0) {
            view.setText(backup.get(Integer.valueOf(Long.toString(id))).substring(0, 9));
        } else {
            view.setText(backup.get(Integer.valueOf(Long.toString(id))));
        }
    }

    @Override
    protected int getItemViewType(int position) {
        return 0;
    }

    @Override
    protected int getItemCount() {
        return mItemList.size();
    }

    @Override
    protected void restoreData(@Nullable Bundle bundle) {
        mItemList = bundle == null ? new ArrayList<>() : bundle.getStringArrayList(String.valueOf(com.sap.cloud.mobile.fiori.R.string.item_list));
    }

    @Override
    protected Bundle saveData() {
        Bundle bundle = new Bundle();
        bundle.putStringArrayList(String.valueOf(com.sap.cloud.mobile.fiori.R.string.item_list), (ArrayList<String>) mItemList);
        return bundle;
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        FioriSearchView mFioriSearchView = (FioriSearchView) menu.findItem(R.id.menu_search).getActionView();
        if (mFioriSearchView != null) {
            mFioriSearchView.setMaxWidth(Integer.MAX_VALUE);
            mFioriSearchView.setIconifiedByDefault(true);
            mFioriSearchView.setBackgroundResource(com.sap.cloud.mobile.fiori.R.color.transparent);
            mFioriSearchView.setScanEnabled(true);
            mFioriSearchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        notifyDataSetChanged();
                    } else {
                        mItemList.clear();
                        mItemList.addAll(backup.subList(0, backup.size() / 4));
                        notifyDataSetChanged();
                    }
                }
            });
            mFioriSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    if (!s.isEmpty()) {
                        updateSearch(s);
                    }
                    return true;
                }
            });
        }
        return true;
    }

    void updateSearch(String s) {
        List<String> temp = new ArrayList<>(backup);
        mItemList.clear();
        for (String str : temp) {
            if (str.contains(s)) {
                mItemList.add(str);
            }
        }
        notifyDataSetChanged();
    }

    @Override
    protected void onBindPosition(int pos) {
        // Load more data if you wish
    }

    @Override
    public Long getId(int position) {
        return Long.valueOf(mItemList.get(position).substring(0, mItemList.get(position).indexOf("-")));
    }
}