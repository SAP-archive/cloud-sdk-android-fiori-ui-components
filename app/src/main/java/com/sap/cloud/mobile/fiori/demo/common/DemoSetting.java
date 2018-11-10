package com.sap.cloud.mobile.fiori.demo.common;

import android.support.annotation.StringRes;
import android.widget.CompoundButton;

import java.io.Serializable;

/**
 * The model of a setting to control the demo activity behavior. Only the Id and Checked state are
 * serialized so client should restore other fields based on Id after deserialization.
 */
public class DemoSetting implements Serializable{
    private int mId;
    @StringRes
    transient private int mName;
    @StringRes
    transient private int mDescription;
    private boolean mChecked;
    transient private CompoundButton.OnCheckedChangeListener mListener;

    public DemoSetting(int id, int name, int description, boolean initialState,
            CompoundButton.OnCheckedChangeListener listener) {
        mId = id;
        mName = name;
        mDescription = description;
        mChecked = initialState;
        mListener = listener;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public int getName() {
        return mName;
    }

    public void setName(int name) {
        mName = name;
    }

    public int getDescription() {
        return mDescription;
    }

    public void setDescription(int description) {
        mDescription = description;
    }

    public boolean isChecked() {
        return mChecked;
    }

    public void setChecked(boolean checked) {
        mChecked = checked;
    }

    public CompoundButton.OnCheckedChangeListener getListener() {
        return mListener;
    }

    public void setListener(CompoundButton.OnCheckedChangeListener listener) {
        mListener = listener;
    }
}
