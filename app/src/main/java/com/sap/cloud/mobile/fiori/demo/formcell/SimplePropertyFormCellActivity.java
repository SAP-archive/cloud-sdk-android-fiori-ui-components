package com.sap.cloud.mobile.fiori.demo.formcell;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.sap.cloud.mobile.fiori.demo.AbstractDemoActivity;
import com.sap.cloud.mobile.fiori.demo.R;
import com.sap.cloud.mobile.fiori.formcell.FormCell;
import com.sap.cloud.mobile.fiori.formcell.SimplePropertyFormCell;

public class SimplePropertyFormCellActivity extends AbstractDemoActivity {
    private SimplePropertyFormCell mSimplePropertyFormCell1, mSimplePropertyFormCell2, mSimplePropertyFormCell3, mSimplePropertyFormCell4;
    @Nullable
    private MenuItem mItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_property_form_cell);
        mSimplePropertyFormCell1 = findViewById(R.id.SimplePropertyFormCell1);
        mSimplePropertyFormCell2 = findViewById(R.id.SimplePropertyFormCell2);
        mSimplePropertyFormCell3 = findViewById(R.id.SimplePropertyFormCell3);
        mSimplePropertyFormCell4 = findViewById(R.id.SimplePropertyFormCell4);
        mSimplePropertyFormCell1.setError("Not a valid value");
        mSimplePropertyFormCell1.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
        mSimplePropertyFormCell2.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
        mSimplePropertyFormCell3.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
        mSimplePropertyFormCell4.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);

        mSimplePropertyFormCell1.setCellValueChangeListener(new FormCell.CellValueChangeListener<CharSequence>() {
            @Override
            protected void cellChangeHandler(@NonNull CharSequence value) {
                if (value.length() > 10) {
                    mSimplePropertyFormCell1.setErrorEnabled(true);
                    mSimplePropertyFormCell1.setError("This is too long value");
                } else {
                    mSimplePropertyFormCell1.setErrorEnabled(false);
                }
            }
        });

        mSimplePropertyFormCell2.setCellValueChangeListener(new FormCell.CellValueChangeListener<CharSequence>() {
            @Override
            protected void cellChangeHandler(@NonNull CharSequence value) {
                if (value.length() > 10) {
                    mSimplePropertyFormCell2.setErrorEnabled(true);
                    mSimplePropertyFormCell2.setError("This is too long value");
                } else {
                    mSimplePropertyFormCell2.setErrorEnabled(false);
                }
            }
        });

        mSimplePropertyFormCell3.setCellValueChangeListener(new FormCell.CellValueChangeListener<CharSequence>() {
            @Override
            protected void cellChangeHandler(@NonNull CharSequence value) {
                if (value.length() > 10) {
                    mSimplePropertyFormCell3.setError("This is too long value");
                } else {
                    mSimplePropertyFormCell3.setHelperText("This is great!");
                }
            }
        });

        mSimplePropertyFormCell4.setCellValueChangeListener(new FormCell.CellValueChangeListener<CharSequence>() {
            @Override
            protected void cellChangeHandler(@NonNull CharSequence value) {
                if (value.length() > 10) {
                    mSimplePropertyFormCell4.setError("This is too long value");
                } else {
                    mSimplePropertyFormCell4.setHelperText("This is great!");
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.attachment_menu, menu);
        mItem = menu.findItem(R.id.attachment_edit_menu);
        if (mItem != null) {
            if (mSimplePropertyFormCell1.isEnabled()) {
                mItem.setTitle("Save");
            } else {
                mItem.setTitle("Edit");
            }
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.attachment_edit_menu:
                if (mSimplePropertyFormCell1.isEnabled()) {
                    mSimplePropertyFormCell1.setEnabled(false);
                    mSimplePropertyFormCell2.setEnabled(false);
                    mSimplePropertyFormCell3.setEnabled(false);
                    mSimplePropertyFormCell4.setEnabled(false);
                    item.setTitle("Edit");
                } else {
                    mSimplePropertyFormCell1.setEnabled(true);
                    mSimplePropertyFormCell2.setEnabled(true);
                    mSimplePropertyFormCell3.setEnabled(true);
                    mSimplePropertyFormCell4.setEnabled(true);
                    item.setTitle("Save");
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
