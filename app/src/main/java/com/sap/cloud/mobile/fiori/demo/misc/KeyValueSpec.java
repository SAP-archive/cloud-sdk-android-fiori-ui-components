package com.sap.cloud.mobile.fiori.demo.misc;

import android.support.annotation.IdRes;

/**
 * Instructs the ObjectCell test activities how to render ObjectCells
 */

public class KeyValueSpec {
    @IdRes private int mLayoutId;
    private int mCounts;
    private int mLines;
    private CharSequence mKeyPrefix;
    private boolean mClickable;
    private boolean mExpandable = true;
    private int mMinWords = 3;
    private int mMaxWords = 50;

    public KeyValueSpec(){}

    public KeyValueSpec(int layoutId, int counts, int lines, String keyPrefix, boolean clickable) {
        mLayoutId = layoutId;
        mCounts = counts;
        mLines = lines;
        mKeyPrefix = keyPrefix;
        mClickable = clickable;
    }

    public int getLayoutId() {
        return mLayoutId;
    }

    public void setLayoutId(int layoutId) {
        mLayoutId = layoutId;
    }

    public int getCounts() {
        return mCounts;
    }

    public void setCounts(int counts) {
        mCounts = counts;
    }

    public int getLines() {
        return mLines;
    }

    public void setLines(int lines) {
        mLines = lines;
    }

    public CharSequence getKeyPrefix() {
        return mKeyPrefix;
    }

    public void setKeyPrefix(CharSequence keyPrefix) {
        mKeyPrefix = keyPrefix;
    }

    public boolean isClickable() {
        return mClickable;
    }

    public void setClickable(boolean clickable) {
        mClickable = clickable;
    }

    public boolean isExpandable() {
        return mExpandable;
    }

    public void setExpandable(boolean expandable) {
        mExpandable = expandable;
    }

    public int getMinWords() {
        return mMinWords;
    }

    public void setMinWords(int minWords) {
        mMinWords = minWords;
    }

    public int getMaxWords() {
        return mMaxWords;
    }

    public void setMaxWords(int maxWords) {
        mMaxWords = maxWords;
    }
}
