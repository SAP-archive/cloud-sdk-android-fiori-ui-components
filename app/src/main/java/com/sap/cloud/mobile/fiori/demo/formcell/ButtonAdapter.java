package com.sap.cloud.mobile.fiori.demo.formcell;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.sap.cloud.mobile.fiori.demo.R;


class ButtonAdapter extends BaseAdapter {
    String[] valueOptions;
    private Context mContext;

    // Gets the context so it can be used later
    public ButtonAdapter(Context c, String[] valueOptions) {
        mContext = c;
        this.valueOptions = valueOptions;
    }

    @Override
    public int getCount() {
        return valueOptions.length;
    }

    @Override
    public Object getItem(int i) {
        return valueOptions[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Nullable
    @Override
    public View getView(int position, @Nullable View convertView, ViewGroup parent) {
        final Button rowButton;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.activity_custom_grid_row, parent, false);
        }
        rowButton = convertView.findViewById(R.id.grid_button);

        if (rowButton != null) {
            convertView.setTag(rowButton);
            rowButton.setText(valueOptions[position]);
            rowButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(@NonNull View view) {
                    view.setSelected(!view.isSelected());
                }
            });
            rowButton.setId(position);
        }
        return convertView;
    }
}

