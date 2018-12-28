package com.sap.cloud.mobile.fiori.demo.formcell;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import com.sap.cloud.mobile.fiori.demo.AbstractDemoActivity;
import com.sap.cloud.mobile.fiori.demo.R;
import com.sap.cloud.mobile.fiori.demo.search.DemoSearchBarcodeValidator;
import com.sap.cloud.mobile.fiori.formcell.FormCell;
import com.sap.cloud.mobile.fiori.formcell.SimplePropertyFormCell;

import static com.sap.cloud.mobile.onboarding.qrcodereader.google.QRCodeReaderActivity.REQ_CAMERA;

public class SimplePropertyFormCellActivity extends AbstractDemoActivity {
    private SimplePropertyFormCell mSimplePropertyFormCell1, mSimplePropertyFormCell2,
            mSimplePropertyFormCell3, mSimplePropertyFormCell4,
            mSimplePropertyFormCell5, mSimplePropertyFormCell6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_property_form_cell);
        mSimplePropertyFormCell1 = findViewById(R.id.SimplePropertyFormCell1);
        mSimplePropertyFormCell2 = findViewById(R.id.SimplePropertyFormCell2);
        mSimplePropertyFormCell3 = findViewById(R.id.SimplePropertyFormCell3);
        mSimplePropertyFormCell4 = findViewById(R.id.SimplePropertyFormCell4);
        mSimplePropertyFormCell5 = findViewById(R.id.SimplePropertyFormCell5);
        mSimplePropertyFormCell6 = findViewById(R.id.SimplePropertyFormCell6);
        mSimplePropertyFormCell1.setError("Not a valid value");
        mSimplePropertyFormCell1.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
        mSimplePropertyFormCell2.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
        mSimplePropertyFormCell3.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
        mSimplePropertyFormCell4.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
        mSimplePropertyFormCell5.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
        mSimplePropertyFormCell6.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);

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

//        mSimplePropertyFormCell6.setSecondaryActionOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(mSimplePropertyFormCell6.getContext(), "Custom secondary action", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQ_CAMERA && resultCode == Activity.RESULT_OK && DemoSearchBarcodeValidator.getBarcode() != null) {
            // Set value from barcode scan.
            mSimplePropertyFormCell5.setValue(DemoSearchBarcodeValidator.getBarcode().displayValue);
        }
    }
}
