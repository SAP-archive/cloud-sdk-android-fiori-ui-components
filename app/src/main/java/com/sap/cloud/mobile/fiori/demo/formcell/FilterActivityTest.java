package com.sap.cloud.mobile.fiori.demo.formcell;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.sap.cloud.mobile.fiori.demo.AbstractDemoActivity;
import com.sap.cloud.mobile.fiori.demo.R;
import com.sap.cloud.mobile.fiori.formcell.FilterDialogFragment;

import org.json.JSONObject;

public class FilterActivityTest extends AbstractDemoActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filterdemo_activity);

        FilterDialogFragmentTest filterDialogFragment = (FilterDialogFragmentTest)
                getSupportFragmentManager().findFragmentById(R.id.fragmentFilter);

        filterDialogFragment.setApplyListener(new FilterDialogFragmentTest.OnApplyListener() {
            @Override
            public void onApply() {
                JSONObject filterValues = filterDialogFragment.getChangedValues();

                Intent result = new Intent("CHANGED_CELLS");
                result.putExtra("CHANGED_CELLS",filterValues.toString());

                setResult(Activity.RESULT_OK, result);
                finish();
            }
        });
        filterDialogFragment.setDismissListener(new FilterDialogFragment.OnDismissListener() {
            @Override
            public void onDismiss() {
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        finish();
    }
}