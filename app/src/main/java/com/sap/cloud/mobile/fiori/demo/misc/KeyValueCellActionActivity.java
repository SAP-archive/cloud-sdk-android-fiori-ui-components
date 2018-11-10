package com.sap.cloud.mobile.fiori.demo.misc;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;

import com.sap.cloud.mobile.fiori.demo.R;

/**
 * Phones as value.
 */

public class KeyValueCellActionActivity extends BaseKeyValueCellActivity {
    private KeyValueSpec mKeyValueSpec;
    @Override
    public KeyValueSpec getKeyValueSpec() {
        if (mKeyValueSpec == null) {
            mKeyValueSpec = new KeyValueSpec(R.layout.key_value_cell, 50, 1, "Work Phone", true);
        }
        return mKeyValueSpec;
    }

}
