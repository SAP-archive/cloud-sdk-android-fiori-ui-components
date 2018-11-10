package com.sap.cloud.mobile.fiori.demo.formcell;

import android.content.Context;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sap.cloud.mobile.fiori.demo.R;
import com.sap.cloud.mobile.fiori.formcell.FormCell;

public class CustomFormCell1 extends LinearLayout implements FormCell<CharSequence> {
    public static final int CUSTOM_FORM_CELL_1 = 1001;
    private EditText mKeyValue;
    private CharSequence mValue;
    private Boolean mIsEditable;
    private CellValueChangeListener<CharSequence> mCellValueChangeListener;
    private CharSequence mHint;
    private int mMaxLength;

    /**
     * Construct a new with default styling, sets the CellType to WidgetType.TITLE_CELL
     *
     * @param context context
     */
    public CustomFormCell1(Context context) {
        super(context);
        initialize();
    }

    /**
     * Construct a new with default styling, overriding the attributes for the control
     * as requested, sets the CellType to WidgetType.TITLE_CELL
     *
     * @param context context
     * @param attrs   attribute set
     */
    public CustomFormCell1(Context context,
            @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    private void initialize() {
        View view = inflate(getContext(), R.layout.custom_cell_2,this);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(params);
        mKeyValue = view.findViewById(com.sap.cloud.mobile.fiori.R.id.SimplePropertyFormCellValue);
        mMaxLength = -1;
        mValue = "";
        mHint = "";
        mKeyValue.setFocusableInTouchMode(false);
        mKeyValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (mCellValueChangeListener != null) {
                    mCellValueChangeListener.beforeCellChanged(charSequence);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(@NonNull Editable editable) {
                /*
                 * No changes in the text field if the new text length + the current one is
                 * greater then the maxLength.
                 */
                int newLength = editable.toString().length();
                if (mMaxLength != -1
                        && newLength > mMaxLength) {
                    return;
                } else {
                    mValue = editable.toString();
                    if (mCellValueChangeListener != null) {
                        mCellValueChangeListener.cellChanged(editable.toString());
                    }
                }
            }
        });
    }

    @Override
    public boolean isEditable() {
        return mIsEditable;
    }

    @Override
    public void setEditable(boolean isEditable) {
        mIsEditable = isEditable;
        if (!isEditable) {
            mKeyValue.setInputType(InputType.TYPE_NULL);
            mKeyValue.setKeyListener(null);
            mKeyValue.setTextIsSelectable(true);
        } else {
            mKeyValue.setTextIsSelectable(false);
        }
    }

    /**
     * Returns the text in the SimplePropertyFormCellValue
     *
     * @return CharSequence text in the cell
     */
    @Override
    public CharSequence getValue() {
        return mValue;
    }

    /**
     * Sets the text for the title in the cell.
     *
     * @param value CharSequence
     */
    @Override
    public void setValue(CharSequence value) {
        mKeyValue.setText(value);
        mValue = value;
    }

    @Override
    public CellValueChangeListener<CharSequence> getCellValueChangeListener() {
        return mCellValueChangeListener;
    }

    @Override
    public void setCellValueChangeListener(CellValueChangeListener<CharSequence> listener) {
        mCellValueChangeListener = listener;
    }

    /**
     * Return the enum value for SimplePropertyFormCellValue
     *
     * @return WidgetType type of the control which has implemented FormCell
     * @see com.sap.cloud.mobile.fiori.formcell.FormCell.WidgetType
     */
    @Override
    public int getCellType() {
        return CUSTOM_FORM_CELL_1;
    }

    /**
     * Returns the placeholder text.
     *
     * @return CharSequence Placeholder
     */
    public CharSequence getHint() {
        return mHint;
    }

    /**
     * Sets the placeholder string to be put on the text area before user typed anything.
     *
     * @param hint CharSequence
     */
    public void setHint(CharSequence hint) {
        mHint = hint;
        mKeyValue.setHint(hint);
    }

    /**
     * Returns the maximum length of the title text.
     *
     * @return int maxLength of text
     */
    public int getMaxLength() {
        return mMaxLength;
    }

    /**
     * This is the maximum length of the title text, if maxTitleTextLength is greater than 0. If
     * the text length reaches this limit, the user cannot enter more text. Note: If the user
     * pastes a string and the length plus the current text length is greater than the limit, the
     * insert is rejected.
     * Partial strings are not accepted in the text field.By default, there is no limit on the length of the title text.
     *
     * @param maxLength int
     */
    public void setMaxLength(int maxLength) {
        mMaxLength = maxLength;
        InputFilter[] filterArray = new InputFilter[1];
        filterArray[0] = new InputFilter.LengthFilter(maxLength);
        mKeyValue.setFilters(filterArray);
        if (mValue.length() == 0) {
            mKeyValue.setHint(mHint.subSequence(0, maxLength));
        } else {
            mKeyValue.setText(mValue);
        }
    }

    /**
     * Return the view holding the value string
     *
     * @return TextView textview displaying value
     */
    public TextView getValueTextField() {
        return mKeyValue;
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState ss = new SavedState(superState);
        ss.value = mValue;
        return ss;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        setValue(ss.value);
    }

    private static class SavedState extends BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR
                = new Parcelable.Creator<SavedState>() {
            public SavedState createFromParcel(@NonNull Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
        CharSequence validationMessage;
        CharSequence value;

        SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(@NonNull Parcel in) {
            super(in);
            value = in.readString();
        }

        @Override
        public void writeToParcel(@NonNull Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeString(String.valueOf(value));
        }
    }

    @Override
    protected void onFocusChanged(boolean gainFocus, int direction, @Nullable Rect previouslyFocusedRect) {
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
        if (mCellValueChangeListener != null) {
            mCellValueChangeListener.focusChanged(mValue);
        }
    }
}
