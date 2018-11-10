package com.sap.cloud.mobile.fiori.demo.common;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Switch;
import android.widget.TextView;

import com.sap.cloud.mobile.fiori.demo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter to use DemoSetting in a list.
 */
public class DemoSettingAdapter extends BaseAdapter {
    @Nullable
    private final LayoutInflater mInflater;
    private List<DemoSetting> mItems = new ArrayList<>();


    public DemoSettingAdapter(Context context,
            List<DemoSetting> items) {
        mItems = items;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        if (mItems.size() <= position) {
            return null;
        }
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        DemoSetting item = (DemoSetting)getItem(position);
        if (item == null){
            return 0;
        }
        return item.getId();
    }

    @Nullable
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view;
        //if we reuse convertView, the first item would not layout correctly for RTL
        if (convertView == null || parent.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL) {
            view = mInflater.inflate(R.layout.setting_list_item, parent, false);
        } else {
            view = convertView;
        }

        bindView(position, view);
        return view;
    }

    private void bindView(int position, @NonNull View view) {
        if (mItems.size() <= position) {
            return;
        }
        DemoSetting setting = mItems.get(position);
        TextView nameView = view.findViewById(R.id.text1);
        nameView.setText(setting.getName());
        TextView descView = view.findViewById(R.id.text2);
        descView.setText(setting.getDescription());
        final Switch switchView = view.findViewById(R.id.switch1);
        //for espresso
        switchView.setContentDescription("switch_"+setting.getId());
        switchView.setOnCheckedChangeListener(null);
        //will trigger listener if it's not null
        switchView.setChecked(setting.isChecked());
        switchView.setOnCheckedChangeListener(setting.getListener());
    }

}
