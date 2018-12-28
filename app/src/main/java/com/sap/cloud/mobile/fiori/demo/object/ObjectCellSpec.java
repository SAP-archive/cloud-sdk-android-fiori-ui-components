package com.sap.cloud.mobile.fiori.demo.object;

import android.support.annotation.IdRes;

/**
 * Instructs the ObjectCell test activities how to render ObjectCells
 */

public class ObjectCellSpec {
    private BizObject.LANGUAGE mLanguage = BizObject.LANGUAGE.ENGLISH;
    @IdRes private int mLayoutId;
    private int mCounts;
    private int mLines;
    private int mHeadlineLines;
    private boolean mHasFootnote;
    private boolean mHasSubheadline;
    private boolean mHasAction;
    private boolean mHasFirstStatus;
    private boolean mHasSecondStatus;
    private boolean mHasDetailImage;
    private boolean mHasIconStack;
    private boolean mShuffleIconStack;
    private boolean mShuffleStatus;
    private boolean mHasContainerLayout;
    private boolean mHideEmptyDescription;
    private boolean mDynamicStatus;

    public ObjectCellSpec(){}

    public ObjectCellSpec(int layoutId, int counts, int lines, int headlineLines,
            boolean hasFootnote,
            boolean hasSubheadline, boolean hasAction, boolean hasFirstStatus,
            boolean hasSecondStatus, boolean hasDetailImage, boolean hasIconStack) {
        mLayoutId = layoutId;
        mCounts = counts;
        mLines = lines;
        mHeadlineLines = headlineLines;
        mHasFootnote = hasFootnote;
        mHasSubheadline = hasSubheadline;
        mHasAction = hasAction;
        mHasFirstStatus = hasFirstStatus;
        mHasSecondStatus = hasSecondStatus;
        mHasDetailImage = hasDetailImage;
        mHasIconStack = hasIconStack;
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

    public int getHeadlineLines() {
        return mHeadlineLines;
    }

    public void setHeadlineLines(int headlineLines) {
        mHeadlineLines = headlineLines;
    }

    public boolean hasFootnote() {
        return mHasFootnote;
    }

    public void setHasFootnote(boolean hasFootnote) {
        mHasFootnote = hasFootnote;
    }

    public boolean hasSubheadline() {
        return mHasSubheadline;
    }

    public void setHasSubheadline(boolean hasSubheadline) {
        mHasSubheadline = hasSubheadline;
    }

    public boolean hasAction() {
        return mHasAction;
    }

    public void setHasAction(boolean hasAction) {
        mHasAction = hasAction;
    }

    public boolean hasFirstStatus() {
        return mHasFirstStatus;
    }

    public void setHasFirstStatus(boolean hasFirstStatus) {
        mHasFirstStatus = hasFirstStatus;
    }

    public boolean hasSecondStatus() {
        return mHasSecondStatus;
    }

    public void setHasSecondStatus(boolean hasSecondStatus) {
        mHasSecondStatus = hasSecondStatus;
    }

    public boolean hasDetailImage() {
        return mHasDetailImage;
    }

    public void setHasDetailImage(boolean hasDetailImage) {
        mHasDetailImage = hasDetailImage;
    }

    public boolean hasIconStack() {
        return mHasIconStack;
    }

    public void setHasIconStack(boolean hasIconStack) {
        mHasIconStack = hasIconStack;
    }

    public boolean shouldShuffleIconStack() {
        return mShuffleIconStack;
    }

    public void setShuffleIconStack(boolean shuffleIconStack) {
        mShuffleIconStack = shuffleIconStack;
    }

    public boolean isShuffleStatus() {
        return mShuffleStatus;
    }

    public void setShuffleStatus(boolean shuffleStatus) {
        mShuffleStatus = shuffleStatus;
    }

    public BizObject.LANGUAGE getLanguage() {
        return mLanguage;
    }

    public void setLanguage(BizObject.LANGUAGE language) {
        mLanguage = language;
    }

    public boolean hasContainerLayout() {
        return mHasContainerLayout;
    }

    public void setHasContainerLayout(boolean hasContainerLayout) {
        mHasContainerLayout = hasContainerLayout;
    }

    public boolean getHideEmptyDescription() {
        return mHideEmptyDescription;
    }

    public void setHideEmptyDescription(boolean hideEmptyDescription) {
        mHideEmptyDescription = hideEmptyDescription;
    }

    public boolean isDynamicStatus() {
        return mDynamicStatus;
    }

    public void setDynamicStatus(boolean dynamicStatus) {
        mDynamicStatus = dynamicStatus;
    }
}
